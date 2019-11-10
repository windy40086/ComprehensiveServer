package db;

import java.sql.ResultSet;
import java.sql.SQLException;


import entity.User;

public class QueryDao {

    public static boolean emailExist(String email) {
        Object[] params = {email};
        String sql = "select * from table where email = ?";
        ResultSet rs = DBUtil.executeQuery(sql, params);
        return true;
    }

    //判断账号是否存在
    public static boolean isAccountExist(User u) {
        String sql = "select * from client where account = ? and password = ?";
        Object[] params = {u.getAccount(), u.getPassword()};
        try {
            ResultSet rs = DBUtil.executeQuery(sql, params);

            if(null !=rs && rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
