package kr.co.toto.comn;

import java.util.HashMap;

public class ParameterMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public void setString(String key, String value){
		put(key,value);
	}
	
	public String getString(String key){
		return getNullToString(key);
	}
	
	public String getNullToString(String key){
		return getNullToString(key,"");
	}
	
	public int getInt(String key){
		return getInt(key,"0");
	}
	
   public int getInt(String key, String def){
       String returnValue = getNullToString(key, def);
        return Integer.parseInt(returnValue);
    }
	   
   public String getNullToString(String key, String def){
       String returnValue = "";
       if(get(key) != null) returnValue = (String)get(key);
       if(returnValue.trim().equals("")) returnValue = def;
       
       return returnValue.trim();
    }
   
   public long getLong(String key){
       return  Long.parseLong(getNullToString(key, "0"));    
   }
}
