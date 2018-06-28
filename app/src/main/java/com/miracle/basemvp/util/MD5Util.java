package com.miracle.basemvp.util;



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {

	public static String getMD5(String text) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};         
        byte[] btInput = text.getBytes();  
        MessageDigest mdInst = MessageDigest.getInstance("MD5");  
        mdInst.update(btInput);  
        byte[] md = mdInst.digest();  
        int j = md.length;  
        char str[] = new char[j * 2];  
        int k = 0;  
        for (int i = 0; i < j; i++) {  
            byte byte0 = md[i];  
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
            str[k++] = hexDigits[byte0 & 0xf];  
        }  
        return new String(str);
	}

	public static String getSHA(String text) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};         
        byte[] btInput = text.getBytes();  
        MessageDigest mdInst = MessageDigest.getInstance("SHA");  
        mdInst.update(btInput);  
        byte[] md = mdInst.digest();  
        int j = md.length;  
        char str[] = new char[j * 2];  
        int k = 0;  
        for (int i = 0; i < j; i++) {  
            byte byte0 = md[i];  
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
            str[k++] = hexDigits[byte0 & 0xf];  
        }  
        return new String(str);
	}



}
