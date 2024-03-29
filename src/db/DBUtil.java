package db;

import entity.MysqlConfig;
import util.XMLUtil;

import java.sql.*;

public class DBUtil {

    private static MysqlConfig mc = XMLUtil.getSqlCFG();

    private static final String URL = mc.getURL();
    private static final String DataBase = mc.getDataBase();
    private static final String U = mc.getU();
    private static final String P = mc.getP();
//    private static final String DRIVER = mc.getDriver();

    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    private static class con {
        private static Connection connect;

        static Connection getCon() {
            if (connect == null) {
                try {
                    connect = DriverManager.getConnection(URL + DataBase + "?autoReconnect=true&useSSL=false&serverTimezone=CTT", U, P);
                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        connect.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    connect = null;
                }
            }
            return connect;
        }
    }

    private static synchronized Connection getCon() {
        return con.getCon();
    }

    private static synchronized PreparedStatement createPreSta(String sql, Object[] params) throws SQLException {
        pstmt = getCon().prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
        return pstmt;
    }

    static synchronized void dbCloseAll() {
        try {
            if (pstmt != null) pstmt.close();
            if (rs != null) rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //增加 删除 修改
    static synchronized boolean executeUpdate(String sql, Object[] params) {
        try {
            int count = createPreSta(sql, params).executeUpdate();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //查询
    static synchronized ResultSet executeQuery(String sql, Object[] params) {
        try {
            return createPreSta(sql, params).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("查询错误 DBUtil 70");
            return null;
        }
    }

//    public static synchronized int getTotalCount(String sql) {
//        int count = 0;
//        try {
//            pstmt = createPreSta(sql, null);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                count++;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return count;
//    }

}