package control.task;

import db.QueryDao;
import entity.Message;
import entity.User;
import inter.IChannel;
import inter.ITask;
import server.SMSServer;
import service.LogVCService;
import util.Log;

import java.util.Random;

public class VCLoginTask implements ITask {

    @Override
    public boolean doTask(IChannel channel, Message message) {
        Log.d("VCLoginTask");
        Message result = login(channel.getUser(), message);
        return sendMessage(channel, result.toString());
    }

    //返回消息
    private static boolean sendMessage(IChannel channel, String message) {
        return channel.send(message);
    }

    //转为登录信息
    private static Message login(User u, Message mi) {
        Message msg = new Message();
        msg.setType(mi.getType());
        return phoneVerification(u, mi);
    }

    ////////////////////////////////////////////////
    //手机号登录
    private static Message phoneVerification(User u, Message mi) {
        //判断是登录信息还是验证信息
        if (SMSServer.isSMSClientCon()) {
            if (mi.isVCExist()) {
                //如果有验证码，则验证并登录
//                return sign_up_by_phone(u, mi);
            } else {
                //没有验证码，则发送验证码
//                return VerificationCode_phone(u, mi);
            }
        } else {
            Message m = new Message();
            m.setType(TYPE_ERROR);
            m.setResult(RESULT_FAIL);
            m.setError(ERROR_SMSClient_IS_CLOSE);
            return m;
        }
        return null;
    }

    //验证
    private static Message sign_up_by_phone(User u, Message mi) {
        Message msg = new Message();
        String account = mi.getAccount();
        String VC = mi.getVc();
        //验证账号
        boolean isVCCorrect = LogVCService.check(account, VC);
        boolean isAccountExist = QueryDao.isAccountExist(account, PHONE);

        //如果账号正确
        if (isAccountExist && isVCCorrect) {
            //登录账号
            msg.setType(TYPE_LOGIN);
            u.setId(QueryDao.getUserID(account) + "");
            u.setAccount(account);

            //删除验证码
//            LogVCService.delete(temp.getAccount());
            msg.setResult(RESULT_SUCCESS);
            msg.setError(ERROR_NONE);

        } else {
            //验证码错误
            msg.setType(TYPE_ERROR);
            msg.setResult(RESULT_FAIL);
            msg.setError(ERROR_LOGIN_VERIFICATION_CODE_IS_WRONG);
        }
        return msg;
    }

    //注册信息
    private static Message VerificationCode_phone(User u, Message mi) {
        Message msg = new Message();
        msg.setType(TYPE_LOGIN);
        String account = mi.getAccount();

        //判断账号密码是否为正确的类型-电话号码

        //判断账号是否不存在
        boolean isAccountExist = QueryDao.isAccountExist(account, PHONE);

        if (!isAccountExist) {
            //生成验证码
            String vc = getVerificationCode();

            //判断容器内是否含有此验证码
            if (LogVCService.isVCExist(account)) {
                //如果有就直接发送
                String send = "phoneNumber=" + mi.getAccount() + "&" + "vc=" + vc;
                SMSServer.sendMsg(send);
            } else {
                //如果没有直接用生成的验证码 发送验证码 短信
                String send = "phoneNumber=" + mi.getAccount() + "&" + "vc=" + vc;
                SMSServer.sendMsg(send);

                //将验证码和账号加入验证容器中
                LogVCService.add(mi.getAccount(), vc);
            }

            Log.d("phone验证码:" + vc);
            //发送成功
            msg.setResult(RESULT_SUCCESS);
            msg.setError(ERROR_NONE);

        } else {
            //发送失败
            msg.setResult(RESULT_FAIL);
            msg.setError(ERROR_REGISTER_ACCOUNT_ALREADY_USE);
        }
        return msg;
    }

    ////////////////////////////////////////////////////////////
    //功能库
    //生成四位的验证码
    private static String getVerificationCode() {
//        String list = "AaBbCc123DdEeFfGgHhJjKk4MmNn567OoPpQqEeSsTt89UuVvWwXxYyZz0";
        String list = "0123456789";
        StringBuilder vc = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            vc.append(list.charAt(random.nextInt(list.length() - 1)));
        }
        random.nextInt(list.length());
        return vc.toString();
    }
}
