/*
 * Copyright (c) 2007-2010 Phronis, Co., Ltd.
 *
 * All Rights Reserved. Unpublished rights reserved under the copyright laws
 * of the South Korea. The software contained on this media is proprietary
 * to and embodies the confidential technology of Phronis, Co., Ltd. The
 * possession or receipt of this information does not convey any right to disclose
 * its contents, reproduce it, or use, or license the use, for manufacture or
 * sale, the information or anything described therein. Any use, disclosure, or
 * reproduction without Phronis's prior written permission is strictly prohibited.
 */
package kr.co.toto.comn.exception;


/**
 * <pre>
 * 첫째줄에는 클래스에 대한 설명을 요약해서 적습니다. 줄바꿈을 위한 <BR> 태그를 절대로 사용하지 마시기 바랍니다. 그냥 엔터치세요.
 * 
 * 줄바꿈한 이 줄부터는 상세한 기능을 번호를 붙여서 적습니다. 아래는 그 예입니다.
 * 1. 전표 번호 채번
 * 2. 계좌 번호 채번
 * 3. 참조 번호 채번
 * </pre>
 *
 * @title AjaxFormResException.java
 * @project SMBC_IBS
 * @date 2011. 10. 10. 오전 9:45:35
 * @version 1.0
 * @author Administrator
 *
 */
public class AjaxFormResException extends Exception{
    private Exception ex;    
    private String redirectUrl;
    private String lrgvolTranNm;
    
    public AjaxFormResException(Exception ex, String redirectUrl) {
        this(ex, redirectUrl, null);
    }
    
    public AjaxFormResException(Exception ex, String redirectUrl, String lrgvolTranNm) {
        this.ex = ex;
        this.redirectUrl= redirectUrl;
        this.lrgvolTranNm = lrgvolTranNm;
    }
    
    public Exception getException(){
        return ex;
    }

    /**
     * @return the redirectUrl
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getLrgvolTranNm() {
        return lrgvolTranNm;
    }
}
