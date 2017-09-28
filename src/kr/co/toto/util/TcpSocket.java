package kr.co.toto.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

public class TcpSocket {
	private final   int     retry     = 1;
	private int             port      = -1;
	private String          host      = null;
	private Socket          sock      = null;
	private BufferedInputStream   in    = null;
	private BufferedOutputStream  out   = null;
	private final   String  sign      = "en$of";
	public  boolean         connected = false;
	private int             timeout   = 0 * 1000;  // millisecond
	private final   String  digit     = "0123456789ABCDEF";

	public  String          error     = null;

	public TcpSocket(Socket sock)
	{
		this.sock = sock;
	}

	public TcpSocket(String host, int port)
	{
		this(host, port, 0);
	}

	public TcpSocket(String host, int port, int timeout)
	{
		this.host    = host;
		this.port    = port;
		this.timeout = timeout;
	}

	public void setTimeout(int msecs) throws Exception
	{
		timeout = msecs;
		sock.setSoTimeout(msecs);
	}

	public boolean connect()
	{
		if (connected) return true;

		boolean result = true;
		try
		{
			if (sock == null)
			{
				int i;
				for (i = 0; sock == null && i < retry; i++)
				{
					try
					{
						sock = new Socket(host, port);
					}
					catch (java.net.ConnectException ce)
					{
						ce.printStackTrace();
						Thread.sleep(300);
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
						Thread.sleep(300);
						if (sock != null)
						{
							try	{ sock.close();	} catch (Exception err)	{}
							sock = null;
						}
					}
				}
				result = (i < retry ? true : false);
				if (result == true)
					sock.setSoTimeout(timeout);
				else
				{
					if (sock != null)
					{
						try
						{
							sock.close();
						}
						catch (Exception err)
						{
							err.printStackTrace();
						}
						sock = null;
					}
				}
			}

			if (result)
			{
				out = new BufferedOutputStream(sock.getOutputStream());
				out.flush();
				in  = new BufferedInputStream(sock.getInputStream());
			}
		}
		catch (Exception ex)
		{
			result = false;
			error = ex.getMessage();
			ex.printStackTrace();
		}

		connected = result;
		return result;
	}

	public void close()
	{
		try
		{
			if (connected == true)
			{
				in.close();
				out.close();
				sock.close();
				port = -1;
				connected = false;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private byte[] read(int len) throws Exception
	{
		byte buff[] = new byte[len];
		return (read(buff, 0, len) == len ? buff : null);
	}

	private int read(byte buff[], int off, int len) throws  Exception
	{
		int idx = 0;
		int retry = 0;

		for (idx = 0; idx < len;)
		{
			int rb = in.read(buff, off + idx, len - idx);
			if (rb > 0)
			{
				idx += rb;
			}
			else
			{
				if (retry > 0)
					throw new Exception("Socket timeout occurred.");
				else
				{
					Thread.sleep(timeout);
					retry++;
				}
			}
		}
		return idx;
	}

	private boolean signCheck()throws   Exception
	{
		boolean result = false;
		try
		{
			byte buff[] = read(sign.length());
			while ((result = new String(buff).equals(sign)) == false)
			{
				// shift one byte
				for (int i = 0; i < sign.length() - 1; i++)
					buff[i] = buff[i + 1];
				// reread sign.length - 1
				if (read(buff, sign.length() - 1, 1) <= 0)
					break;
			}
		}catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return result;
	}

	private int length()throws   Exception
	{
		int result = -1;
		try
		{
			byte b[] = read(1);
			b = new byte[digit.indexOf((int)b[0])];

			if (read(b, 0, b.length) != b.length)
				return result;

			result = Integer.parseInt(new String(b));
		}catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return result;
	}

	public byte[] read() throws   Exception
	{
		if (signCheck() == false)
			return null;

		int length = length();
		return (length < 0 ? null : read(length));
	}

	public void write(byte data[]) throws Exception
	{
		String pckInfo = sign + digit.charAt(new String("" + data.length).length()) + data.length;
		byte buff[] = new byte[pckInfo.length() + data.length];
		System.arraycopy(pckInfo.getBytes("KSC5601"), 0, buff, 0, pckInfo.length());
		System.arraycopy(data, 0, buff, pckInfo.length(), data.length);
		out.write(buff);
		out.flush();
	}
}
