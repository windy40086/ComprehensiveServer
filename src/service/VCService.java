package service;

import java.util.Date;
import java.util.HashMap;

public class VCService {
    private static HashMap<String, String> VerificationCode = new HashMap<>();


    static void add(String account, String vc) {
        VerificationCode.put(account, vc);
    }

    static boolean delete(String account) {
        VerificationCode.remove(account);
        return true;
    }

    static String getVC(String account) {
        return VerificationCode.get(account);
    }

    //是否存在
    static boolean isVCExist(String account) {
        return VerificationCode.get(account) != null;
    }

    //是否正确
    static boolean check(String account, String vc) {
        String getVC = VerificationCode.get(account);
        if (getVC == null) return false;
        return getVC.equals(vc);
    }

    public static boolean update_vc(int sleep) {
        System.out.println("////////////////短信清洗/////////////////");
        System.out.println("验证码清洗中,所有超过" + sleep + "分钟的验证码都会被清理");
        System.out.println("目前的验证码:" + VerificationCode.toString());
        System.out.println("/////////////////////////////////////////");
        return true;
    }

    public static boolean isVCEmpty() {
        return VerificationCode.isEmpty();
    }
}