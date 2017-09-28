package kr.co.toto.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.support.RequestContext;


public class JsonResponse {
    private static final Log log = LogFactory.getLog(JsonResponse.class);
    
	private String code;
	private String message;
	private Object params;
	
	public JsonResponse(HttpServletRequest request) {
	    this(request, "000000");
	}

    public JsonResponse(HttpServletRequest request, Exception e) {
            log.error(e.toString(), e);
            code = "999999";
            RequestContext rc = new RequestContext(request);
            message = StringUtils.replace(rc.getMessage("json.code."+code), "<br/>", "\n");
    }

    public JsonResponse(HttpServletRequest request, String code) {
        this.code = code;
        // locale에 따라 응답메시지를 설정한다.
        RequestContext rc = new RequestContext(request);
        message = rc.getMessage("json.code."+code);
    }
    
    public JsonResponse(HttpServletRequest request, String code, String ... params) {
        this.code = code;
        // locale에 따라 응답메시지를 설정한다.
        RequestContext rc = new RequestContext(request);
        message = rc.getMessage("json.code."+code, params);
    }
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getParams() {
		return params;
	}
	public void setParams(Object params) {
		this.params = params;
	}
}
