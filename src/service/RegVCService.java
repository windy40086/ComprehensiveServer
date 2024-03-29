package service;

import util.Log;

import java.util.HashMap;

public class RegVCService {
    private static HashMap<String, String> verificationCode = new HashMap<>();

    public static void add(String account, String vc) {
        verificationCode.put(account, vc);
    }

    public static boolean delete(String account) {
        verificationCode.remove(account);
        return true;
    }

    public static String getVC(String account) {
        return verificationCode.get(account);
    }

    //是否存在
    public static boolean isVCExist(String account) {
        return verificationCode.get(account) != null;
    }

    //是否正确
    public static boolean check(String account, String vc) {
        String getVC = verificationCode.get(account);
        if (getVC == null) return false;
        return getVC.equals(vc);
    }

    public static boolean update_vc(int sleep) {
        Log.d("////////////////短信清洗/////////////////");
        Log.d("验证码清洗中,所有超过" + sleep + "分钟的验证码都会被清理");
        Log.d("目前的验证码:" + verificationCode.toString());
        Log.d("/////////////////////////////////////////");
        return true;
    }

    public static boolean isVCEmpty() {
        return verificationCode.isEmpty();
    }
}