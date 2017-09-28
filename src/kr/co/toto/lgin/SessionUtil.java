package kr.co.toto.lgin;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static HttpSession getSession(HttpServletRequest request) {
        return request.getSession(true);
    }

    public static boolean isLogined(HttpServletRequest request) {
        return isLogined(getSession(request));
    }

    public static boolean isLogined(HttpSession session) {
        Object obj = session.getAttribute(WebAttrKey.SES_LOGIN_CONTEXT);
        return (obj != null);
    }

    public static LoginContext getLoginContext(HttpServletRequest request) {
        return getLoginContext(getSession(request));
    }

    public static LoginContext getLoginContext(HttpSession session) {
        return (LoginContext) session.getAttribute(WebAttrKey.SES_LOGIN_CONTEXT);
    }

    public static UserDetail getUserDetail(HttpServletRequest request) {
        return getUserDetail(getSession(request));
    }

    public static UserDetail getUserDetail(HttpSession session) {
        LoginContext loginContext = getLoginContext(session);
        if (loginContext == null)
            return null;
        return (UserDetail) loginContext.getUserDetail();
    }

    public static Object getAttribute(HttpServletRequest request, String key) {
        return getSession(request).getAttribute(key);
    }
    
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        getSession(request).setAttribute(key, value);
    }
    
    /**
     * <pre>
     * 요청 METHOD가 GET인경우만 저장한다.
     * 
     * 줄바꿈한 이 줄부터는 상세한 기능에 대한 설명, 사용법 및 사용예에 대한 설명을 적습니다.
     * </pre>
     *
     * @param request
     */
    public static void saveCurrentRequestUri(HttpServletRequest request) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            getSession(request).setAttribute(WebAttrKey.SES_PREVIOUS_URI,
                    request.getAttribute(WebAttrKey.REQ_SPRING_HANDELR_MAPPING_PATH));
        }
    }

    public static void resetPreviousRequestUri(HttpServletRequest request) {
        getSession(request).removeAttribute(WebAttrKey.SES_PREVIOUS_URI);
    }

    public static void invalidate(HttpServletRequest request) {
        invalidate(getSession(request));
    }

    @SuppressWarnings("unchecked")
    public static void invalidate(HttpSession session) {
        Enumeration<String> enu = session.getAttributeNames();
        while (enu.hasMoreElements()) {
            session.removeAttribute(enu.nextElement());
        }
        session.invalidate();
    }

    public static String getPreviousRequestUri(HttpServletRequest request) {
        return (String) getSession(request).getAttribute(WebAttrKey.SES_PREVIOUS_URI);
    }

    public static String getPreviousRequestRedirectViewName(HttpServletRequest request) {
        String uri = getPreviousRequestUri(request);
        if (uri != null)
            return uri;
//            return "redirect:" + uri;
        else
            return null;
    }

    public static String getPreviousRequestForwardViewName(HttpServletRequest request) {
        String uri = getPreviousRequestUri(request);
        if (uri != null)
            return "forward:" + uri;
        else
            return null;
    }
}
