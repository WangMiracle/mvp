package com.miracle.basemvp.util;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Base64;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SecretTool {

    public static String encrypt(String context, String key) {
        try {
            byte[] encrypt = DESUtil.encrypt(context.getBytes("UTF-8"), key);
            String encryptBASE64 = DESUtil.encryptBASE64(encrypt);
            return encryptBASE64;
        } catch (Exception e) {
            throw new RuntimeException("GENERATING SIGN ERROR", e);
        }
    }

    public static String decrypt(String context, String key) {
        try {
            byte[] decryptBASE64 = DESUtil.decryptBASE64(context);
            byte[] decrypt = DESUtil.decrypt(decryptBASE64, key);
            return new String(decrypt, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("GENERATING SIGN ERROR", e);
        }
    }

    public static String generatingSign(String context) {
        try {
            return MD5Util.getMD5(context);
        } catch (Exception e) {
            throw new RuntimeException("GENERATING SIGN ERROR", e);
        }
    }

    public static boolean checkSign(String context, String context1) {
        if (context == null || "".equals(context.trim()) || context1 == null || "".equals(context1.trim())) {
            return false;
        }
        if (context.equals(context1)) {
            return true;
        }
        return false;
    }

    public static String getServerKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String encryptServerKey(String serverKey, String clientKey) {
        try {
            return Base64.getEncoder().encodeToString(DESUtil.encrypt(serverKey.getBytes("UTF-8"), clientKey));
        } catch (Exception e) {
            throw new RuntimeException("GENERATING SIGN ERROR", e);
        }
    }

    public static String getToken(String key) {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
