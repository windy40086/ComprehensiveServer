package control;

import db.UpdateDao;
import entity.Message;
import entity.MsgInfo;
import entity.User;
import inter.IError;
import inter.IType;
import service.HistoryService;
import service.LoginService;
import service.RegisterService;

import java.util.Date;

class Analyze implements IType, IError {

    //分析数据的发送方
    static Message analyzeMessage(User u, String text) {

        String[] strs = text.split("&");

        //解析字段
        MsgInfo mi = analyzeMsgToMI(strs);
        Message message;

        //分析消息是否正确
        if (isMsgCorrect(mi)) {
            //通过消息类型判断返回的Message形式
            message = MItoMsg(u, mi);
        } else {
            //消息不正确
            mi.setType(TYPE_ERROR);
            message = MItoMsg(u, mi);
        }

        if (null != message) {
            //Log
            System.out.println("返回消息:" + message.toString());
            return message;
        } else {
            return null;
        }
    }


    //解析字段函数 -> 分解为MsgInfo ->转变为各自的类
    private static MsgInfo analyzeMsgToMI(String[] strs) {
        MsgInfo mi = new MsgInfo();
        try {
            //解析字段
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
                    case RECEIVER:
                        mi.setReceive(value);
                        break;
                    case ERR:
                        mi.setError(value);
                        break;
                    case VC:
                        mi.setVc(value);
                    default:
                        break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //用户发送的信息不规范
            mi.setType(TYPE_SYSTEM);
            mi.setError(ERROR_MSG_CANT_ANALYZE + "");
        }
        return mi;
    }

    //通过MsgInfo 合并信息
    private static Message MItoMsg(User u, MsgInfo mi) {
        Message message = new Message();
        //判断消息类型
        switch (mi.getType()) {
            case TYPE_RELAY:
                System.out.println("转发消息");
                message = toRelayMsg(mi);
                break;
            //登陆消息
            case TYPE_LOGIN:
                System.out.println("登录消息");
                message = LoginService.toLoginMsg(u, mi);
                break;
            //注册消息
            case TYPE_REGISTER:
                System.out.println("注册消息");
                message = RegisterService.toRegisterMsg(mi);
                break;
            //系统消息
            case TYPE_SYSTEM:
                System.out.println("系统消息");
                message = toSystemMsg(u, mi);
                break;
            //错误信息
            case TYPE_ERROR:
                System.out.println("错误消息");
                message = toErrorMsg(mi);
                break;
            default:
                break;
        }
        return message;
    }

    //转为错误信息
    private static Message toErrorMsg(MsgInfo mi) {
        Message msg = new Message();
        msg.setType(mi.getType());
        msg.setError(ERROR_MSG_CANT_ANALYZE);
        return msg;
    }

    //分析消息是否正确
    private static boolean isMsgCorrect(MsgInfo mi) {
        //requestType 一定有
        if (mi.getType() == null) {
            return false;
        }
        switch (mi.getType()) {
            //登录信息
            case TYPE_LOGIN:
                //用户注册信息
                return mi.isAccountExist() && mi.isPasswordExist();
            case TYPE_REGISTER:
                return (mi.isAccountExist()) || (mi.isAccountExist() && mi.isVCExist() && mi.isPasswordExist());
            //用户转发信息
            case TYPE_RELAY:
                return mi.isAccountExist() && mi.isReceiveExist()
                        && mi.isMsgExist();
            default:
                return false;
        }
    }

    //将分析的信息进行转换
    //转为用户信息
    public static Message toRelayMsg(MsgInfo mi) {
        //这里需要通过User来过度
        Message msg = new Message();
        msg.setType(mi.getType());
        msg.setAccount(mi.getAccount());
        msg.setReceiver(mi.getReceive());
        msg.setMsg(mi.getMsg());

        //通过Account | Receiver记录历史记录
        HistoryService.insertHM(msg.getAccount(), msg.getReceiver(), msg.getMsg(), msg.getType());
        HistoryService.insertHM(msg.getReceiver(), msg.getAccount(), msg.getMsg(), msg.getType());

        return msg;
    }

    //转为系统信息
    private static Message toSystemMsg(User u, MsgInfo mi) {
        Message msg = new Message();
        msg.setType(mi.getType());
        return msg;
    }
}