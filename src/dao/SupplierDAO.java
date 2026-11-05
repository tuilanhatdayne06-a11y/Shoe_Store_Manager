package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.connectDB;

public class SupplierDAO {
	public ArrayList<String> getAllSupplier()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		String sql = "select * from Supplier";
		Connection con = connectDB.getConnection();
		try(
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery()
		)
		{
			while(rs.next())
			{
				list.add(rs.getString(2));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
