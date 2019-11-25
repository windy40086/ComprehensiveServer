package control.task;

import db.QueryDao;
import db.UpdateDao;
import entity.Message;
import entity.User;
import inter.IChannel;
import inter.ITask;
import server.SMSServer;
import service.EmailService;
import service.LogVCService;

import java.util.Random;

public class VCLoginTask implements ITask {

    @Override
    public boolean doTask(IChannel channel, Message message) {
        System.out.println("VCLoginTask");
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
        String account = mi.getAccount();

        //如果包含 @
        if (account.contains("@")) {
            //邮箱登录
            msg = mailboxVerification(u, mi);
        } else {
            //手机号登录
            msg = phoneVerification(u, mi);
        }

        return msg;
    }

    ////////////////////////////////////////////////
    //手机号登录
    private static Message phoneVerification(User u, Message mi) {
        //判断是登录信息还是验证信息
        if (SMSServer.isSMSClientCon()) {
            if (mi.isVCExist()) {
                //如果有验证码，则验证并登录
                return sign_up_by_phone(u, mi);
            } else {
                //没有验证码，则发送验证码
                return VerificationCode_phone(u, mi);
            }
        } else {
            Message m = new Message();
            m.setType(TYPE_ERROR);
            m.setResult(RESULT_FAIL);
            m.setError(ERROR_SMSClient_IS_CLOSE);
            return m;
        }
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

            System.out.println("phone验证码:" + vc);
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

    /////////////////////////////////////////////////
    //邮箱验证
    private static Message mailboxVerification(User u, Message mi) {

        //判断是注册信息还是验证信息
        if (mi.isVCExist()) {
            //如果有验证码，则验证并登录
//            return sign_up_by_email(mi);
        } else {
            //没有注册码，则发送注册码
//            return VerificationCode_email(mi);
        }
        return null;
    }

    //验证
    private static Message sign_up_by_email(User u, Message mi) {
        Message msg = new Message();
        User temp = new User(mi.getAccount(), mi.getPassword());
        String VC = mi.getVc();
        //验证账号
        boolean isVCCorrect = LogVCService.check(temp.getAccount(), VC);
        boolean isAccountExist = QueryDao.isAccountExist(mi.getAccount(), EMAIL);

        //如果账号正确
        if ((!isAccountExist) && isVCCorrect) {
            //创建账号
            msg.setType(TYPE_LOGIN);
            boolean isCreateSuccess = UpdateDao.createAccount(temp);
            if (isCreateSuccess) {
                //创建成功则删除验证码
                LogVCService.delete(temp.getAccount());
                msg.setResult(RESULT_SUCCESS);
                msg.setError(ERROR_NONE);
            } else {
                //创建意外失败
                msg.setType(TYPE_ERROR);
                msg.setResult(RESULT_FAIL);
                msg.setError(ERROR_LOGIN);
            }
        } else {
            //验证码错误
            msg.setType(TYPE_ERROR);
            msg.setResult(RESULT_FAIL);
            msg.setError(ERROR_REGISTER_VERIFICATION_CODE_IS_WRONG);
        }

        return msg;
    }

    //注册信息
    private static Message VerificationCode_email(User u, Message mi) {
        Message msg = new Message();
        String account = mi.getAccount();

        //判断账号密码是否为正确的类型-邮件地址

        //判断账号是否存在
        boolean isAccountExist = QueryDao.isAccountExist(account, EMAIL);
        if (!isAccountExist) {
            //生成验证码
            String vc = getVerificationCode();

            //判断容器内是否含有此验证码
            if (LogVCService.isVCExist(account)) {
                //如果有就直接发送
                EmailService es = new EmailService(account, LogVCService.getVC(account));
                es.sendEmail();
            } else {
                //如果没有直接用生成的验证码 发送验证码邮件
                EmailService es = new EmailService(account, vc);

                boolean isSuccess = es.sendEmail();
                if (!isSuccess) {
                    msg.setType(TYPE_REGISTER);
                    msg.setResult(RESULT_FAIL);
                    msg.setError(ERROR_REGISTER_EMAIL_WRONG);
                    return msg;
                }

                //将验证码和账号加入验证容器中
                LogVCService.add(mi.getAccount(), vc);
            }

            System.out.println("Email验证码:" + vc);
            //发送成功
            msg.setType(TYPE_REGISTER);
            msg.setResult(RESULT_SUCCESS);
            msg.setError(ERROR_NONE);

        } else {
            //发送失败
            msg.setType(TYPE_REGISTER);
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
