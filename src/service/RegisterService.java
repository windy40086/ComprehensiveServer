package service;

import entity.Message;
import entity.MsgInfo;
import entity.User;
import inter.IError;
import inter.IType;
import util.DBUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class RegisterService implements IType, IError {

    //转为注册信息
    public static Message toRegisterMsg(User u, MsgInfo mi) {
        Message msg = new Message();
        msg.setType(mi.getType());
        User temp = new User(mi.getAccount(), mi.getPassword());
        //判断账号密码是否为正确的类型-邮件地址


        //判断账号是否存在
        if (!isAccountExist(u.getAccount())) {
            //验证码
            String vc = getVerificationCode();
            String vcCheck = null;

            //发送验证码邮件
            EmailService es = new EmailService(temp.getAccount(), vc);
            es.sendEmail();

            //获取验证码
            System.out.println(u.getClient() + "等待验证码");
            try {
                vcCheck = StreamService.reciMsg(u.getClient());
            } catch (IOException e) {
                System.err.println("客户端关闭了连接");
                return null;
            }

            //等待5分钟


            //如果验证码有问题
            if (!vcCheck.equals(vc)) {
                System.out.println(vc + " != " + vcCheck);
                msg.setType(TYPE_REGISTER);
                msg.setResult(RESULT_FAIL);
                msg.setError(IError.ERROR_REGISTER_VERIFICATION_CODE_IS_WRONG);
                return msg;
            }

            System.out.println(vc + vcCheck);

            //创建账号
            msg.setType(TYPE_REGISTER);
            boolean isCreateSuccess = createAccount(temp);
            if (isCreateSuccess) {
                msg.setResult(RESULT_SUCCESS);
                msg.setError(ERROR_NONE);
            } else {
                msg.setResult(RESULT_FAIL);
                msg.setError(ERROR_REGISTER_FAIL);
            }
        } else {
            msg.setType(TYPE_REGISTER);
            msg.setResult(RESULT_FAIL);
            msg.setError(ERROR_REGISTER_ACCOUNT_ALREADY_USE);
        }
        return msg;
    }

    //查看数据库是否存在account
    private static boolean isAccountExist(String account) {
        String sql = "select * from client where account = ?";
        Object[] params = {account};
        ResultSet rs = DBUtil.executeQuery(sql, params);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            return true;
        }
        return false;
    }

    //在数据库中建立用户
    private static boolean createAccount(User u) {
        String sql = "insert into client(account,password,jurisdiction) values(?,?,?)";
        Object[] params = {u.getAccount(), u.getPassword(), 0};
        boolean isSuccess;
        isSuccess = DBUtil.executeUpdate(sql, params);
        if (!isSuccess) return false;
        sql = "insert into user_profile(account,email) values(?,?)";
        params = new Object[]{u.getAccount(), u.getAccount()};
        isSuccess = DBUtil.executeUpdate(sql, params);
        return isSuccess;
    }

    //生成四位的验证码
    private static String getVerificationCode() {
        String list = "AaBbCc123DdEeFfGgHhJjKk4MmNn567OoPpQqEeSsTt89UuVvWwXxYyZz0";
        String vc = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            vc += list.charAt(random.nextInt(list.length() - 1));
        }
        random.nextInt(list.length());
        return vc;
    }
}
