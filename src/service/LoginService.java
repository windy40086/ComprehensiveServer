package service;

import db.QueryDao;
import entity.User;

public class LoginService {

    //通过User判断数据库是否有此用户
    public boolean isUserExist(User u){
        return QueryDao.isAccountExist(u);
    }
}
