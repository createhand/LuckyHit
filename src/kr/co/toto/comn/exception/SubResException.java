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
 * Sub페이지 로딩시 에러가 발생한 경우 호출되는 예외 클래스
 * </pre>
 *
 * @title BizException.java
 * @project SMBC_IBS
 * @date 2011. 10. 10. 오전 9:45:35
 * @version 1.0
 * @author Administrator
 *
 */
public class SubResException extends Exception{
    private Exception e;
    
    public SubResException(Exception ex) {
        super(ex.getMessage()); 
        this.e = ex;
    }
    
    public SubResException(String msg) {
        super(msg);
    }
    
    public Exception getException() {
        return e;
    }
}
