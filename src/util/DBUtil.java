package util;

import java.sql.*;

public class DBUtil {

	private static final String URL = "jdbc:mysql://localhost:3306/";
	private static final String DataBase= "comprehensive";
	private static final String U = "root";
	private static final String P = "root";

	private static Connection connect = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	
	private static Connection getCon() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL + DataBase + "?serverTimezone=CTT", U, P);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return connect;

	}

	private static PreparedStatement createPreSta(String sql, Object[] params) throws SQLException {
		pstmt = getCon().prepareStatement(sql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
		}
		return pstmt;
	}
	
	private static void dbCloseAll(){
		try {
			if(pstmt!=null)pstmt.close();
			if(rs!=null)rs.close();
			if(connect!=null)connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static boolean executeUpdate(String sql,Object[] params) {
		try {
			int count = createPreSta(sql, params).executeUpdate();
			return count > 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbCloseAll();
		}
		return false;
	}

	public static ResultSet executeQuery(String sql, Object[] params) {
		try {
			return createPreSta(sql,params).executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static int getTotalCount(String sql){
		int count = 0;
		try {
			pstmt = createPreSta(sql, null);
			rs = pstmt.executeQuery();

			while(rs.next()){
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
		
	}

}
