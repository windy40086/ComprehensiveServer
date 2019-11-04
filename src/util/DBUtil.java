package util;

import entity.*;

import java.sql.*;

public class DBUtil {

    private static MysqlConfig mc = XMLUtil.getSqlCFG();

    private static final String URL = mc.getURL();
    private static final String DataBase = mc.getDataBase();
    private static final String U = mc.getU();
    private static final String P = mc.getP();
    private static final String DRIVER = mc.getDriver();

    private static Connection connect = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    private static Connection getCon() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL + DataBase + "?serverTimezone=CTT", U, P);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("mysql is close");
        }
        return connect;

    }

    private static PreparedStatement createPreSta(String sql, Object[] params) throws SQLException {
        pstmt = getCon().prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
        return pstmt;
    }

    private static void dbCloseAll() {
        try {
            if (pstmt != null) pstmt.close();
            if (rs != null) rs.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //增加 删除 修改
    public static boolean executeUpdate(String sql, Object[] params) {
        try {
            int count = createPreSta(sql, params).executeUpdate();
            return count > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //查询
    public static ResultSet executeQuery(String sql, Object[] params) {
        try {
            return createPreSta(sql, params).executeQuery();
        } catch (Exception e) {
            System.err.println("查询错误 DBUtil 70");
            return null;
        }
    }

    public static int getTotalCount(String sql) {
        int count = 0;
        try {
            pstmt = createPreSta(sql, null);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;

    }
}