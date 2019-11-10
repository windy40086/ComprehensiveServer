package db;

import entity.User;

public class UpdateDao {

    public static void login_insert(User u){
        Object[] obj = {u.getAccount(),u.getPassword()};

    }
}
