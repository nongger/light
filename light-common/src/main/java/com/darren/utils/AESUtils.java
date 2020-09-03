package com.darren.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * AES加密
 * @author Darren
 */
public class AESUtils {
    private static final String DEFAULT_CHAR_ENCODING = "utf-8";

    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final String AES_DESC = "AES";

    private static final String defaultKey = "bd04c606507c49e1"; //默认加密key,实际应用需定义在配置中

    /**
     * 加密
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, AES_DESC);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, AES_DESC);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, seckey);// 初始化
            byte[] result = cipher.doFinal(data);
            return result; // 加密
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, AES_DESC);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, AES_DESC);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, seckey);// 初始化
            byte[] result = cipher.doFinal(data);
            return result; // 加密
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!" + e.getMessage(), e);
        }
    }

    public static String encryptToBase64(String data, String key) {
        try {
            byte[] valueByte = encrypt(data.getBytes(DEFAULT_CHAR_ENCODING), key.getBytes(DEFAULT_CHAR_ENCODING));
            return new String(Base64.encodeBase64(valueByte));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }

    }

    public static String decryptFromBase64(String data, String key) {
        try {
            byte[] originalData = Base64.decodeBase64(data.getBytes());
            byte[] valueByte = decrypt(originalData, key.getBytes(DEFAULT_CHAR_ENCODING));
            return new String(valueByte, DEFAULT_CHAR_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    /**
     * 默认AES加密方法
     *
     * @param data
     * @return
     */
    public static String encryptToBase64(String data) {
        return AESUtils.encryptToBase64(data, defaultKey);
    }

    /**
     * AES默认解密办法
     *
     * @param data
     * @return
     */
    public static String decryptFromBase64(String data) {
        return AESUtils.decryptFromBase64(data, defaultKey);
    }

}
