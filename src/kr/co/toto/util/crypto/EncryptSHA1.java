/*
 * Copyright (c) 2007-2010 Phronis, Co., Ltd.
 *
 * All Rights Reserved. Unpublished rights reserved under the copyright laws
 * of the South Korea. The software contained on this media is proprietary
 * to and embodies the confidential technology of Phronis, Co., Ltd. The
 * possession or receipt of this information does not convey any right to disclose
 * its contents, reproduce it, or use, or license the use, for manufacture or
 * sale, the information or anything described therein. Any use, disclosure, or
 * reproduction without Phronis's prior written permission is strictly prohibited.
 */
package kr.co.toto.util.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * 첫째줄에는 클래스에 대한 설명을 요약해서 적습니다. 줄바꿈을 위한 <BR> 태그를 절대로 사용하지 마시기 바랍니다. 그냥 엔터치세요.
 * 
 * 줄바꿈한 이 줄부터는 상세한 기능을 번호를 붙여서 적습니다. 아래는 그 예입니다.
 * 1. 전표 번호 채번
 * 2. 계좌 번호 채번
 * 3. 참조 번호 채번
 * </pre>
 *
 * @title EcryptSHA1.java
 * @project SMBC_IBS
 * @date 2011. 9. 27. 오후 4:01:17
 * @version 1.0
 * @author Administrator
 *
 */
public class EncryptSHA1 {
    /**
     * <pre>
     * 평문을 SHA1으로 변환합니다.
     * </pre>
     * 
     * @param plantext
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
	public static String getEncryptSHA1(String plantext)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(plantext.getBytes("iso-8859-1"), 0, plantext.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);

    }
    
    
    /**
     * <pre>
     * Byte를 String 으로 변환합니다.
     * </pre>
     * 
     * @param data
     * @return
     */
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}
