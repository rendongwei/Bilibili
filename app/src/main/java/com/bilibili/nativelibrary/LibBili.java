package com.bilibili.nativelibrary;


import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class LibBili {
    static {
        System.loadLibrary("bili");
    }

    public static String c(String paramString) {
        return a(paramString);
    }

    @Deprecated
    public static String a() {
        return c("android");
    }

    private static native String a(String paramString);

    public static byte[] a(String paramString, byte[] paramArrayOfByte)
            throws InvalidKeyException {
        try {
            byte[] arrayOfByte1 = paramString.getBytes("UTF-8");
            IvParameterSpec localIvParameterSpec = b(paramString);
            try {
                byte[] arrayOfByte2 = a(new SecretKeySpec(Arrays.copyOf(arrayOfByte1, 16), "AES"), localIvParameterSpec, paramArrayOfByte);
                return arrayOfByte2;
            } catch (Exception localException) {
                return paramArrayOfByte;
            }
        } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
        }
        return paramArrayOfByte;
    }

    public static byte[] a(SecretKey paramSecretKey, IvParameterSpec paramIvParameterSpec, byte[] paramArrayOfByte) throws GeneralSecurityException {
        try {
            Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            localCipher.init(1, paramSecretKey, paramIvParameterSpec);
            byte[] arrayOfByte = localCipher.doFinal(paramArrayOfByte);
            return arrayOfByte;
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            throw new AssertionError(localNoSuchAlgorithmException);
        } catch (NoSuchPaddingException localNoSuchPaddingException) {
        }
        return paramArrayOfByte;
    }

    private static native IvParameterSpec b(String paramString)
            throws InvalidKeyException;

    public static native int getCpuCount();

    @Deprecated
    public static native int getCpuId();

    public static native SignedQuery s(SortedMap<String, String> map);

}