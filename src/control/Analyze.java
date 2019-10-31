package control;

import entity.Message;

import java.util.Date;

class Analyze {

    private static final String ACCOUNT = "account";
    private static final String MSG = "msg";

    //分析数据的发送方
    static Message analyzeMessage(String text) {
        String[] strs = text.split("&");
        String msg = null;
        String msgAccount = null;
        String createTime = null;

        for (String str : strs) {
            String[] result = str.split("=");
            Date d = new Date();
            createTime = d.getTime() + "";
            switch (result[0]) {
                case ACCOUNT:
                    msgAccount = result[1];
                    break;
                case MSG:
                    msg = result[1];
                    break;
                default:
                    break;
            }
        }

        return new Message(msg, msgAccount, createTime);
    }
}
