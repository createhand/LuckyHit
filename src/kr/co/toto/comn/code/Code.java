/*
 * @(#)InfoService.java		1.0		2008. 10. 15.
 *
 * Copyright (c) ${year} TA Networks, Inc.
 * All rights reserved.
 *
 */
package kr.co.toto.comn.code;

import java.io.Serializable;

/**
 * Code 클래스 입니다.
 */
public class Code implements Serializable {

    private static final long serialVersionUID = -4362064773078663187L;
    
    private String lclasCd; // 대분류코드
    private String sclasCd; // 소분류코드
    private String cdNm; // 코드명
    private String alnm; // 별칭
    private String engNm; // 영문명
    private String alnm2; // 별칭2
    private String rmk; // 비고
    private String useYn; // 사용여부
    private String chnlDvCd; // 채널구분코드
    private String memUseYn; // 메모리사용여부
    private String sysRegDtm; // 시스템등록일시
    private String sysUpdDtm; // 시스템변경일시
    private String regEmpNo; // 등록직원번호
    private String updEmpNo; // 변경직원번호

    /**
     * 생성자.
     */
    public Code() {
    }
    
    /**
     * 생성자.
     * @param lclasCd
     * @param sclasCd
     * @param cdNm
     */
    public Code(String lclasCd, String sclasCd, String cdNm) {
        this.lclasCd = lclasCd;
        this.sclasCd = sclasCd;
        this.cdNm = cdNm;
    }

    /**
     * @return lclasCd 값을 제공한다.
     */
    public String getLclasCd() {
        return lclasCd;
    }

    /**
     * @param lclasCd lcassCd에 값을 설정한다.
     */
    public void setLclasCd( String lcassCd) {
        this.lclasCd = lcassCd;
    }

    /**
     * @return sclasCd 값을 제공한다.
     */
    public String getSclasCd() {
        return sclasCd;
    }

    /**
     * @param sclasCd sclasCd에 값을 설정한다.
     */
    public void setSclasCd( String sclasCd) {
        this.sclasCd = sclasCd;
    }

    /**
     * @return cdNm 값을 제공한다.
     */
    public String getCdNm() {
        return cdNm;
    }

    /**
     * @param cdNm cdNm에 값을 설정한다.
     */
    public void setCdNm( String cdNm) {
        this.cdNm = cdNm;
    }

    /**
     * @return alnm 값을 제공한다.
     */
    public String getAlnm() {
        return alnm;
    }

    /**
     * @param alnm alnm에 값을 설정한다.
     */
    public void setAlnm( String alnm) {
        this.alnm = alnm;
    }

    /**
     * @return engNm 값을 제공한다.
     */
    public String getEngNm() {
        return engNm;
    }

    /**
     * @param engNm engNm에 값을 설정한다.
     */
    public void setEngNm( String engNm) {
        this.engNm = engNm;
    }

    /**
     * @return alnm2 값을 제공한다.
     */
    public String getAlnm2() {
        return alnm2;
    }

    /**
     * @param alnm2 alnm2에 값을 설정한다.
     */
    public void setAlnm2( String alnm2) {
        this.alnm2 = alnm2;
    }

    /**
     * @return rmk 값을 제공한다.
     */
    public String getRmk() {
        return rmk;
    }

    /**
     * @param rmk rmk에 값을 설정한다.
     */
    public void setRmk( String rmk) {
        this.rmk = rmk;
    }

    /**
     * @return useYn 값을 제공한다.
     */
    public String getUseYn() {
        return useYn;
    }

    /**
     * @param useYn useYn에 값을 설정한다.
     */
    public void setUseYn( String useYn) {
        this.useYn = useYn;
    }

    /**
     * @return chnlDvCd 값을 제공한다.
     */
    public String getChnlDvCd() {
        return chnlDvCd;
    }

    /**
     * @param chnlDvCd chnlDvCd에 값을 설정한다.
     */
    public void setChnlDvCd( String chnlDvCd) {
        this.chnlDvCd = chnlDvCd;
    }

    /**
     * @return memUseYn 값을 제공한다.
     */
    public String getMemUseYn() {
        return memUseYn;
    }

    /**
     * @param memUseYn memUseYn에 값을 설정한다.
     */
    public void setMemUseYn( String memUseYn) {
        this.memUseYn = memUseYn;
    }

    /**
     * @return sysRegDtm 값을 제공한다.
     */
    public String getSysRegDtm() {
        return sysRegDtm;
    }

    /**
     * @param sysRegDtm sysRegDtm에 값을 설정한다.
     */
    public void setSysRegDtm( String sysRegDtm) {
        this.sysRegDtm = sysRegDtm;
    }

    /**
     * @return sysUpdDtm 값을 제공한다.
     */
    public String getSysUpdDtm() {
        return sysUpdDtm;
    }

    /**
     * @param sysUpdDtm sysUpdDtm에 값을 설정한다.
     */
    public void setSysUpdDtm( String sysUpdDtm) {
        this.sysUpdDtm = sysUpdDtm;
    }

    /**
     * @return regEmpNo 값을 제공한다.
     */
    public String getRegEmpNo() {
        return regEmpNo;
    }

    /**
     * @param regEmpNo regEmpNo에 값을 설정한다.
     */
    public void setRegEmpNo( String regEmpNo) {
        this.regEmpNo = regEmpNo;
    }

    /**
     * @return updEmpNo 값을 제공한다.
     */
    public String getUpdEmpNo() {
        return updEmpNo;
    }

    /**
     * @param updEmpNo updEmpNo에 값을 설정한다.
     */
    public void setUpdEmpNo( String updEmpNo) {
        this.updEmpNo = updEmpNo;
    }

}
