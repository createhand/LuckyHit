package kr.co.toto.util;

import java.util.List;
import java.util.Map;

//import kr.co.esmbc.inbk.comn.Global;
import kr.co.toto.comn.code.CodeDataProxy;
import kr.co.toto.comn.code.CodeResult;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 공통 코드 Helper
 * 
 * 공통코드테이블에 대한  명칭조회, 콤보박스생성, 라디오컨트롤 생성등.
 * </pre>
 *
 * @title ComnCodeHelper.java
 * @project SMBC_INBK
 * @date 2012. 9. 24. 오전 10:25:50
 * @version 1.0
 * @author jkkoo
 *
 */
public class ComnCodeHelper {

    /**
     * <pre>
     * 콤보박스(select 컨트롤) 생성
     * 
     * 전달된 그룹코드에 대한 코드항목들로 Combo Box생성 html스크립트를 반환
     * </pre>
     *
     * @param useLang
     * @param title
     * @param name
     * @param grpCd
     * @return
     */
    public static String generateSelect(String useLang, String title, String name, String grpCd) {
        return generateSelect(useLang, title, name, grpCd,  "");
    }


    /**
     * <pre>
     * 콤보박스(select 컨트롤) 생성
     * 
     * 전달된 그룹코드에 대한 코드항목들로 Combo Box생성 html스크립트를 반환
     * </pre>
     *
     * @param useLang
     * @param title
     * @param name
     * @param grpCd
     * @param selected
     * @return
     */
    public static String generateSelect(String useLang, String title, String name, String grpCd,
            String selected) {
        return generateSelect(useLang, title, name, grpCd,  selected, "");
    }


    /**
     * <pre>
     * 콤보박스(select 컨트롤) 생성
     * 
     * 전달된 그룹코드에 대한 코드항목들로 Combo Box생성 html스크립트를 반환
     * </pre>
     *
     * @param useLang
     * @param title
     * @param name
     * @param grpCd
     * @param selected
     * @param style
     * @return
     */
    public static String generateSelect(String useLang, String title, String name, String grpCd,
            String selected, String style) {
        return generateSelect(useLang, title, name, grpCd,  selected, style, "");
    }

    /**
     * <pre>
     * 콤보박스(select 컨트롤) 생성
     * 
     * 전달된 그룹코드에 대한 코드항목들로 Combo Box생성 html스크립트를 반환
     * </pre>
     *
     * @param useLang
     * @param title
     * @param name
     * @param grpCd
     * @param selected
     * @param style
     * @param onChange
     * @return
     */
    public static String generateSelect(String useLang, String title, String name, String grpCd,
            String selected, String style, String onChange) {

        StringBuilder optionSb = new StringBuilder();
        List<Map<String, String>> commonCodeList = getCommonCodeList(grpCd);

        optionSb.append("<select id='" + name + "' name='" + name + "' ");
        if (StringUtils.isNotEmpty(style)) {
            optionSb.append(style);
        }
        if (StringUtils.isNotEmpty(onChange)) {
            optionSb.append(" onChange='" + onChange + "' ");
        }
        optionSb.append(" >\n");

        if (title != null && !StringUtils.equals(title, "")) {
            optionSb.append("	<option value=\"\" >" + title + "</option>\n");
        }
        // while( iter.hasNext() ){
        for (int i = 0; i < commonCodeList.size(); i++) {
            Map<String, String> codeInfo = (Map<String, String>) commonCodeList.get(i);
            if(StringUtils.equals(codeInfo.get("useYn"), "Y")) {
                String val = codeInfo.get(useLang);
                optionSb.append("   <option value=\"")
                        .append(String.valueOf(codeInfo.get("cdId")))
                        .append("\"")
                        .append(selected.equals(String.valueOf(codeInfo.get("cdId"))) ? " selected" : "").append(">");
                optionSb.append(String.valueOf(val));
                optionSb.append("   </option>\n");
            }
        }
        optionSb.append("</select>\n");
        return optionSb.toString();
    }

    /**
     * <pre>
     * 라디오 버튼 control 생성.
     * 
     * 공통코드에 대한 라디오 버튼 컨트롤을 생성하는 스크립트 반환
     * </pre>
     *
     * @param useLang
     * @param name
     * @param grpCd
     * @param checked
     * @return
     */
    public static String generateRadio(String useLang, String name, String grpCd, String checked) {
        return generateRadio(useLang, name, grpCd, checked,  "");
    }

    /**
     * <pre>
     * 라디오 버튼 control 생성.
     * 
     * 공통코드에 대한 라디오 버튼 컨트롤을 생성하는 스크립트 반환
     * </pre>
     *
     * @param useLang
     * @param name
     * @param grpCd
     * @param checked
     * @param style
     * @return
     */
    public static String generateRadio(String useLang, String name, String grpCd, String checked,
            String style) {
        return generateRadio(useLang, name, grpCd, checked,  style, "");
    }

    /**
     * <pre>
     * 라디오 버튼 control 생성.
     * 
     * 공통코드에 대한 라디오 버튼 컨트롤을 생성하는 스크립트 반환
     * </pre>
     *
     * @param useLang
     * @param name
     * @param grpCd
     * @param checked
     * @param style
     * @param onClick
     * @return
     */
    public static String generateRadio(String useLang, String name, String grpCd, String checked,
            String style, String onClick) {
        return generateRadio(useLang, name, grpCd, checked,  style, onClick, "");
    }

    /**
     * <pre>
     * 라디오 버튼 control 생성.
     * 
     * 공통코드에 대한 라디오 버튼 컨트롤을 생성하는 스크립트 반환
     * </pre>
     *
     * @param useLang
     * @param name
     * @param grpCd
     * @param checked
     * @param style
     * @param onClick
     * @param etc
     * @return
     */
    public static String generateRadio(String useLang, String name, String grpCd, String checked,
            String style, String onClick, String etc) {

        StringBuilder radioSb = new StringBuilder();

        List<Map<String, String>> commonCodeList = getCommonCodeList(grpCd);

        for (int i = 0; i < commonCodeList.size(); i++) {
            Map<String, String> codeInfo = (Map<String, String>) commonCodeList.get(i);
            if(StringUtils.equals(codeInfo.get("useYn"), "Y")) {
                radioSb.append("<input type=\"radio\" value=\"").append(codeInfo.get("cdId"))
                        .append("\" name=\"" + name).append("\" id=\"" + name).append("\"")
                        .append(checked.equals(codeInfo.get("cdId")) ? " checked=\"checked\"" : "");

                if (StringUtils.isNotEmpty(style)) {
                    radioSb.append(style);
                }
                if (StringUtils.isNotEmpty(onClick)) {
                    radioSb.append(" onclick='" + onClick + "' ");
                }
                radioSb.append("/> ").append(codeInfo.get(useLang)).append("\n");
            }
        }
        return radioSb.toString();
    }

    /**
     * <pre>
     * 체크박스 control 생성.
     * 
     * 공통코드에 대한 체크박스 컨트롤을 생성하는 스크립트 반환
     * </pre>
     *
     * @param useLang
     * @param name
     * @param grpCd
     * @return
     */
    public static String generateCheck(String useLang, String name, String grpCd) {
        return generateCheck(useLang, name, grpCd,  "");
    }

    /**
     * <pre>
     * 체크박스 control 생성.
     * 
     * 공통코드에 대한 체크박스 컨트롤을 생성하는 스크립트 반환
     * </pre>
     *
     * @param useLang
     * @param name
     * @param grpCd
     * @param checked
     * @return
     */
    public static String generateCheck(String useLang, String name, String grpCd, String checked) {
        return generateCheck(useLang, name, grpCd,  checked, "");
    }

    /**
     * <pre>
     * 체크박스 control 생성.
     * 
     * 공통코드에 대한 체크박스 컨트롤을 생성하는 스크립트 반환
     * </pre>
     *
     * @param useLang
     * @param name
     * @param grpCd
     * @param checked
     * @param onClick
     * @return
     */
    public static String generateCheck(String useLang, String name, String grpCd, String checked,
            String onClick) {
        return generateCheck(useLang, name, grpCd,  checked, onClick, "");
    }

    /**
     * <pre>
     * 체크박스 control 생성.
     * 
     * 공통코드에 대한 체크박스 컨트롤을 생성하는 스크립트 반환
     * </pre>
     *
     * @param useLang
     * @param name
     * @param grpCd
     * @param checked
     * @param onClick
     * @param style
     * @return
     */
    public static String generateCheck(String useLang, String name, String grpCd, String checked,
            String onClick, String style) {
        StringBuilder radioSb = new StringBuilder();

        String[] checkedList = StringUtils.split(checked, ",");

        List<Map<String, String>> commonCodeList = getCommonCodeList(grpCd);

        for (int i = 0; i < commonCodeList.size(); i++) {
            Map<String, String> codeInfo = (Map<String, String>) commonCodeList.get(i);
            if(StringUtils.equals(codeInfo.get("useYn"), "Y")) {
                radioSb.append("<input type=\"checkbox\" value=\"").append(codeInfo.get("cdId"))
                        .append("\" name=\"").append(name).append("\" id=\"").append(name).append("\"");
                if (StringUtils.isNotEmpty(onClick)) {
                    radioSb.append(" onclick=\"" + onClick + "\"");
                }
                if (StringUtils.isNotEmpty(style)) {
                    radioSb.append(style);
                }

                for (int j = 0; j < checkedList.length; j++) {
                    if (checkedList[j].equals(codeInfo.get("cdId"))) {
                        radioSb.append(" checked");
                    }
                }
                radioSb.append("> ").append(codeInfo.get(useLang) + "&nbsp;&nbsp;").append("\n");
            }

        }
        return radioSb.toString();
    }

    /**
     * <pre>
     * 이메일입력용 control 생성.
     * 
     * 공통코드에 등록된 이메일도메인을 사용시 이메일입력 input컨트롤과 콤보박스를 생성하는 스크립트 반환
     * </pre>
     *
     * @param title
     * @param name
     * @param selected
     * @return
     */
    public static String generateEmailSelect(String title, String name, String selected) {
        return generateEmailSelect(title, name, selected, "", "");
    }

    /**
     * <pre>
     * 이메일입력용 control 생성.
     * 
     * 공통코드에 등록된 이메일도메인을 사용시 이메일입력 input컨트롤과 콤보박스를 생성하는 스크립트 반환
     * </pre>
     *
     * @param title
     * @param name
     * @param selected
     * @param onChange
     * @param style
     * @return
     */
    public static String generateEmailSelect(String title, String name, String selected,
            String onChange, String style) {
        return generateEmailSelect(title, name, selected, onChange, style, "N", "", "", "");
    }

    /**
     * <pre>
     * 이메일입력용 control 생성.
     * 
     * 공통코드에 등록된 이메일도메인을 사용시 이메일입력 input컨트롤과 콤보박스를 생성하는 스크립트 반환
     * </pre>
     *
     * @param title
     * @param name
     * @param selected
     * @param onChange
     * @param style
     * @param fullTextYn
     * @param inputNm1
     * @param inputNm2
     * @param inputStyle
     * @return
     */
    public static String generateEmailSelect(String title, String name, String selected,
            String onChange, String style, String fullTextYn, String inputNm1, String inputNm2,
            String inputStyle) {
        StringBuilder selectSb = new StringBuilder();
        String email1 = "";
        String email2 = "";
        List<Map<String, String>> emailHostList = getCommonCodeList("200");

        if (fullTextYn == "Y") {
            String[] email = selected.split("@");
            if (email != null && email.length > 1) {
                email1 = email[0];
                email2 = email[1];
            } else {
                email1 = "";
                email2 = "";
            }
            selectSb.append(" <input type=text name='" + name + "1'")
                    .append(" id='" + name + "1' value='" + email1 + "' " + inputStyle)
                    .append(" > @ ").append(" <input type=text name='" + name + "2'")
                    .append(" id='" + name + "2' value='" + email2 + "' " + inputStyle)
                    .append(" >");
            selected = email2;
        }
        selectSb.append(" <select name=\"").append(name + "_sel").append("\" id=\"")
                .append(name + "_sel").append("\"");

        if (StringUtils.isNotEmpty(onChange)) {
            selectSb.append(" onchange=\"" + onChange + "\"");
        }
        if (StringUtils.isNotEmpty(style)) {
            selectSb.append(style);
        }
        selectSb.append(">\n");
        if (StringUtils.isNotEmpty(title)) {
            selectSb.append("<option value=''>" + title + "</option>\n");
        }

        for (int i = 0; i < emailHostList.size(); i++) {
            Map<String, String> codeInfo = (Map<String, String>) emailHostList.get(i);
            if(StringUtils.equals(codeInfo.get("useYn"), "Y")) {
                selectSb.append("<option value='" + codeInfo.get("cdId") + "'")
                .append(selected.equals(codeInfo.get("cdId")) ? " selected" : "").append(">")
                .append(codeInfo.get("cdId")).append("</option>\n");
            }
        }
        selectSb.append("</select>\n");

        return selectSb.toString();
    }

    /**
     * <pre>
     * 서버 메모리에 등록된 코드찾기.
     * 
     * </pre>
     *
     * @param codeGbn
     * @return
     */
    private static List<Map<String, String>> getCommonCodeList(String codeGbn) {
        try {
            CodeResult cr = CodeDataProxy.findCodeResult(codeGbn);
            return cr.getCodeList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <pre>
     * 서버에 올라온 코드목록에서 특정코드에 대한 명칭 가져오기
     * 
     * </pre>
     *
     * @param useLang
     * @param codeGbn
     * @param code
     * @return
     */
    public static String getCodeName(String useLang, String codeGbn, String code) {

        String returnStr = "";
        returnStr = CodeDataProxy.retrieveCodeName(codeGbn, code, useLang);
        return returnStr;
    }
}
