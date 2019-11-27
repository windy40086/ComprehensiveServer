package control.task;

import db.QueryDao;
import entity.Message;
import entity.User;
import inter.IChannel;
import inter.ITask;
import server.ChannelServer;
import service.TokenService;
import util.Log;
import util.Util;

public class PasswordLoginTask implements ITask {
    @Override
    public boolean doTask(IChannel channel, Message mi) {
        Log.d("LoginTask");

        Message msg = new Message();
        //返回的消息为登录信息
        msg.setType(mi.getType());

        //获取账号密码
        String account = mi.getAccount();
        String password = mi.getPassword();

        //判断此account是否已经登录
        //待修复
        if (isUserLogin(account)) {
            Log.d("账号已经登录,即将更新token");
        }

        //通过LoginService服务读取数据库信息
        if (isUserExist(new User(account, password))) {
            //读取成功后设置 channel 的 user
            channel.getUser().setAccount(account);
            channel.getUser().setPassword(password);
            channel.getUser().setId(getUserID(account));
            msg.setResult(RESULT_SUCCESS);
            msg.setError(ERROR_NONE);
            msg.setUid(getUserID(account));

            TokenService.addToken(channel.getUser().getId());

            msg.setToken(TokenService.getToken(channel.getUser().getId()));

            Log.d("登录成功");
        } else {
            //读取失败则返回错误
            msg.setResult(RESULT_FAIL);
            msg.setError(ERROR_LOGIN);
            Log.e("登陆失败");
        }
        return sendMessage(channel, msg.toString());
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
        for (IChannel channel : ChannelServer.getChannels()) {
            if (null != channel.getUserAccount() && channel.getUserAccount().equals(account)) {
                return true;
            }
        }
        return false;
    }

    //发送登录信息
    private static boolean sendMessage(IChannel channel, String message) {
       return channel.send(message);
    }
}
