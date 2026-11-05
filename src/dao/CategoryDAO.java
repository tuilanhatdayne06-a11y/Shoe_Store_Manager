package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.connectDB;

public class CategoryDAO {
	public List<String> getCategory()
	{
		List<String> list = new ArrayList<String>();
		String sql = "select * from Category ";
		Connection conn = connectDB.getConnection();
		try (
				PreparedStatement pst = conn.prepareStatement(sql);
				ResultSet rs = pst.executeQuery()){
			while(rs.next())
				list.add(rs.getString(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
