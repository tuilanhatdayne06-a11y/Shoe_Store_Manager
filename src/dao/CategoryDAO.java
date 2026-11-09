package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Category;

public class CategoryDAO {
	
	public Category getCategoryByName(String name)
	{
		String sql = "select * from Category where categoryName = ?";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next())
				return new Category(rs.getString("categoryId"), rs.getString("categoryName"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<String> getCategoryName()
	{
		List<String> list = new ArrayList<String>();
		String sql = "select * from Category ";
		Connection conn = ConnectDB.getConnection();
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
