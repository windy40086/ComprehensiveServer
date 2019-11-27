package control.task;

import db.QueryDao;
import db.UpdateDao;
import entity.Message;
import entity.User;
import inter.IChannel;
import inter.ITask;
import server.SMSServer;
import service.EmailService;
import service.RegVCService;
import util.Log;

import java.util.Random;

public class RegisterTask implements ITask {
    @Override
    public boolean doTask(IChannel channel, Message message) {
        Log.d("RegisterTask");
        Message result = register(message);
        return sendMessage(channel,result.toString());
    }

    //返回消息
    private static boolean sendMessage(IChannel channel, String message){
        return channel.send(message);
    }

    //转为注册信息
    private static Message register(Message mi) {
        Message msg = new Message();
        msg.setType(mi.getType());
        String account = mi.getAccount();

        //如果包含 @
        if (account.contains("@")) {
            //邮箱注册
            msg = mailboxVerification(mi);
        }else {
            //手机号注册
            msg = phoneVerification(mi);
        }

        return msg;
    }

    ////////////////////////////////////////////////
    //手机号注册
    private static Message phoneVerification(Message mi) {
        //判断是注册信息还是验证信息
        if (SMSServer.isSMSClientCon()){
            if (mi.isVCExist()) {
                //如果有验证码，则验证并注册
                return sign_up_by_phone(mi);
            } else {
                //没有注册码，则发送注册码
                return VerificationCode_phone(mi);
            }
        }else{
            Message m = new Message();
            m.setType(TYPE_ERROR);
            m.setResult(RESULT_FAIL);
            m.setError(ERROR_SMSClient_IS_CLOSE);
            return m;
        }
    }

    //验证
    private static Message sign_up_by_phone(Message mi) {
        Message msg = new Message();
        User temp = new User(mi.getAccount(), mi.getPassword());
        String VC = mi.getVc();
        //验证账号
        boolean isVCCorrect = RegVCService.check(temp.getAccount(), VC);
        boolean isAccountExist = QueryDao.isAccountExist(temp.getAccount(),PHONE);

        //如果账号正确
        if ((!isAccountExist)&&isVCCorrect) {
            //创建账号
            msg.setType(TYPE_REGISTER);
            boolean isCreateSuccess = UpdateDao.createAccount(temp);
            if (isCreateSuccess) {
                //创建成功则删除验证码
                RegVCService.delete(temp.getAccount());
                msg.setResult(RESULT_SUCCESS);
                msg.setError(ERROR_NONE);
            } else {
                //创建意外失败
                msg.setResult(RESULT_FAIL);
                msg.setError(ERROR_REGISTER_FAIL);
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
    private static Message VerificationCode_phone(Message mi) {
        Message msg = new Message();
        msg.setType(TYPE_REGISTER);
        String account = mi.getAccount();

        //判断账号密码是否为正确的类型-电话号码

        //判断账号是否不存在
        boolean isAccountExist = QueryDao.isAccountExist(account,PHONE);

        if (!isAccountExist) {
            //生成验证码
            String vc = getVerificationCode();

            //判断容器内是否含有此验证码
            if (RegVCService.isVCExist(account)) {
                //如果有就直接发送
                String send = "phoneNumber=" + mi.getAccount() + "&" + "vc=" + vc;
                SMSServer.sendMsg(send);
            } else {
                //如果没有直接用生成的验证码 发送验证码 短信
                String send = "phoneNumber=" + mi.getAccount() + "&" + "vc=" + vc;
                SMSServer.sendMsg(send);

                //将验证码和账号加入验证容器中
                RegVCService.add(mi.getAccount(), vc);
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

    /////////////////////////////////////////////////
    //邮箱验证
    private static Message mailboxVerification(Message mi) {

        //判断是注册信息还是验证信息
        if (mi.isVCExist()) {
            //如果有验证码，则验证并注册
            return sign_up_by_email(mi);
        } else {
            //没有注册码，则发送注册码
            return VerificationCode_email(mi);
        }
    }

    //验证
    private static Message sign_up_by_email(Message mi) {
        Message msg = new Message();
        User temp = new User(mi.getAccount(), mi.getPassword());
        String VC = mi.getVc();
        //验证账号
        boolean isVCCorrect = RegVCService.check(temp.getAccount(), VC);
        boolean isAccountExist = QueryDao.isAccountExist(mi.getAccount(),EMAIL);

        //如果账号正确
        if ((!isAccountExist)&&isVCCorrect) {
            //创建账号
            msg.setType(TYPE_REGISTER);
            boolean isCreateSuccess = UpdateDao.createAccount(temp);
            if (isCreateSuccess) {
                //创建成功则删除验证码
                RegVCService.delete(temp.getAccount());
                msg.setResult(RESULT_SUCCESS);
                msg.setError(ERROR_NONE);
            } else {
                //创建意外失败
                msg.setType(TYPE_ERROR);
                msg.setResult(RESULT_FAIL);
                msg.setError(ERROR_REGISTER_FAIL);
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
    private static Message VerificationCode_email(Message mi) {
        Message msg = new Message();
        String account = mi.getAccount();

        //判断账号密码是否为正确的类型-邮件地址


        //判断账号是否存在
        boolean isAccountExist = QueryDao.isAccountExist(account,EMAIL);
        if (!isAccountExist) {
            //生成验证码
            String vc = getVerificationCode();

            //判断容器内是否含有此验证码
            if (RegVCService.isVCExist(account)) {
                //如果有就直接发送
                EmailService es = new EmailService(account, RegVCService.getVC(account));
                es.sendEmail();
            } else {
                //如果没有直接用生成的验证码 发送验证码邮件
                EmailService es = new EmailService(account, vc);

                boolean isSuccess = es.sendEmail();
                if(!isSuccess){
                    msg.setType(TYPE_REGISTER);
                    msg.setResult(RESULT_FAIL);
                    msg.setError(ERROR_REGISTER_EMAIL_WRONG);
                    return msg;
                }

                //将验证码和账号加入验证容器中
                RegVCService.add(mi.getAccount(), vc);
            }

            Log.d("Email验证码:" + vc);
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
