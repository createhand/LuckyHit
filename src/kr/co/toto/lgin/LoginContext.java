package kr.co.toto.lgin;

import java.io.Serializable;

public class LoginContext implements Serializable {
	private static final long serialVersionUID = -2406337170163860759L;

	private UserDetail userDetail;
	private long loginTimes;
	private long accessTimes;
    private byte [] sessionKey;
    private String certId;
    private String certDn;
    private String lastLginDt;
    private String lastLginTm;

    public LoginContext() {
    	loginTimes = accessTimes = System.currentTimeMillis();
    }
    
	public UserDetail getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}
	public long getLoginTimes() {
		return loginTimes;
	}
	public long getAccessTimes() {
		return accessTimes;
	}
	public void setAccessTimes(long accessTimes) {
		this.accessTimes = accessTimes;
	}
	public byte[] getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(byte[] sessionKey) {
		this.sessionKey = sessionKey;
	}
	public String getCertId() {
		return certId;
	}
	public void setCertId(String certId) {
		this.certId = certId;
	}

    /**
     * @return the certDn
     */
    public String getCertDn() {
        return certDn;
    }

    /**
     * @param certDn the certDn to set
     */
    public void setCertDn(String certDn) {
        this.certDn = certDn;
    }

    /**
     * @return the lastLginDt
     */
    public String getLastLginDt() {
        return lastLginDt;
    }

    /**
     * @param lastLginDt the lastLginDt to set
     */
    public void setLastLginDt(String lastLginDt) {
        this.lastLginDt = lastLginDt;
    }

    /**
     * @return the lastLginTm
     */
    public String getLastLginTm() {
        return lastLginTm;
    }

    /**
     * @param lastLginTm the lastLginTm to set
     */
    public void setLastLginTm(String lastLginTm) {
        this.lastLginTm = lastLginTm;
    }
}
