package control;

import entity.Message;
import entity.MsgInfo;
import inter.IType;

import java.util.Date;

class Analyze implements IType {

    //分析数据的发送方
    static Message analyzeMessage(String text) {
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
                default:
                    break;
            }
        }

        //判断消息是否不是用户消息
        if(mi.getType()!=TYPE_RELAY){
            //如果是
        }

        return new Message(mi.getType(), mi.getMsg(), mi.getAccount(), mi.getPassword(), mi.getTime());
    }

    private static boolean isExist() {

        return true;
    }

    private static boolean isCorrect(String msg) {

        return true;
    }
}
