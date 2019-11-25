package control;

import control.task.*;
import entity.Message;
import inter.IError;
import inter.ITask;
import inter.IType;

import java.util.Date;

class Analyze implements IType, IError {

    static ITask analyze(String m) {
        Message message = getMessage(m);
        if (isMsgCorrect(message)) {
            return getTask(message);
        }
        return new ErrorTask();
    }

    //获取类型 -> 执行Task
    private static ITask getTask(Message m) {
        //判断消息类型
        switch (m.getType()) {
            case TYPE_RELAY:
                System.out.println("转发消息");
                return new RelayTask();
            //登陆消息
            case TYPE_LOGIN:
                System.out.println("登录消息");
                return new PasswordLoginTask();
            case TYPE_LOGIN_TOKEN:
                System.out.println("token登录消息");
                return new TokenLoginTask();
            case TYPE_LOGIN_VC:
                System.out.println("vc登录消息");
                return null;
            //注册消息
            case TYPE_REGISTER:
                System.out.println("注册消息");
                return new RegisterTask();
            //系统消息
            case TYPE_SYSTEM:
                System.out.println("系统消息");
                return new SystemTask();
            //错误信息
            case TYPE_ERROR:
                System.out.println("错误消息");
                return new ErrorTask();
            //历史消息
            case TYPE_HISTORT:
                System.out.println("历史消息获取");
                return new HistoryTask();
            default:
                break;
        }
        return null;
    }

    //解析字段函数 -> 分解为Message -> 返回整合后的Message
    static Message getMessage(String text) {
        String[] strs = text.split("&");
        Message me = new Message();
        try {
            //解析字段
            for (String str : strs) {
                String[] result = str.split("=");
                me.setTime(new Date().getTime() + "");
                String key = result[0];
                String value = result[1];
                switch (key) {
                    case STRING_TYPE:
                        me.setType(value);
                        break;
                    case STRING_ACCOUNT:
                        me.setAccount(value);
                        break;
                    case STRING_PASSWORD:
                        me.setPassword(value);
                        break;
                    case STRING_MSG:
                        me.setMsg(value);
                        break;
                    case STRING_RECEIVER:
                        me.setReceiver(value);
                        break;
                    case STRING_ERR:
                        me.setError(value);
                        break;
                    case STRING_VC:
                        me.setVc(value);
                        break;
                    case STRING_CURSOR:
                        me.setCursor(value);
                        break;
                    case STRING_UID:
                        me.setUid(value);
                        break;
                    case STRING_TOKEN:
                        me.setToken(value);
                        break;
                    default:
                        break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //用户发送的信息不规范
            me.setType(TYPE_SYSTEM);
            me.setError(ERROR_MSG_CANT_ANALYZE);
        }
        return me;
    }

    //判断消息是否正确
    private static boolean isMsgCorrect(Message mi) {
        //requestType 一定有
        if (mi.getType() == null) {
            return false;
        }
        switch (mi.getType()) {
            //登录信息
            case TYPE_LOGIN:
                return mi.isAccountExist() && mi.isPasswordExist();
            case TYPE_LOGIN_TOKEN:
                return mi.isUIDExist() && mi.isTokenExist();
            case TYPE_LOGIN_VC:
                return (mi.isAccountExist()) || (mi.isAccountExist() && mi.isVCExist());
            //用户注册信息
            case TYPE_REGISTER:
                return (mi.isAccountExist()) || (mi.isAccountExist() && mi.isVCExist() && mi.isPasswordExist());
            //用户转发信息
            case TYPE_RELAY:
                return mi.isUIDExist() && mi.isReceiverExist() && mi.isTokenExist()
                        && mi.isMsgExist();
            case TYPE_HISTORT:
                return mi.isUIDExist() && mi.isReceiverExist() && mi.isCursorExist();
            default:
                return false;
        }
    }
}