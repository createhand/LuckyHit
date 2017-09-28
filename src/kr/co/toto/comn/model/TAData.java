package kr.co.toto.comn.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TAData extends HashMap<String, Object> {

	private static final long serialVersionUID = 5176356319370760473L;
	
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	private boolean nullToInitialize;
    public boolean isNullToInitialize()
    {
        return nullToInitialize;
    }

    public void setNullToInitialize(boolean nullToInitialize)
    {
        this.nullToInitialize = nullToInitialize;
    }

	public TAData() {
		this(false);
	}
	
	public TAData(boolean isDate) {
		super();

		if(isDate) {
			Date d = new Date();
			put("SYS_DT", new SimpleDateFormat("yyyyMMdd").format(d));
			put("SYS_TM", new SimpleDateFormat("HHmmss").format(d));
			put("SYS_DTTM", new SimpleDateFormat("yyyyMMddHHmmss").format(d));
		}

		nullToInitialize = false;
	}

	public TAData(Map map) {
		this(map, false);
	}

	public TAData(Map map, boolean isDate) {
		super();
		if(map != null) putAll(map);

		if(isDate) {
			Date d = new Date();
			put("SYS_DT", new SimpleDateFormat("yyyyMMdd").format(d));
			put("SYS_TM", new SimpleDateFormat("HHmmss").format(d));
			put("SYS_DTTM", new SimpleDateFormat("yyyyMMddHHmmss").format(d));
		}

		nullToInitialize = false;
	}

	public void set(String key, String value) {
		put(key, value);
	}
	
	public void set(String key, int value) {
		put(key, Integer.valueOf(value));
	}

	public void set(String key, long value) {
		put(key, Long.valueOf(value));
	}

	public void set(String key, float value) {
		put(key, Float.valueOf(value));
	}
	
	public void set(String key, boolean value) {
		put(key, Boolean.valueOf(value));
	}

	public void set(String key, Object value) {
		put(key, value);
	}
	/*
	public String getClob(String key) {
		if(key == null) {
			throw new RuntimeException("TAData : getClob parameter is empty!!");
		}
		
		StringBuffer sb = null;
		CLOB value = null;		
		Object obj = null;
		obj = get(key);
		
		if(obj == null) {
			return "";
		}
		else if(obj instanceof CLOB) {
			sb = new StringBuffer();
			value = (CLOB) obj;
			try {
				Reader rd = value.getCharacterStream();
				char[] buffer = new char[1024];			
		        int byteRead = 0; 
				while((byteRead=rd.read(buffer,0,1024))!=-1){ 
					sb.append(buffer,0,byteRead); 
				}
			} catch(Exception e) {
				throw new RuntimeException("TAData : getClob Exception [" + value + "]");
			}
		}
		else {
			throw new RuntimeException("TAData : value is not Clob object! [" + obj.getClass() + "]");
		}
		
		return sb.toString();
	}	
*/
	public String getString(String key) {
		if(key == null) {
			throw new RuntimeException("TAData : getString parameter is empty!!");
		}
		
		String value = null;

		Object obj = null;
		obj = get(key);
		if(obj == null) {
			return "";
		}
		else if(obj instanceof String) {
			value = (String) obj;
		}
		else if(obj instanceof Number) {
			Number n = (Number) obj;
			value = n.toString();
		}
		
		return value;
	}
	
	public int getInt(String key) {
		if(key == null) {
			throw new RuntimeException("TAData : getString parameter is empty!!");
		}
		
		int retVal = 0;
		
		Object obj = null;
		obj = get(key);
		if(obj == null) {
            if(isNullToInitialize()) {
            	return 0;
            }
            
            throw new RuntimeException("TAData : value is not Integer object. value is NULL.");
		}
		else if(obj instanceof String) {
			retVal = Integer.parseInt((String) obj);
		}
		else if(obj instanceof Integer) {
			Integer value = null;
			try {
				value = (Integer) get(key);
				retVal = value.intValue();
			} catch(Exception e) {
				throw new RuntimeException("TAData : value is not Integer object [" + value + "]");
			}
		}
		else if(obj instanceof Number) {
			Number value = null;
			try {
				value = (Number) get(key);
				retVal = value.intValue();
			} catch(Exception e) {
				throw new RuntimeException("TAData : value is not Number object [" + value + "]");
			}
		}

		return retVal;
	}

	public long getLong(String key) {
		if(key == null) {
			throw new RuntimeException("TAData : getString parameter is empty!!");
		}
		
		long retVal = 0;
		
		Object obj = null;
		obj = get(key);
		if(obj == null) {
//            if(isNullToInitialize()) {
            	return 0l;
//            }
            
//            throw new RuntimeException("TAData : value is not Long object. value is NULL.");
		}
		else if(obj instanceof String) {
			retVal = Long.parseLong((String) obj);
		}
		else if(obj instanceof Long) {
			Long value = null;
			try {
				value = (Long) get(key);
				retVal = value.longValue();
			} catch(Exception e) {
				throw new RuntimeException("TAData : value is not Long object [" + value + "]");
			}
		}
		else if(obj instanceof Number) {
			Number value = null;
			try {
				value = (Number) get(key);
				retVal = value.longValue();
			} catch(Exception e) {
				throw new RuntimeException("TAData : value is not Number object [" + value + "]");
			}
		}
		
		return retVal;
	}

	public double getDouble(String key) {
		if(key == null) {
			throw new RuntimeException("TAData : getString parameter is empty!!");
		}
		
		double retVal = 0;
		
		Object obj = null;
		obj = get(key);
		if(obj == null) {
            if(isNullToInitialize()) {
            	return 0;
            }
            
            throw new RuntimeException("TAData : value is not Long object. value is NULL.");
		}
		else if(obj instanceof String) {
			retVal = Double.parseDouble((String) obj);
		}
		else if(obj instanceof Double) {
			Double value = null;
			try {
				value = (Double) get(key);
				retVal = value.doubleValue();
			} catch(Exception e) {
				throw new RuntimeException("TAData : value is not Long object [" + value + "]");
			}
		}
		else if(obj instanceof Number) {
			Number value = null;
			try {
				value = (Number) get(key);
				retVal = value.doubleValue();
			} catch(Exception e) {
				throw new RuntimeException("TAData : value is not Number object [" + value + "]");
			}
		}
		
		return retVal;
	}
	
	public float getFloat(String key) {
		if(key == null) {
			throw new RuntimeException("TAData : getString parameter is empty!!");
		}
		
		float retVal = 0;
		
		Object obj = null;
		obj = get(key);
		if(obj == null) {
            if(isNullToInitialize()) {
            	return 0f;
            }
            
            throw new RuntimeException("TAData : value is not Float object. value is NULL.");
		}
		else if(obj instanceof String) {
			retVal = Integer.parseInt((String) obj);
		}
		else if(obj instanceof Float) {
			Float value = null;
			try {
				value = (Float) get(key);
				retVal = value.floatValue();
			} catch(Exception e) {
				throw new RuntimeException("TAData : value is not Float object [" + value + "]");
			}
		}
		else if(obj instanceof Number) {
			Number value = null;
			try {
				value = (Number) get(key);
				retVal = value.floatValue();
			} catch(Exception e) {
				throw new RuntimeException("TAData : value is not Number object [" + value + "]");
			}
		}

		return retVal;
	}

	public boolean getBoolean(String key) {
		if(key == null) {
			throw new RuntimeException("TAData : getString parameter is empty!!");
		}
		
		Object obj = get(key);
        if(obj == null)
        {
            if(isNullToInitialize()) {
            	return false;
            }
            
            throw new RuntimeException("TAData : value is not Boolean object. value is NULL.");
        }
        else {
            if(obj instanceof Boolean) {
                return ((Boolean)obj).booleanValue();
            }
            else if(obj instanceof String) {
                try {
                    return Boolean.valueOf(obj.toString()).booleanValue();
                } catch(Exception e) {
                	throw new RuntimeException("TAData : value is not Boolean object [" + obj + "]");
                }
            }
        }
		
        return false;
	}

	public List<TAData> getList(String key) {
		if(key == null) {
			throw new RuntimeException("TAData : getString parameter is empty!!");
		}
		
		List tmpList = (List) get(key);

		List<TAData> resultList = new ArrayList<TAData>();
		
		if(tmpList != null && tmpList.size() > 0){
			for(int i = 0 ; i < tmpList.size() ; i++) {
				Object obj = tmpList.get(i);
				if(obj instanceof TAData) {
					resultList.add((TAData) obj);
				}
				else {
					TAData row = new TAData((Map) tmpList.get(i));
					resultList.add(row);
				}
			}
		}
		
		set(key, resultList);
		return resultList;
	}

	public Object getObject(String key) {
		if(key == null) {
			throw new RuntimeException("TAData : getString parameter is empty!!");
		}
		
		return get(key);
	}

	public boolean isEmpty() {
		boolean rs = super.isEmpty();
		if(rs) return rs;
		
		if(super.size() > 0) return false;
		return true;
	}

	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		String nameStr = "";
		if(this.name != null) nameStr = " - " + this.name;
		
		sb.append("\n");
		sb.append(allocateCenter(makeRepeatString("-", 70), "[TAData" + nameStr + "]")).append("\n");
		sb.append(allocateCenter(makeRepeatString(" ", 25), "KEY") + "|" + allocateCenter(makeRepeatString(" ", 44), "VALUE")).append("\n");
		sb.append(makeRepeatString("-", 70)).append("\n");

		for(Iterator i = keySet().iterator() ; i.hasNext() ;) {
			String key = (String) i.next();
			Object value = get(key);
			
			sb.append(allocateLeft(makeRepeatString(" ", 25), key));
			sb.append("|");

			String valueStr = "";
			if(value != null) {
				valueStr = value.toString();
				byte[] tmpBytes = new byte[44];
				System.arraycopy(valueStr.getBytes(), 0, tmpBytes, 0, valueStr.getBytes().length < 44 ? valueStr.getBytes().length : 44);
			}
			sb.append(allocateLeft(makeRepeatString(" ", 44), valueStr));
			sb.append("\n");
		}
		
		sb.append(makeRepeatString("-", 70));
		
		return sb.toString();
	}
	
	private String makeRepeatString(String str, int size) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < size ; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

    private String allocateCenter(String line, String text)
    {
        StringBuffer sb = new StringBuffer();
        if(line == null || line.length() == 0)
            return "";
        sb.append(line);
        if(text == null || text.length() == 0)
            return sb.toString();
        if(text.length() > line.length())
        {
            String temp = text;
            text = (new StringBuilder(String.valueOf(temp.substring(0, line.length() - 2)))).append("..").toString();
        }
        int start = line.length() / 2 - text.length() / 2;
        int end = start + text.length();
        sb.replace(start, end, text);
        return sb.toString();
    }

    private String allocateLeft(String line, String text)
    {
        StringBuffer sb = new StringBuffer();
        if(line == null || line.length() == 0)
            return "";
        sb.append(line);
        if(text == null || text.length() == 0)
            return sb.toString();
        if(text.length() > line.length())
        {
            String temp = text;
            text = (new StringBuilder(String.valueOf(temp.substring(0, line.length() - 2)))).append("..").toString();
        }
        int start = 0;
        int end = start + text.length();
        sb.replace(start, end, text);
        return sb.toString();
    }
}
