package kr.co.toto.util;

import javax.servlet.http.HttpServletRequest;

import kr.co.toto.lgin.WebAttrKey;

import org.springframework.web.servlet.support.RequestContext;

public class MessageUtil {
    public static void saveMessage(HttpServletRequest request, String code) {
        saveMessage(request, code, "");
    }
    public static void saveMessage(HttpServletRequest request, String code, String message) {
		request.setAttribute(WebAttrKey.REQ_CODE, code);
		request.setAttribute(WebAttrKey.REQ_MESSAGE, message);
	}
    
    public static String getJsonMessage(HttpServletRequest request, String code) {
        // locale에 따라 응답메시지를 설정한다.
        RequestContext rc = new RequestContext(request);
        try {
            return rc.getMessage("json.code."+code);
        }
        catch (Exception e) {
            return rc.getMessage("json.code.empty");
        }
    }
    
    public static String getLabel(HttpServletRequest request, String key) {
        RequestContext rc = new RequestContext(request);
        try {
            return rc.getMessage("label."+key);
        }
        catch (Exception e) {
            return rc.getMessage("label.empty");
        }
    }
    
}
