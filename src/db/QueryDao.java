package db;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


import entity.User;
import inter.IType;
import util.Util;

public class QueryDao implements IType {

//    public static boolean emailExist(String email) {
//        Object[] params = {email};
//        String sql = "select * from client where email = ?";
//        ResultSet rs = DBUtil.executeQuery(sql, params);
//        return true;
//    }

    //判断账号是否存在
    public static boolean isAccountExist(User u, int type) {
        String sql;
        Object[] params;
        switch (type) {
            case PHONE:
                sql = "select * from client where tel = ? and password = ?";
                params = new Object[]{u.getAccount(), u.getPassword()};
                try {
                    ResultSet rs = DBUtil.executeQuery(sql, params);

                    if (null != rs && rs.next()) {
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
                break;
            case EMAIL:
                sql = "select * from client where email = ? and password = ?";
                params = new Object[]{u.getAccount(), u.getPassword()};
                try {
                    ResultSet rs = DBUtil.executeQuery(sql, params);

                    if (null != rs && rs.next()) {
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
                break;
        }
        return false;
    }

    //通过tel | email 获取用户的id
    public static int getUserID(String account) {
        String sql;
        Object[] params;
        switch (Util.getAccountType(account)) {
            case PHONE:
                sql = "select pk_id from client where tel = ?";
                params = new Object[]{account};
                try {
                    ResultSet rs = DBUtil.executeQuery(sql, params);
                    if (null != rs && rs.next()) {
                        return rs.getInt(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case EMAIL:
                sql = "select pk_id from client where email = ?";
                params = new Object[]{account};
                try {
                    ResultSet rs = DBUtil.executeQuery(sql, params);
                    if (null != rs && rs.next()) {
                        return rs.getInt(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return -1;
    }

    //通过 tel | email 获取 Cursor
    public static String getCursor(String account) {
        String sql;

        sql = "select pk_id from msg_" + getUserID(account) + " order by pk_id DESC limit 1";

        try {
            ResultSet rs = DBUtil.executeQuery(sql, new Object[]{});
            if (null != rs && rs.next()) {
                return rs.getInt(1) + "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    //获取历史记录
    public static ArrayList<String> getHistory(String account, int cursor) {
        ArrayList<String> messages = new ArrayList<>();

        String sql;

        sql = "select account,receiver,msg,send_time from msg_" + getUserID(account) + " where pk_id > ?";

        try {
            ResultSet rs = DBUtil.executeQuery(sql, new Object[]{cursor});
            assert rs != null;
            while (rs.next()) {
                String a = rs.getString(1);
                String r = rs.getString(2);
                String m = rs.getString(3);
                Timestamp s = rs.getTimestamp(4);

                String msg = "account=" + a + "&receiver=" + r + "&message=" + m + "&time=" + s.toString();
                messages.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}

class test {
    public static void main(String[] args) {
        ArrayList<String> msg = QueryDao.getHistory("379949419@qq.com",0);

        try {
            System.out.println("写入消息");
            ServerSocket ss = new ServerSocket(10443);
            Socket client = ss.accept();
            ObjectOutputStream oos = new ObjectOutputStream(new DataOutputStream(client.getOutputStream()));
            oos.writeObject(msg);
            oos.flush();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}