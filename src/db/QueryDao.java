package db;

import com.mysql.cj.protocol.Resultset;
import util.DBUtil;

public class QueryDao {

    public static boolean emailExist(String email){
        Object[] params = {email};
        String sql = "select * from table where email = ?";
        Resultset rs = (Resultset) DBUtil.executeQuery(sql,params);
        return true;
    }

}
