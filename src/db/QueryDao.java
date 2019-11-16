package db;

import java.sql.ResultSet;
import java.sql.SQLException;


import entity.User;
import inter.IType;
import util.Util;

public class QueryDao implements IType {

    public static boolean emailExist(String email) {
        Object[] params = {email};
        String sql = "select * from client where email = ?";
        ResultSet rs = DBUtil.executeQuery(sql, params);
        return true;
    }

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

    //通过 tel | email 获取历史记录id

}
