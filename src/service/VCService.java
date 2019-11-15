package service;

import java.util.Date;
import java.util.HashMap;

public class VCService {
    private static HashMap<String, String> PhoneVerificationCode = new HashMap<>();


    static void add(String account, String vc) {
        PhoneVerificationCode.put(account, vc);
    }

    static boolean delete(String account) {
        PhoneVerificationCode.remove(account);
        return true;
    }

    static String getVC(String account) {
        return PhoneVerificationCode.get(account);
    }

    //是否存在
    static boolean isVCExist(String account) {
        return PhoneVerificationCode.get(account) != null;
    }

    //是否正确
    static boolean check(String account, String vc) {
        String getVC = PhoneVerificationCode.get(account);
        if (getVC == null) return false;
        return getVC.equals(vc);
    }

    public static boolean update_vc() {
        System.out.println("验证码清洗中,所有超过5分钟的验证码都会被清理");
        System.out.println("目前的验证码");
        return false;
    }

}