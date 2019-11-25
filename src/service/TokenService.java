package service;

import java.util.HashMap;

public class TokenService {
    private static HashMap<String, String> token = new HashMap<>();

    public static void addToken(String uid) {
        String s_token = uid + System.currentTimeMillis();
        token.put(uid, s_token.hashCode() + "");
    }

    public static boolean checkToken(String uid,String s_token){
        return token.get(uid).equals(s_token);
    }

    public static String getToken(String uid){
        return token.get(uid);
    }
}
