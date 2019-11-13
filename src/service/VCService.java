package service;

import java.util.HashMap;

public class VCService {
    private static HashMap<String, String> PhoneVerificationCode = new HashMap<>();


    static void add(String account, String vc) {
        PhoneVerificationCode.put(account, vc);
    }

    public static boolean delete(String account) {
        PhoneVerificationCode.remove(account);
        return true;
    }

    public static String getVC(String account) {
        return PhoneVerificationCode.get(account);
    }

    static boolean isVCExist(String account) {
        return PhoneVerificationCode.get(account) != null;
    }

    static boolean check(String account, String vc) {
        String getVC = PhoneVerificationCode.get(account);
        if(getVC == null) return false;
        return getVC.equals(vc);
    }


}

class PhoneVC{

}

class EmailVC{
    private static HashMap<String, String> EmailVerificationCode = new HashMap<>();
}