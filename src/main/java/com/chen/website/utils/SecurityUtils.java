package com.chen.website.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 安全工具类
 * @author ChenetChen
 * @since 2020/8/19 14:09
 */
public class SecurityUtils {

    /**
     * md5加密
     * @param str 明文
     * @return 密文
     */
    public static String MD5Encrypt(String str){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] byteDigest = messageDigest.digest();
            int i;
            StringBuffer buffer = new StringBuffer("");
            for (int offset = 0;offset < byteDigest.length;offset++){
                i = byteDigest[offset];
                if (i < 0){
                    i +=256;
                }
                if (i < 16){
                    buffer.append("0");
                }
                buffer.append(Integer.toHexString(i));
            }
            //32位加密
            return buffer.toString();
            //16位
            //return buffer.toString().substring(8,24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用户名校验
     * @param username 用户名
     * @return 结果
     */
    public static boolean checkUsername(String username){
        return username.matches("\\w+");
    }

    /**
     * 密码校验
     * @param password 密码
     * @return 结果
     */
    public static boolean checkPassword(String password){
        return password.matches("\\w{8}");
    }

    /**
     * 生成随机指定字长的字符串
     * @param length 字长
     * @return 字符串
     */
    public static String randomKey(int length){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}