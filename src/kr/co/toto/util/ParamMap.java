package kr.co.toto.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.toto.lgin.SessionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import signgate.core.crypto.util.Base64Util;
import signgate.core.crypto.util.CipherUtil;

public class ParamMap extends LinkedHashMap<String, Object> {
    Log log = LogFactory.getLog(ParamMap.class);
    private static final long serialVersionUID = 1L;
    public ParamMap(Map<String, Object> params) {
        super(params);
        setPaging();
        if (log.isDebugEnabled()) {
            log.debug("PARAM MAP = " + this);
        }
    }
    
    public ParamMap(String params) {
        if (log.isDebugEnabled()) {
            log.debug("PARAM MAP = " + params);
        }
        try {
            if (params == null) 
                return;
        
            parseJsonParameter(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPaging();
    }
    
    public ParamMap(String params, HttpServletRequest request) {
        byte[] sessionKey = (byte[]) SessionUtil.getLoginContext(request).getSessionKey();
        if (log.isDebugEnabled()) {
            log.debug("PARAM MAP = " + params);
        }
        try {
            if (params == null) 
                return;
        
            String decryptedData = decrypt(sessionKey, params);
            if (decryptedData == null) return;
            parseJsonParameter(decryptedData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setPaging();
    }
    
    public ParamMap(Map<String, Object> params, HttpServletRequest request) {
//        super(params);

        if (request != null) {
            byte[] sessionKey = (byte[]) SessionUtil.getLoginContext(request).getSessionKey();
            String p = request.getParameter("p");
            String q = request.getParameter("q");
            try {
                if (p != null) {
                    String decryptedData = decrypt(sessionKey, URLDecoder.decode(p, "UTF-8"));
                    if (decryptedData == null) return;
                    parseFormParameter(decryptedData);
                }
                if (q != null) {
                    String decryptedData = decrypt(sessionKey, URLDecoder.decode(q, "UTF-8"));
                    if (decryptedData == null) return;
                    parseFormParameter(decryptedData);
                }
            }
            catch (Exception e) {
                log.error(e.toString(), e);
            }
        }
        setPaging();
    }
    
    private void setPaging() {
        // 페이징관련 파라메터가 있으면 페이징 조회값을 설정한다.
        if (containsKey("_page_limit")) {
            int start = getInt("_page_start", 1) - 1;
            int limit = getInt("_page_limit", 10);

            int offset = (start * limit);
            put("_page_min", Integer.toString(offset + 1));
            put("_page_max", Integer.toString(offset + limit));
        }

        if (log.isDebugEnabled()) {
            log.debug("request params = " + this);
        }
    }

    private String decrypt(byte[] sessionKey, String enCryptData) {
        if (enCryptData == null || enCryptData.trim().length() == 0)
            return null;

        try {
            CipherUtil cipher = new CipherUtil(enCryptData);
            //cipher.decryptInit(sessionKey);
            log.debug("sessionKey : " +  new java.math.BigInteger(sessionKey).toString(16));
            if (log.isDebugEnabled()) {
                log.debug("enCryptDate [" + enCryptData.replaceAll(" ", "+") + "]");
                
            }
            byte[] decryptedByteData = cipher.decryptUpdate(Base64Util.decode(enCryptData.replaceAll(" ", "+")));
            cipher.decryptFinal();
            if (decryptedByteData == null) {
                log.error("복화중 에러 발생 : " + cipher.getErrorMsg());
                return null;
            }
            String decryptedData = new String(decryptedByteData);
//            String decryptedData = StringUtils.replace(enCryptData, "_!!_", "&");
//            decryptedData = StringUtils.replace(decryptedData, "_!_", "=");
            
            if (log.isDebugEnabled()) {
                log.debug("decryptedData="+decryptedData);
            }
            
            return decryptedData;
        }
        catch (Exception e) {
            log.error(e.toString(), e);
            return null;
        }
    }
   
    
    public static String decrypt(String params, HttpServletRequest request) {
        ParamMap pm = new ParamMap((String)null);
        byte[] sessionKey = (byte[]) SessionUtil.getLoginContext(request).getSessionKey();
        try {
            if (params == null) 
                return null;
        
            return pm.decrypt(sessionKey, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private void parseFormParameter(String decryptedData) throws UnsupportedEncodingException {
        String[] parameters = StringUtils.split(decryptedData, "&");
        int parameterSize = parameters.length;
        for (int i = 0; i < parameterSize; i++) {
            int pos = parameters[i].indexOf('=');
            if (pos > 0) {
                String key = parameters[i].substring(0, pos);
                if (containsKey(key)) {
                    Object val = get(key);
                    if (val instanceof String) {
                        List<String> list = new ArrayList<String>();
                        list.add((String) val);
                        list.add(URLDecoder.decode(parameters[i].substring(pos + 1), "UTF-8"));
                        put(key, list);
                    } else {
                        ((List<String>) val).add(URLDecoder.decode(parameters[i].substring(pos + 1), "UTF-8"));
                    }
                } else {
                    put(key, URLDecoder.decode(parameters[i].substring(pos + 1), "UTF-8"));
                    log.debug("param put key="+key+", val=" + parameters[i].substring(pos + 1));
                    log.debug("param put key="+key+", val=" + URLDecoder.decode(parameters[i].substring(pos + 1), "UTF-8"));
                }
            }
        }
    }
    
    private void parseJsonParameter(String decryptedData) throws Exception {
        
        String jsonStr = JsonUtil.base64Decode(decryptedData);
        if (log.isDebugEnabled()) {
            log.debug("jsonStr = " + jsonStr);
        }
        JSONObject jo = JSONObject.fromObject(jsonStr);
        parseJson(this, jo);
//        for (Iterator<String> iter = jo.keys(); iter.hasNext();) {
//            String key = iter.next();
//            Object obj = jo.get(key);
//            if (JSONUtils.isArray(obj)) {
//                List<String> list = new ArrayList<String>();
//                JSONArray ja = (JSONArray)obj;
//                for (int i = 0; i < ja.size(); i++) {
//                    list.add(ja.getString(i));
//                }
//                put(key, list);
//            }
//            else {
//                put(key, jo.getString(key));
//            }
//        }
        
        log.debug("all items = " + toString());
    }

    private void parseJson(Map<String,Object> map, JSONObject jo) throws Exception {
        for (Iterator<String> iter = jo.keys(); iter.hasNext();) {
            String key = iter.next();
            Object obj = jo.get(key);
            if (JSONUtils.isArray(obj)) {
                log.debug("리스트임. !! " + key);
                List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
                JSONArray ja = (JSONArray)obj;
                for (int i = 0; i < ja.size(); i++) {
                    Map<String,Object> arrMap = new HashMap<String,Object>();
                    parseJson(arrMap, ja.getJSONObject(i));
                    log.debug("리스트에 추가되는 정보 : " + arrMap);
                    list.add(arrMap);
                }
                map.put(key, list);
            }
            else {
                map.put(key, jo.getString(key));
            }
        }
    }
    
    public List<Map<String,Object>> getList(String key) {
        return (List<Map<String,Object>>)get(key);
    }

    public boolean isArray(String key) {
        Object value = get(key);

        if (value == null) {
            return false;
        }

        if (value instanceof String[]) {
            return true;
        }
        return false;
    }

    public String [] getStringArray(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return new String[] { (String) value };
        }
        
        List<String> temp = (List<String>)value;
        return temp.toArray(new String[temp.size()]);
    }

    public String [] getRemoveDateFormatStringArray(String key) {
        String [] arr = getStringArray(key);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = StringUtils.replace(StringUtils.replace(arr[i], ".", ""), "-", "");
        }
        return arr;
    }

    public String [] getRemoveNumberFormatStringArray(String key) {
        String [] srcArr = getStringArray(key);
        String [] tarArr = getStringArray(key);
        for (int i = 0; i < srcArr.length; i++) {
            tarArr[i] = StringUtils.defaultIfEmpty(StringUtils.replace(srcArr[i], ",", ""), "0");
        }
        srcArr = null;
        return tarArr;
    }

    public int [] getIntArray(String key) {
        String [] srcArr = getStringArray(key);
        int [] tarArr = new int[srcArr.length];
        for (int i = 0; i < srcArr.length; i++) {
            tarArr[i] = NumberUtils.toInt(StringUtils.defaultIfEmpty(StringUtils.replace(srcArr[i], ",", ""), "0"));
        }
        srcArr = null;
        return tarArr;
    }

    public long [] getLongArray(String key) {
        String [] srcArr = getStringArray(key);
        long [] tarArr = new long[srcArr.length];
        for (int i = 0; i < srcArr.length; i++) {
            tarArr[i] = NumberUtils.toInt(StringUtils.defaultIfEmpty(StringUtils.replace(srcArr[i], ",", ""), "0"));
        }
        srcArr = null;
        return tarArr;
    }

    public BigDecimal [] getBigDecimalArray(String key) {
        String [] srcArr = getStringArray(key);
        BigDecimal [] tarArr = new BigDecimal[srcArr.length];
        for (int i = 0; i < srcArr.length; i++) {
            tarArr[i] = new BigDecimal(StringUtils.defaultIfEmpty(StringUtils.replace(srcArr[i], ",", ""), "0"));
        }
        srcArr = null;
        return tarArr;
    }

    public String getString(String key) {
        Object value = get(key);

        if (value == null) {
            return "";
        }

        if (value instanceof String[]) {
            String[] temp = (String[]) value;
            if (temp.length > 1) {
                return temp[0];
            } else {
                return "";
            }
        }
        return (String) value;
    }

    public String getRemoveDateFormatString(String key) {
        return StringUtils.replace(StringUtils.replace(getString(key), ".", ""), "-", "");
    }
    
    public String getRemoveNumberFormatString(String key) {
        return StringUtils.defaultIfEmpty(StringUtils.replace(getString(key), ",", ""), "0");
    }
    
    public int getInt(String key) {
        return NumberUtils.toInt(getRemoveNumberFormatString(key));
    }

    public int getInt(String key, int defVal) {
        return NumberUtils.toInt(getRemoveNumberFormatString(key), defVal);
    }

    public long getLong(String key) {
        return NumberUtils.toLong(getRemoveNumberFormatString(key));
    }

    public BigDecimal getBigDecimal(String key) {
        return new BigDecimal(getRemoveNumberFormatString(key));
    }
    
    public static void main(String [] args) throws Exception {
        Map map = new LinkedHashMap();
        map.put("a", "123,123,123,1321");
        map.put("b", "2012-01-01");
        map.put("c", "2012.01.01");
        map.put("d", "");
        map.put("e", "+");
        
        ParamMap pm = new ParamMap(map);
        
        System.out.println("["+pm.getRemoveNumberFormatString("d")+"]");
        System.out.println(pm.getBigDecimal("a"));
        System.out.println(pm.getRemoveDateFormatString("b"));
        System.out.println(pm.getRemoveDateFormatString("c"));
        System.out.println(pm.getBigDecimal("d"));
        System.out.println(pm.getString("e"));
        
        String temp = "eyJhY2N0U2VxTm8iOjEsImFwcnZScXNZbiI6IiIsImJrZ1RyYW5EdCI6IjIwMTIw\n"
                +"NjIyIiwiYmtnVHJhblluIjoiTiIsImNhbmNEdCI6bnVsbCwiY250Y1RlbE5vIjoi\n"
        +"IiwiY3VzdE5vIjoiIiwiZGVwQWNjdE5vIjoiMTIzNDEyMzQxMjM0IiwiZGVwQmFu\n"
        +"a0NkIjoiMDA0IiwiZGVwQmFua05tIjoi6rWt66 87J2A7ZaJIiwiZGVwRHBwcyI6\n"
        +"Iu2Zjeq4uOuPmSAgICAgICAgICAgICAgIiwiZGVwUGJrU2h3Q29udCI6IiIsImZl\n"
        +"ZSI6MjAwMCwiZ3Zia1JzbiI6IiIsImxzdEFwcnZTdGVwIjowLCJtbnlQcHMiOiIi\n"
        +"LCJvdXRXZHJBY2N0Tm8iOiIzMTIxV1IxMDAxNjE3S1JXMSIsInJlZ0R0IjoiMjAx\n"
        +"MjA2MjEiLCJyZWdUbSI6IjE3MTIxMjIwIiwicnNwQ2QiOiIwMDAwIiwicnNwTXNn\n"
        +"IjoiIiwidHJhbkFjbWxBbXQiOjEsInRyYW5BbXQiOjEsInRyYW5Qcm9jRHMiOiIi\n"
        +"LCJ0cmFuUHJvY1N0dHMiOiIiLCJ0cnhEcyI6IiIsInVzZXJJZCI6IiIsIndkckFj\n"
+"Y3RObyI6IjUxNjMwMTAwMTYxNzEiLCJ3ZHJQYmtTaHdDb250IjoiIn0=";
        
        System.out.println(new String(Base64Util.decode(temp)));
        
        
    }
}
