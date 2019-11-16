package service;

import db.UpdateDao;
import inter.IType;

public class HistoryService {

    public static boolean insertHM(String account, String receiver, String message,String type) {
        return UpdateDao.insertHM(account, receiver, message,type);
    }
}

