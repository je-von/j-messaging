package com.example.jmessagingapp.util;

import org.mindrot.jbcrypt.BCrypt;

public class CryptHelper {

    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }
}
