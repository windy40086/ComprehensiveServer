package db;

import entity.User;
import inter.IType;
import util.Util;

public class UpdateDao implements IType {
    //在数据库中建立用户
    public static boolean createAccount(User u) {
        boolean isSuccess = false;
        String sql;
        Object[] params;
        switch (Util.getAccountType(u.getAccount())) {
            case PHONE:
                sql = "insert into client(tel,password,jurisdiction) values(?,?,?)";
                params = new Object[]{u.getAccount(), u.getPassword(), 0};
                isSuccess = DBUtil.executeUpdate(sql, params);
                createHDB(QueryDao.getUserID(u.getAccount()));
                break;
            case EMAIL:
                sql = "insert into client(email,password,jurisdiction) values(?,?,?)";
                params = new Object[]{u.getAccount(), u.getPassword(), 0};
                isSuccess = DBUtil.executeUpdate(sql, params);
                createHDB(QueryDao.getUserID(u.getAccount()));
                break;
            default:
                break;
        }
        return isSuccess;
    }

    //创建用户的信息数据库
    public static boolean createHDB(int account) {
        String sql = "create table msg_" + account + "(\n" +
                "    pk_id bigint unsigned primary key auto_increment,\n" +
                "    account varchar(50),\n" +
                "    receiver varchar(50),\n" +
                "    msg varchar(1000) default '',\n" +
                "    type_id int not null,\n" +
                "    send_time TIMESTAMP default CURRENT_TIMESTAMP,\n" +
                "    create_time TIMESTAMP default CURRENT_TIMESTAMP ,\n" +
                "    update_time TIMESTAMP default CURRENT_TIMESTAMP\n" +
                ");";
        Object[] params = new Object[]{};

        return DBUtil.executeUpdate(sql, params);
    }

    //插入用户的聊天记录
    public static boolean insertHM(String account, String receiver, String message, String type) {
        boolean isSuccess = false;
        String sql;
        Object[] params;
        int accountID = -1;
        int receiverID = -1;
        switch (Util.getAccountType(account)) {
            case PHONE:
            case EMAIL:
                accountID = QueryDao.getUserID(account);
                receiverID = QueryDao.getUserID(receiver);
                sql = "insert into msg_" + QueryDao.getUserID(account) + "(account,receiver,msg,type_id) values(?,?,?,?)";
                params = new Object[]{accountID, receiverID, message, type};
                isSuccess = DBUtil.executeUpdate(sql, params);
                break;
            default:
                break;
        }
        return isSuccess;
    }
}

