package kr.co.toto.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import kr.co.toto.base.persistence.domain.DomainConst;



public class BizUtil {
	public String convertRecord(int data, int type) {
	
		return "";		
	}	
	
	public int convertInt(String str) {
        try {            
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {}
        return 0;
    }
	
	public long convertLong(String str) {
        try {            
            return Long.parseLong(str);
        } catch (NumberFormatException nfe) {}
        return 0;
    }	
	
	public String getWinstrTeam(String pStr) {
		String winStr = "";
		if(pStr == null) return winStr;
		if(pStr.equals("H")) winStr = "승";
		else if(pStr.equals("A")) winStr = "패";
		else if(pStr.equals("D")) winStr = "무";
		return winStr;
	}
	
	public String getWinstrResult(String pStr) {
		String winStr = "";
		if(pStr == null) return winStr;
		if(pStr.equals(DomainConst.RECORD_WIN)) winStr = "승";
		else if(pStr.equals(DomainConst.RECORD_LOSE)) winStr = "패";
		else if(pStr.equals(DomainConst.RECORD_DRAW)) winStr = "무";
		return winStr;
	}
	
	public int getDayNo(String strDay) {
		int dayNo = 0;
		if(strDay.equals("월")) dayNo = Calendar.MONDAY;
		if(strDay.equals("화")) dayNo = Calendar.TUESDAY;
		if(strDay.equals("수")) dayNo = Calendar.WEDNESDAY;
		if(strDay.equals("목")) dayNo = Calendar.THURSDAY;
		if(strDay.equals("금")) dayNo = Calendar.FRIDAY;
		if(strDay.equals("토")) dayNo = Calendar.SATURDAY;
		if(strDay.equals("일")) dayNo = Calendar.SUNDAY;
		return dayNo;
	}
	
	public String getDayStr(int dayNo) {
		String strDay = "";
		if(dayNo == Calendar.MONDAY) strDay = "월";
		if(dayNo == Calendar.TUESDAY) strDay = "화";
		if(dayNo == Calendar.WEDNESDAY) strDay = "수";
		if(dayNo == Calendar.THURSDAY) strDay = "목";
		if(dayNo == Calendar.FRIDAY) strDay = "금";
		if(dayNo == Calendar.SATURDAY) strDay = "토";
		if(dayNo == Calendar.SUNDAY) strDay = "일";
		return strDay;
	}
	
    public static String getUrlSource(String url, String encoding) throws IOException {
        URL urlObj = new URL(url);
        URLConnection urlConn = urlObj.openConnection();
        urlConn.setRequestProperty("accept-language", "ko-KR");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                urlConn.getInputStream(), encoding));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        in.close();

        return a.toString();
    }
    
    public static String replaceAll(String str, String targetStr, String destStr) {
    	if(str == null) return "";
    	str = str.replaceAll(targetStr, destStr);
    	return str;
    }
    
    public static Object isNull(Object str) {
    	if(str == null || str.equals("null")) {
    		return "";
    	}
    	return str.toString();
    }
    
    public static String isNullZero(Object str) {
    	if(str == null || str.equals("") || str.equals("null")) {
    		return "0";
    	}
    	return str.toString();
    }
    
}
