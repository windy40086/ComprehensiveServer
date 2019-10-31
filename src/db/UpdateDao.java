package db;

import entity.User;
import util.DBUtil;

public class UpdateDao {

    public static void login_insert(User u){
        Object[] obj = {u.getAccount(),u.getPassword()};

    }
}
