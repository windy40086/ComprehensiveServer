package service;

import db.QueryDao;
import entity.Message;
import entity.MsgInfo;
import entity.User;
import inter.IError;
import inter.IType;

public class LoginService implements IType, IError {

    //通过User判断数据库是否有此用户
    private static boolean isUserExist(User u) {
        return QueryDao.isAccountExist(u);
    }

    //转为登录信息
    public static Message toLoginMsg(User u, MsgInfo mi) {

        Message msg = new Message();
        msg.setType(mi.getType());
        //判断账号密码
        String account = mi.getAccount();
        String password = mi.getPassword();

        //需要判断账号到底是Email还是phone

        //判断此account是否已经登录

        //通过LoginService服务读取数据库信息
        if (isUserExist(new User(account, password))) {
            //读取成功后设置 channel 的 user
            u.setAccount(account);
            u.setPassword(password);
            msg.setResult(RESULT_SUCCESS);
            msg.setError(ERROR_NONE);
            System.out.println("登录成功");
            return msg;
        } else {
            //读取失败则返回错误
            msg.setResult(RESULT_FAIL);
            msg.setError(ERROR_LOGIN);
            return msg;
        }
    }

}
