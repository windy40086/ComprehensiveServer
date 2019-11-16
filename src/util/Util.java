package util;

import db.QueryDao;
import inter.IType;

public class Util implements IType {

    public static int getAccountType(String account){
        if (account.contains("@")) {
            return EMAIL;
        } else {
            return PHONE;
        }
    }
}
