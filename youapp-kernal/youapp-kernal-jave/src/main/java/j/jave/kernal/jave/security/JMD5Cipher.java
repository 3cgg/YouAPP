package j.jave.kernal.jave.security;

import j.jave.kernal.jave.security.exception.JSecurityException;

import java.security.MessageDigest;

public class JMD5Cipher {

	
	 /*** 
     * encrypted by MD5
     * @param 
     * @return  32 bit string returned
     */
    public static String encrypt(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new JSecurityException(e);
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
	
}
