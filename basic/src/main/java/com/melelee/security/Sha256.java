package com.melelee.security;

import cn.hutool.core.util.HexUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author menglili
 */
public class Sha256 {
    public static final String MESSAGE = "顶顶顶顶顶顶顶顶顶顶顶顶顶反反复复烦烦烦烦烦烦烦烦烦烦烦烦烦烦烦";
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        sha256();
        hmacSha256("123");
    }

    private static void sha256() throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        byte[] hash = instance.digest(MESSAGE.getBytes(StandardCharsets.UTF_8));
        String hexStr = HexUtil.encodeHexStr(hash);
        System.out.println("9dc1bb848a1e59845ffbe34cb640431287be684b22458d92164af47a0a93209b".equals(hexStr));
    }

    public static void hmacSha256(String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        hmacSha256.init(secretKey);

        byte[] bytes = hmacSha256.doFinal(MESSAGE.getBytes());
        String hexStr = HexUtil.encodeHexStr(bytes);
        System.out.println("7d54cfb8b36e3f28da9cf9a9c0675ecb3db14b6115654d96d4d568001e2457e9".equals(hexStr));
    }
}



