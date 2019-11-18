package service;

import db.QueryDao;
import db.UpdateDao;
import inter.IType;

import java.util.ArrayList;

public class HistoryService {

    //插入历史记录
    public static boolean insertHM(String account, String receiver, String message, String type) {
        return UpdateDao.insertHM(account, receiver, message, type);
    }

    //获取用户的最后一个Cursor
    public static String getCursor(String account) {
        return QueryDao.getCursor(account);
    }

    //获取用户历史记录
    public static ArrayList<String> getMessages(String account, int cursor) {
        return QueryDao.getHistory(account, cursor);
    }
}

