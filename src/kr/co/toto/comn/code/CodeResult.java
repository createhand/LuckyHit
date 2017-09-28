/*
 * @(#)InfoService.java		1.0		2008. 10. 15.
 *
 * Copyright (c) ${year} TA Networks, Inc.
 * All rights reserved.
 *
 */
package kr.co.toto.comn.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * majorCode와 codeList을 제공하는 클래스 입니다.
 */
public class CodeResult {
    private String clsId;
    
    /* codeList */
    private List<Map<String,String>> codeList;

    /* regist time (ms) */
    private long registTime;

    /**
     * 생성자.
     * 
     * @param clsId
     */
    CodeResult(String clsId) {
        this.registTime = System.currentTimeMillis();
        this.clsId = clsId;
    }

    /**
     * 생성자.
     * 
     * @param clsCode
     * @param codeList
     */
    CodeResult(String clsId, List<Map<String,String>> codeList) {
        this.registTime = System.currentTimeMillis();
        this.clsId = clsId;
        this.codeList = codeList;
    }

    /**
     * @return lclasCode 값을 제공한다.
     */
    public String getClsId() {
        return clsId;
    }

    /**
     * @return codeList 값을 제공한다.
     */
    public List<Map<String,String>> getCodeList() {
        return codeList;
    }

    /**
     * @return registTime 값을 제공한다.
     */
    public long getRegistTime() {
        return registTime;
    }
    
    public static CodeResult createErrorCodeResult(String clsId, String message) {
    	List<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("ERROR", message);
    	list.add(map);
    	return new CodeResult(clsId, list);
    }
}
