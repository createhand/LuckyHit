package kr.co.toto.base.controller;

import java.text.ParseException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import kr.co.toto.base.persistence.domain.DomainConst;
import kr.co.toto.comn.exception.AjaxFormResException;
import kr.co.toto.comn.exception.SubResException;
import kr.co.toto.util.MessageUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.support.RequestContext;


/**
 * <pre>
 * 컨트롤러에서 사용되는 기본 메소드를 제공하는 추상클래스.
 * 
 * 작성된 모든 컨트롤러는 이 클래스를 확장하여야 한다.
 * </pre>
 *
 * @title AbstractController.java
 * @project Default
 * @date 2012. 9. 24. 오전 10:16:23
 * @version 1.0
 * @author hjlee
 *
 */
public abstract class AbstractController {
	private static final Log log = LogFactory.getLog(AbstractController.class);

    /**
     * <pre>
     * AJAX로 호출되는 Controller에서 발생되는 Exception에 대한 예외 처리 메소드
     * 
     * Controller에서 AjaxFormResException 발생되면 해당 메소드가 수행된다.
     * </pre>
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(AjaxFormResException.class)
    public String handleAjaxFormResException(AjaxFormResException e, HttpServletRequest request) {
        saveMessage(e.getException(), request);
        request.setAttribute("redirectUrl", e.getRedirectUrl());
        request.setAttribute("lrgvolTranNm", e.getLrgvolTranNm());
        return "999988";
    }
    
    
    /**
     * <pre>
     * 페이지내에서 InnerHTML로 호출되는 Controller에서 발생되는 Exception에 대한 예외 처리 메소드
     * 
     * Controller에서 SubResException에 발생되면 해당 메소드가 수행된다.
     * </pre>
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(SubResException.class)
    public String handleSubResException(SubResException e, HttpServletRequest request) {
        saveMessage(e.getException(), request);
        return "999997";
    }
    
    
    /**
     * <pre>
     * Exception에 대한 예외 처리 메소드
     * 
     * Controller에서 Exception이 발생되면 해당 메소드가 수행된다.
     * </pre>
     *
     * @param e 예외
     * @param request HttpServletRequest
     * @return view
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest request) {
        saveMessage(e, request);
        return "error";
    }
    
	
	/**
	 * <pre>
	 * View 명을 리턴한다.
	 * 
	 * requestURI로 부터 뷰명을 찾는다.
	 * </pre>
	 *
	 * @param request HttpServletRequest
	 * @return view
	 */
	protected String getViewName(HttpServletRequest request) {
		String uri = request.getRequestURI();
		
		String viewName = uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf("."));
		request.setAttribute("_screenId", viewName);
		return viewName;
	}
	
	/**
	 * <pre>
	 * 발생된 예외에 대한 에러 메시지를 Request객체에 저장한다.
	 * 
	 * 리턴되는 페이지에 해당 에러메시지가 출력된다.
	 * </pre>
	 *
	 * @param e 예외
	 * @param request HttpServletRequest
	 */
	protected void saveMessage(Exception e, HttpServletRequest request) {
        RequestContext rc = new RequestContext(request);
        String code = null;
        String message = null;
        if (e instanceof kr.co.toto.base.exception.BizException) {
            code = ((kr.co.toto.base.exception.BizException) e).getErrCode();
            try {
                Object [] params = ((kr.co.toto.base.exception.BizException) e).getParams();
                message = rc.getMessage("json.code."+code, params);
            }
            catch (NoSuchMessageException nsme) {
                message = rc.getMessage("json.code.empty") + " ["+code+"]";
            }
        }
        else {
            log.error("에러 발생", e);
            
            code = "999999";
            try {
                message = rc.getMessage("json.code."+code);
            }
            catch (NoSuchMessageException nsme) {
                message = rc.getMessage("json.code.empty") + " ["+code+"]";
            }
        }
        
        MessageUtil.saveMessage(request, code, message);
	}
	
    private static final String [] datePattern = {"yyyyMMdd"};


}
