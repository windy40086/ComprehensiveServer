package control;

import entity.Message;
import entity.MsgInfo;
import inter.IError;
import inter.IType;

import java.util.Date;

class Analyze implements IType, IError {

    //分析数据的发送方
    static Message analyzeMessage(String text) {

        String[] strs = text.split("&");
        Message msg = new Message();
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
        if(mi.getType().equals(TYPE_RELAY)){
            msg.setType(mi.getType());
            msg.setAccount(mi.getAccount());
            msg.setMsg(mi.getMsg());
            return msg;
        }
        //登陆消息
        else if(mi.getType().equals(TYPE_LOGIN)){
            msg.setType(mi.getType());
            if(mi.getAccount().equals("123")&&mi.getPassword().equals("456")){
                msg.setResult("1");
                msg.setError(ERROR_NONE+"");
                return msg;
            }else{
                msg.setResult("0");
                msg.setError(ERROR_LOGIN+"");
                return msg;
            }
        }
        //注册消息
        else if(mi.getType().equals(TYPE_REGISTER)){

        }
        //系统消息
        else if(mi.getType().equals(TYPE_SYSTEM)){

        }

        return null;
    }
}
