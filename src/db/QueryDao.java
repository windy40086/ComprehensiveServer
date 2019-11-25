package db;

import entity.User;
import inter.IType;
import util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class QueryDao implements IType {

    //判断账号是否存在
    public static synchronized boolean isAccountExist(String account, int type) {
        String sql;
        Object[] params;
        switch (type) {
            case PHONE:
                sql = "select * from client where tel = ?";
                params = new Object[]{account};
                try {
                    ResultSet rs = DBUtil.executeQuery(sql, params);
                    if (null != rs && rs.next()) {
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    DBUtil.dbCloseAll();
                }
                break;
            case EMAIL:
                sql = "select * from client where email = ?";
                params = new Object[]{account};
                try {
                    ResultSet rs = DBUtil.executeQuery(sql, params);
                    if (null != rs && rs.next()) {
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    DBUtil.dbCloseAll();
                }
                break;
            default:
                break;
        }
        return false;
    }

    //判断uid是否存在
//    public static synchronized boolean isUIDExist(String uid){
//        String sql;
//        Object[] params;
//        sql = "select * from client where tel = ? and password = ?";
//        params = new Object[]{u.getAccount(), u.getPassword()};
//        try {
//            ResultSet rs = DBUtil.executeQuery(sql, params);
//
//            if (null != rs && rs.next()) {
//                return true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            DBUtil.dbCloseAll();
//        }
//    }

    //判断账号是否正确
    public static synchronized boolean isAccountCorrect(User u, int type) {
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
                } finally {
                    DBUtil.dbCloseAll();
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
                } finally {
                    DBUtil.dbCloseAll();
                }
                break;
        }
        return false;
    }

    //通过tel | email 获取用户的id
    public static synchronized int getUserID(String account) {
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
                } finally {
                    DBUtil.dbCloseAll();
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
                } finally {
                    DBUtil.dbCloseAll();
                }
                break;
        }
        return -1;
    }

    //通过 tel | email 获取 Cursor
    public static synchronized String getCursor(String uid) {
        String sql;

        sql = "select pk_id from msg_" + uid + " order by pk_id DESC limit 1";

        try {
            ResultSet rs = DBUtil.executeQuery(sql, new Object[]{});
            if (null != rs && rs.next()) {
                return rs.getInt(1) + "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbCloseAll();
        }
        return "-1";
    }

    public static synchronized ArrayList<String> getGroupHistory(int gid,int cursor){
        ArrayList<String> messages = new ArrayList<>();

        String sql;

        //获取全部的信息
        sql = "select account,receiver,msg,send_time from msg_" + gid + " where pk_id > ?";
        try {
            ResultSet rs = DBUtil.executeQuery(sql, new Object[]{cursor});
            assert rs != null;
            while (rs.next()) {
                String a = rs.getString(1);
                String r = rs.getString(2);
                String m = rs.getString(3);
                Timestamp s = rs.getTimestamp(4);

                String msg = "uid=" + a + "&receiver=" + r + "&message=" + m + "&time=" + s.toString();
                messages.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbCloseAll();
        }

        return messages;
    }


    //获取好友的历史记录
    public static synchronized ArrayList<String> getFriendHistory(int uid, int receiver, int cursor) {
        ArrayList<String> messages = new ArrayList<>();

        String sql;

        //获取接受的信息
        sql = "select account,receiver,msg,send_time from msg_" + uid + " where pk_id > ? and account = ?";
        try {
            ResultSet rs = DBUtil.executeQuery(sql, new Object[]{cursor,receiver});
            assert rs != null;
            while (rs.next()) {
                String a = rs.getString(1);
                String r = rs.getString(2);
                String m = rs.getString(3);
                Timestamp s = rs.getTimestamp(4);

                String msg = "uid=" + a + "&receiver=" + r + "&message=" + m + "&time=" + s.toString();
                messages.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbCloseAll();
        }

        //获取发送的信息
        sql = "select account,receiver,msg,send_time from msg_" + uid + " where pk_id > ? and receiver = ?";
        try {
            ResultSet rs = DBUtil.executeQuery(sql, new Object[]{cursor,receiver});
            assert rs != null;
            while (rs.next()) {
                String a = rs.getString(1);
                String r = rs.getString(2);
                String m = rs.getString(3);
                Timestamp s = rs.getTimestamp(4);

                String msg = "uid=" + a + "&receiver=" + r + "&message=" + m + "&time=" + s.toString();
                messages.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
}