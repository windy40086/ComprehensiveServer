package service;

import control.Channel;
import db.QueryDao;
import entity.Message;
import entity.MsgInfo;
import entity.User;
import inter.IError;
import inter.IType;
import server.Server;

public class LoginService implements IType, IError {

    //通过User判断数据库是否有此用户
    private static boolean isUserExist(User u) {
        return QueryDao.isAccountExist(u);
    }

    //判断账号是否已经登录
    private static boolean isUserLogin(String account) {
        for (Channel channel : Server.getChannels()) {
            if (null != channel.getUserAccount() && channel.getUserAccount().equals(account)) {
                return true;
            }
        }
        return false;
    }

    //转为登录信息

    /**
     * @param u  未登陆的channel的u，传入后如登陆成功则设置u的值
     * @param mi 包含客户端传入的信息
     * @return 返回登录结果
     */
    public static Message toLoginMsg(User u, MsgInfo mi) {

        Message msg = new Message();
        //返回的消息为登录信息
        msg.setType(mi.getType());

        //获取账号密码
        String account = mi.getAccount();
        String password = mi.getPassword();

        //需要判断账号到底是Email还是phone

        //判断此account是否已经登录
        if (false && isUserLogin(account)) {
            msg.setResult(RESULT_FAIL);
            msg.setError(ERROR_LOGIN_ACCOUNT_IS_LOGIN);
            return msg;
        }

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
