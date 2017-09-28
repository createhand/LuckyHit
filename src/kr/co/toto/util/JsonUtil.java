package kr.co.toto.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import signgate.core.crypto.util.Base64Util;
import signgate.core.crypto.util.InvalidBase64Exception;

public class JsonUtil {
    public static String base64Encode(Object obj) throws UnsupportedEncodingException {
        String ue = URLEncoder.encode(JSONObject.fromObject(obj).toString(), "UTF-8");
        return Base64Util.encode(ue);
    }
    public static String base64EncodeList(List<? extends Object> list) throws UnsupportedEncodingException {
        String ue = URLEncoder.encode(JSONArray.fromObject(list).toString(), "UTF-8");
        return Base64Util.encode(ue);
    }
    public static String base64Encode(String str) throws UnsupportedEncodingException {
        String ue = URLEncoder.encode(str, "UTF-8");
        return Base64Util.encode(ue);
    }
    public static String base64Decode(String val) throws UnsupportedEncodingException, InvalidBase64Exception {
//        URIComponent.decodeURIComponent(rows[i])
        String temp = new String(Base64Util.decode(val));
        return URLDecoder.decode(temp, "UTF-8");
//        return new String(Base64Util.decode(dec));
        
    }
    
    public static JSONObject fromBase64(String val) throws UnsupportedEncodingException, InvalidBase64Exception {
        return JSONObject.fromObject(base64Decode(val));
    }
    public static JSONArray fromBase64List(String val) throws UnsupportedEncodingException, InvalidBase64Exception {
        return JSONArray.fromObject(base64Decode(val));
    }
}
