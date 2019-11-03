package control;

import entity.Message;
import entity.MsgInfo;
import inter.IError;
import inter.IType;

import java.util.Date;

class Analyze implements IType, IError {

    //分析数据的发送方
    static Message analyzeMessage(String text) {

        Message message = new Message();
        String[] strs = text.split("&");
        MsgInfo mi = new MsgInfo();

        for (String str : strs) {
            String[] result = str.split("=");
            mi.setTime(new Date().getTime() + "");
            String key = result[0];
            String value = result[1];
            switch (key) {
                case TYPE:
                    mi.setType(value);
                    break;
                case ACCOUNT:
                    mi.setAccount(value);
                    break;
                case MSG:
                    mi.setMsg(value);
                    break;
                case PASSWORD:
                    mi.setPassword(value);
                    break;
                case RECEIVE:
                    mi.setReceive(value);
                    break;
                case ERR:
                    mi.setError(value);
                default:
                    break;
            }
        }

        //判断消息是否是用户消息
        switch (mi.getType()) {
            case TYPE_RELAY:
                message = toRelayMsg(mi);
                break;
            //登陆消息
            case TYPE_LOGIN:
                message = toLoginMsg(mi);
                break;
            //注册消息
            case TYPE_REGISTER:
                message = toRegisterMsg(mi);
                break;
            //系统消息
            case TYPE_SYSTEM:
                message = toSystemMsg(mi);
                break;
            default:
                break;
        }

        System.out.println("返回消息:" + message.getString());

        return message;
    }

    //将分析的信息进行转换
    //转为用户信息
    private static Message toRelayMsg(MsgInfo mi) {
        Message msg = new Message();
        msg.setType(mi.getType());
        msg.setAccount(mi.getAccount());
        msg.setMsg(mi.getMsg());
        return msg;
    }

    //转为登录信息
    private static Message toLoginMsg(MsgInfo mi) {
        Message msg = new Message();
        msg.setType(mi.getType());
        //判断账号密码

        //这里需要数据库读取判断账号密码

        //需要判断账号到底是Email还是phone
        if (mi.getAccount().equals("123") && mi.getPassword().equals("456")) {

            msg.setResult("1");
            msg.setError(ERROR_NONE + "");
            return msg;
        } else {
            msg.setResult("0");
            msg.setError(ERROR_LOGIN + "");
            return msg;
        }
    }

    //转为注册信息
    private static Message toRegisterMsg(MsgInfo mi) {
        Message msg = new Message();
        return msg;
    }

    //转为系统信息
    private static Message toSystemMsg(MsgInfo mi) {
        Message msg = new Message();
        return msg;
    }

    //转为错误信息

}
