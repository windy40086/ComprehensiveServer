package service;

import util.Log;

import java.util.HashMap;

public class TokenService {
    private static HashMap<String, String> token = new HashMap<>();

    public static void addToken(String uid) {
        String s_token = uid + System.currentTimeMillis();
        token.put(uid, s_token.hashCode() + "");
    }

    public static boolean checkToken(String uid, String s_token) {
        Log.d("uid:" + uid + " token=" + s_token);
        if (token.get(uid) != null)
            return token.get(uid).equals(s_token);
        return false;
    }

    public static String getToken(String uid) {
        return token.get(uid);
    }
}
