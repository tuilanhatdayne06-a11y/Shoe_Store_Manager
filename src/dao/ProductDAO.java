package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.connectDB;
import utils.AppUtils;

public class ProductDAO {
	
//	public int getRowCountProduct()
//	{
//		String sql = "select count(*) as total from Product";
//		int rowCount = 0;
//		try (
//				Connection con = connectDB.getConnection();
//				PreparedStatement pst = con.prepareStatement(sql);
//				ResultSet rs = pst.executeQuery();
//		){
//			while(rs.next())
//				rowCount = rs.getInt(1);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return rowCount;
//	}
	
	
	public void removeProduct(String removeId)
	{
		
		String sqlFindProductId = """
				select p.productId from ProductDetail pd join
				Product p on pd.productId = p.productId where pd.productDetailId = ?
			""";
		
		String sqlRemove = """
				delete pd from ProductDetail pd 
				join Product p on pd.productId = p.productId
				where pd.productDetailId = ?
			""";
		
		String sqlCountProductById = """
				select count(p.productId) as total from ProductDetail pd join
				Product p on pd.productId = p.productId where p.productId = ?
			""";
		
		String sqlRemoveProduct = """
				delete Product where productId = ?
			""";
		
		String productId = null;
		int existRow = 0;
		
		try {
			// find id
			try (
					Connection con = connectDB.getConnection();
					PreparedStatement pst = con.prepareStatement(sqlFindProductId)
			){
				pst.setString(1, removeId);
				ResultSet rs = pst.executeQuery();
				while(rs.next())
					productId = rs.getString(1);
				
				System.out.println(productId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// remove product
			try (
					Connection con = connectDB.getConnection();
					PreparedStatement pst = con.prepareStatement(sqlRemove)
					){
				pst.setString(1, removeId);
				pst.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// count id
			try (
					Connection con = connectDB.getConnection();
					PreparedStatement pst = con.prepareStatement(sqlCountProductById)
					){
				pst.setString(1, productId);
				ResultSet rs = pst.executeQuery();
				while(rs.next())
					existRow = Integer.parseInt(rs.getString(1));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(existRow == 0)
			{
				try (
						Connection con = connectDB.getConnection();
						PreparedStatement pst = con.prepareStatement(sqlRemoveProduct)
						){
					pst.setString(1, productId);
					pst.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Object[]> getAllProduct()
	{
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		String sql = """
					select pd.productDetailId, p.productName, pd.size, pd.color, c.categoryName, pd.stockQuantity,
					 p.unitPrice, sp.supplierName, pd.imgProductColor
					from Product p join ProductDetail pd on p.productId = pd.productId 
					join Category c on p.categoryId = c.categoryId
					join Supplier sp on sp.supplierId = p.supplierId;
				""";
		Connection con = connectDB.getConnection();
		try (
				
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();
		)
		{
			while(rs.next())
			{
				Object[] newObject = {
						rs.getString("productDetailId"),
						rs.getString("productName"),
						rs.getInt("size"),
						rs.getString("color"),
						rs.getString("categoryName"),
						rs.getInt("stockQuantity"),
						rs.getDouble("unitPrice"),
						rs.getString("supplierName"),
						rs.getString("imgProductColor")
				};
				list.add(newObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void updateUnitPrice(String id, double price)
	{
		String sql = """
				update p set p.unitPrice= ?  from
				Product p join ProductDetail pd on p.productId = pd.productId where pd.productDetailId = ?
			""";
		
		try (
				Connection con = connectDB.getConnection();
				PreparedStatement pst = con.prepareStatement(sql);
				
		)
		{
			pst.setDouble(1, price);
			pst.setString(2, id);
			
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Object[]> addToDatabase(
			String productDetailId,
			String productName, int size, String color,
			String category, int stock, double price, String supplier, int tableSize,
			String imgName
	)
	{
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		
		AppUtils utils = new AppUtils();
		
		String productId = null;
		String categoryId = null;
		String supplierId = null;
		
		String sqlFindProduct = "select * from Product where productName = ? and  categoryId = ?";
		String sqlProduct = """
					insert into Product values (?, ?, ?, ?, ?, ?)
				""";
		String sqlProductDetail = """
					insert into ProductDetail values (?, ?, ?, ?, ?, ?)
				""";
		String sqlGetProductDetailId = "select count(*) as total from ProductDetail";
		
		String sqlCategory = "select categoryId from Category where categoryName = ?";
		String sqlSupplier = "select supplierId from Supplier where supplierName = ?";
		
		
		try {
			// category execute 
			try (
					Connection con = connectDB.getConnection();
					PreparedStatement pst = con.prepareStatement(sqlCategory);
					
			){
				pst.setString(1, category);
				
				ResultSet rs = pst.executeQuery();
				
				while(rs.next())
					categoryId = rs.getString(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//find product 
			try (Connection con = connectDB.getConnection();
					PreparedStatement pst = con.prepareStatement(sqlFindProduct);
					)
			{
				pst.setString(1, productName);
				pst.setString(2, categoryId);
				ResultSet rs = pst.executeQuery();
				while(rs.next())
				{
					productId = rs.getString("productId");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//supplier execute
			
			try (
					Connection con = connectDB.getConnection();
					PreparedStatement pst = con.prepareStatement(sqlSupplier);
					
			){
				pst.setString(1, supplier);
				
				ResultSet rs = pst.executeQuery();
				
				while(rs.next())
					supplierId = rs.getString(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// product execute
			if(productId == null)
			{
				productId = utils.autoGenerateId("SP");
				try (
						Connection con = connectDB.getConnection();
						PreparedStatement pst = con.prepareStatement(sqlProduct);
						
				){
					pst.setString(1, productId);
					pst.setString(2, productName);
					pst.setDouble(3, price);
					pst.setString(4, categoryId);
					pst.setString(5, supplierId);
					pst.setString(6, imgName);
					
					pst.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			// product detail execute
			try (
					Connection con = connectDB.getConnection();
					PreparedStatement pst = con.prepareStatement(sqlProductDetail);
					PreparedStatement pstPDId = con.prepareStatement(sqlGetProductDetailId)
			){
				pstPDId.executeQuery();
				
				pst.setString(1, productDetailId);
				pst.setInt(2, size);
				pst.setString(3, color);
				pst.setInt(4, stock);
				pst.setString(5, productId);
				pst.setString(6, imgName);
				
				pst.executeUpdate();
		
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}