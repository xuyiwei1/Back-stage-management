package com.itheima.ssm.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtils {

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encodeString(String password) {

        String encodePassword = bCryptPasswordEncoder.encode(password);

        return encodePassword;

    }

    public static void main(String[] args) {
        String s = encodeString("123"); //$2a$10$lHd5YAKFTuqkrx2pjJHmmu2TSBQ1qAgNM.J0gwAq95WxJCdzAuuS.
        System.out.println(s);
    }

}
