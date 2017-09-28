package kr.co.toto.lgin;

import java.io.Serializable;


/**
 *
 * @author TA
 *
 */
public class UserDetail implements Serializable {
	private static final long serialVersionUID = 6731900324077796071L;

    private String custNo;          // 고객 번호
    private String coNm;            // 회사 명
    private String coNmEn;          // 회사 명 영어
    private String custDs;          // 고객 구분
    private String custStts;        // 고객 상태
	private String userId;         // 사용자 아이디
    private String userDs;          // 사용자 구분
    private String userNm;          // 사용자 명
    private String userNmEn;        // 사용자 명 영어
    private String telNo;           // 전화번호
    private String mpNo;            // 휴대전화번호
    private String email;           // 이메일
    private String lginDs;          // 로그인 구분 (1:인증서,2:아이디)
    private String pwdChgYn;        // 비밀번호변경여부
    private String trmsAgmtYn;      // 이용약관동의여부
    private String language;        // 사용언어
    
    /**
     * @return the custNo
     */
    public String getCustNo() {
        return custNo;
    }
    /**
     * @param custNo the custNo to set
     */
    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }
    /**
     * @return the coNm
     */
    public String getCoNm() {
        return coNm;
    }
    /**
     * @param coNm the coNm to set
     */
    public void setCoNm(String coNm) {
        this.coNm = coNm;
    }
    /**
     * @return the coNmEn
     */
    public String getCoNmEn() {
        return coNmEn;
    }
    /**
     * @param coNmEn the coNmEn to set
     */
    public void setCoNmEn(String coNmEn) {
        this.coNmEn = coNmEn;
    }
    /**
     * @return the custDs
     */
    public String getCustDs() {
        return custDs;
    }
    /**
     * @param custDs the custDs to set
     */
    public void setCustDs(String custDs) {
        this.custDs = custDs;
    }
    /**
     * @return the custStts
     */
    public String getCustStts() {
        return custStts;
    }
    /**
     * @param custStts the custStts to set
     */
    public void setCustStts(String custStts) {
        this.custStts = custStts;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the userDs
     */
    public String getUserDs() {
        return userDs;
    }
    /**
     * @param userDs the userDs to set
     */
    public void setUserDs(String userDs) {
        this.userDs = userDs;
    }
    /**
     * @return the userNm
     */
    public String getUserNm() {
        return userNm;
    }
    /**
     * @param userNm the userNm to set
     */
    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }
    /**
     * @return the userNmEn
     */
    public String getUserNmEn() {
        return userNmEn;
    }
    /**
     * @param userNmEn the userNmEn to set
     */
    public void setUserNmEn(String userNmEn) {
        this.userNmEn = userNmEn;
    }
    /**
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }
    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
    /**
     * @return the mpNo
     */
    public String getMpNo() {
        return mpNo;
    }
    /**
     * @param mpNo the mpNo to set
     */
    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the lginDs
     */
    public String getLginDs() {
        return lginDs;
    }
    /**
     * @param lginDs the lginDs to set
     */
    public void setLginDs(String lginDs) {
        this.lginDs = lginDs;
    }
    /**
     * @return the pwdChgYn
     */
    public String getPwdChgYn() {
        return pwdChgYn;
    }
    /**
     * @param pwdChgYn the pwdChgYn to set
     */
    public void setPwdChgYn(String pwdChgYn) {
        this.pwdChgYn = pwdChgYn;
    }
    /**
     * @return the trmsAgmtYn
     */
    public String getTrmsAgmtYn() {
        return trmsAgmtYn;
    }
    /**
     * @param trmsAgmtYn the trmsAgmtYn to set
     */
    public void setTrmsAgmtYn(String trmsAgmtYn) {
        this.trmsAgmtYn = trmsAgmtYn;
    }
    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }
    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }
}
