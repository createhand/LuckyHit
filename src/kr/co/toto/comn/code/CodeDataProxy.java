/*
 * @(#)InfoService.java		1.0		2008. 10. 15.
 *
 * Copyright (c) ${year} TA Networks, Inc.
 * All rights reserved.
 *
 */
package kr.co.toto.comn.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.toto.comn.Global;
import kr.co.toto.comn.service.CodeService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Cache 기반 CodeResult를 제공하는 CodeDataProxy 입니다.
 */
public class CodeDataProxy {

    /* LOG */
    private static Log LOG = LogFactory.getLog(CodeDataProxy.class);
    
    private static final String MSG_MISSING_SCODE_LIST = "[하위코드목록 없음]";
    
//    private static final String MSG_MISSING_SCODE = "[해당코드 없음]";
    private static final String MSG_MISSING_SCODE = "";

    /* CodeResult codeResultCache */
    private static Map<String, CodeResult> codeResultCache = new HashMap<String, CodeResult>();

    /* lock */
    private static Object lock = new Object();


	
    /**
     * @param clsId
     * @return
     * @throws Exception
     */
    private static List<Map<String,String>> listForCode( String clsId) throws Exception {
    	CodeService codeService = new CodeService();

    	// 코드데이타를 DB로부터 가져와서 캐싱한다.   
		Map<String,String> params = new HashMap<String,String>();
		params.put("clsId", clsId);
		List<Map<String,String>> svcCodeList = codeService.getComnCdList(params);
        if (LOG.isDebugEnabled()) {
            LOG.debug("listForCode(" + clsId + ") 결과: " + ( svcCodeList == null ? "null" : String.valueOf(svcCodeList.size()) + " 건" ));
        }
        return svcCodeList;
    }

    /**
     * @param lclasCd
     * @param sclasCd
     * @return
     */
    public static String retrieveCodeName(String clsId, String cdId, String localeStr) {
        CodeResult codeResult = findCodeResult(clsId);
        
        if (codeResult.getCodeList() == null || codeResult.getCodeList().size() == 0) {
            return MSG_MISSING_SCODE_LIST;
        }

        for (Map<String,String> code : codeResult.getCodeList()) {
            if (StringUtils.equals(cdId, code.get("cdId"))) {
                String val = code.get(localeStr);
//                if (val == null && Global.LANG_JAPAN.equals(localeStr)) {
//                    val = code.get(Global.LANG_ENGLISH);
//                }
                return val;
//                return code.get(localeStr);
            }
        }
        
        return MSG_MISSING_SCODE;
    }

    /**
     * 캐싱된 코드를 초기화한다.
     */
    public static void clearCodeCache() {
    	codeResultCache.clear();
    }
    
    /**
     * clsId에 해당하는 코드 목록을 제공합니다. 제공되는 코드 목록은 시스템 운용시 캐싱되어 제공 되지만, refreshTerm에 지정된 초단위 만큰 지나면
     * 저장소에서 다시 읽어 캐싱 합니다.
     * 
     * @param lclasCd 대분류코드
     * @param refreshTerm 단위:초
     * @return
     */
    public static CodeResult findCodeResult(String clsId) {
        CodeResult codeResult = codeResultCache.get(clsId);

        // 캐싱된 Code 조회 결과 제공.
        if (codeResult != null) {
            return codeResult;
        }

        // remove, inquiry, caching
        synchronized (lock) {
            // inquiry & caching
            try {

                // 코드목록 조회
                List<Map<String,String>> codeList = listForCode(clsId);
                if (codeList == null || codeList.size() == 0) {
                    return CodeResult.createErrorCodeResult(clsId, "[조회결과 없음]");
                }

                // CodeResult 재설정.
                codeResult = new CodeResult(clsId, codeList);

                // caching
                codeResultCache.put(clsId, codeResult);

                if (LOG.isInfoEnabled()) {
                    LOG.info("다음 분류코드 CodeResult를 캐싱 했습니다. (" + clsId  + ")");
                    LOG.info("현재 캐싱된 CodeResult는 " + codeResultCache.size() + "개 입니다.");
                }

            } catch (Exception e) {

                if (LOG.isErrorEnabled()) {
                    LOG.error(e.getMessage(), e);
                }
                return CodeResult.createErrorCodeResult(clsId, "[조회중 오류발생]");
            }
        }

        return codeResult;
    }
}
