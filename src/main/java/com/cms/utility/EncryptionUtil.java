package com.cms.utility;

import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {
	private String encrypt(String clearText, String secretKey) {
        try {
            byte[] bytePass = secretKey.getBytes("utf-8");
            byte[] byteKey = Arrays.copyOf(bytePass, 24);
            // System.out.println(DatatypeConverter.printHexBinary(byteKey));
            
            SecretKey key = new SecretKeySpec(byteKey, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
            byte[] byteText = clearText.getBytes("utf-8");
            byte[] buf = cipher.doFinal(byteText);
            
            byte[] byteBase64 = Base64.getEncoder().encode(buf);
            String data = new String(byteBase64);
            
            return data;
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
    }
    
    private String decrypt(String data, String secretKey) {
        try {
            byte[] byteData = Base64.getDecoder().decode(data.getBytes("utf-8"));
            byte[] bytePass = secretKey.getBytes("utf-8");
            byte[] byteKey = Arrays.copyOf(bytePass, 24);
            
            SecretKey key = new SecretKeySpec(byteKey, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            byte[] byteText = cipher.doFinal(byteData);
            String clearText = new String(byteText, "utf-8");
            
            return clearText;
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
    }
    
    public static void main(String[] args) {
        String clearText = "cek ya";
        String secretKey = "SecretKey";
        
        String data = new EncryptionUtil().encrypt(clearText, secretKey);
        clearText = new EncryptionUtil().decrypt(data, secretKey);
        
        System.out.println("Encrypted String: " + data);
        System.out.println(clearText);
    }
}
