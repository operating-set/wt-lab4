package com.application.service.hashing.impl;

import com.application.service.hashing.HashingService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Hashing implements HashingService {
    public SHA256Hashing() {
    }

    public String hashing(String password) {
        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (!password.isEmpty()){
            byte[] password256 = sha256.digest(password.trim().getBytes());
            StringBuilder passMD5 = new StringBuilder();
            for (byte b : password256) {
                passMD5.append(String.format("%02X", b));
            }
            return passMD5.toString();
        } else {
            return "";
        }

    }

    public static boolean isValid(String password){
        return password.matches("[A-F0-9]{64}");
    }
}
