package control.task;

import control.Channel;
import db.QueryDao;
import entity.Message;
import entity.User;
import inter.ITask;
import server.ServerAndroid;
import service.StreamService;
import util.Util;

import java.io.IOException;

public class LoginTask implements ITask {
    @Override
    public boolean doTask(User u, Message mi) {
        System.out.println("LoginTask");

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
            return sendMessage(u, msg.toString());
        }

        //通过LoginService服务读取数据库信息
        if (isUserExist(new User(account, password))) {
            //读取成功后设置 channel 的 user
            u.setAccount(account);
            u.setPassword(password);
            u.setId(getUserID(account));
            msg.setResult(RESULT_SUCCESS);
            msg.setError(ERROR_NONE);
            msg.setUid(getUserID(account));
            System.out.println("登录成功");
        } else {
            //读取失败则返回错误
            msg.setResult(RESULT_FAIL);
            msg.setError(ERROR_LOGIN);
            System.out.println("登陆失败");
        }
        return sendMessage(u, msg.toString());
    }

    //通过User判断数据库是否有此用户
    private static boolean isUserExist(User u) {
        String account = u.getAccount();
        return QueryDao.isAccountCorrect(u, Util.getAccountType(account));
    }

    private static String getUserID(String account) {
        return QueryDao.getUserID(account) + "";
    }

    //判断账号是否已经登录
    private static boolean isUserLogin(String account) {
        for (Channel channel : ServerAndroid.getChannels()) {
            if (null != channel.getUserAccount() && channel.getUserAccount().equals(account)) {
                return true;
            }
        }
        return false;
    }

    //发送登录信息
    private static boolean sendMessage(User u, String message) {
        try {
            StreamService.sendMsg(u.getClient(), message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
