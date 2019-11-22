package service;

import db.QueryDao;
import db.UpdateDao;

import java.util.ArrayList;

public class HistoryService {

    //插入历史记录
    public static boolean insertHM(String uid, String receiver, String message, String type) {
        return UpdateDao.insertHM(uid, receiver, message, type);
    }

    //获取用户的最后一个Cursor
    public static String getCursor(String uid) {
        return QueryDao.getCursor(uid);
    }

    //获取用户历史记录
    public static ArrayList<String> getMessages(String uid, String receiver, String cursor) {

        int c = Integer.parseInt(cursor);
        int i = Integer.parseInt(uid);
        int r = Integer.parseInt(receiver);

        if (r > 1000000000) {
            //如果接收者为用户
            return QueryDao.getFriendHistory(i, r, c);
        } else {
            //如果接受者为群
            return QueryDao.getGroupHistory(r, c);
        }
    }
}

