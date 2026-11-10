package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import connectDB.ConnectDB;
import entity.Category;
import entity.Product;
import entity.ProductDetail;
import entity.Supplier;

public class ProductDAO {
	
	public Product getProduct(String productName, String categoryName, String supplierName)
	{
		CategoryDAO categoryDAO = new CategoryDAO();
		SupplierDAO supplierDAO = new SupplierDAO();
		
		Category category = categoryDAO.getCategoryByName(categoryName);
		Supplier supplier = supplierDAO.getSupplierByName(supplierName);

		
		String sqlFindProduct = "select * from Product where productName = ? and  categoryId = ?";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement pst = con.prepareStatement(sqlFindProduct);
			
			pst.setString(1, productName);
			pst.setString(2, category.getCategoryId());
			
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return new Product(
						rs.getString("productId"),
						rs.getString("productName"),
						rs.getDouble("unitPrice"),
						category,
						supplier
				);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public void removeProduct(String removeId)
	{
		String sql = "delete Product where productId = ?";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, removeId);
			pst.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public ArrayList<ProductDetail> getAllProduct()
	{
		ArrayList<ProductDetail> list = new ArrayList<ProductDetail>();
		String sql = """
					select *
					from Product p join ProductDetail pd on p.productId = pd.productId 
					join Category c on p.categoryId = c.categoryId
					join Supplier sp on sp.supplierId = p.supplierId;
				""";

		Connection con = ConnectDB.getConnection();

		try (
				
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();
		)
		{
			while(rs.next())
			{
				String productId = rs.getString("productId");
				Category category = new Category(rs.getString("categoryId"), rs.getString("categoryName"));
				Supplier supplier = new Supplier(
						rs.getString("supplierId"),
						rs.getString("supplierName"),
						rs.getString("address"),
						rs.getString("phoneNumber"),
						rs.getString("email"),
						rs.getString("logo")
				);
				
				Product product = new Product(
						productId,
						rs.getString("productName"),
						rs.getDouble("unitPrice"),
						category,
						supplier
				); 
				
				ProductDetail productDetail = new ProductDetail(
						rs.getString("productDetailId"),
						rs.getInt("size"),
						rs.getString("color"),
						rs.getInt("stockQuantity"),
						product,
						rs.getString("imgProductColor")
				);
				list.add(productDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void updateStockQuantity(int stock, String productName, int size, String color, String categoryName)
	{
		CategoryDAO categoryDAO = new CategoryDAO();
		String categoryId = categoryDAO.getCategoryByName(categoryName).getCategoryId();
		String sql = """
				update pd set pd.stockQuantity= ?  from
				Product p join ProductDetail pd on p.productId = pd.productId 
				where p.productName = ? and pd.size = ? and pd.color= ? and p.categoryId = ?	
			""";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setInt(1, stock);
			pst.setString(2, productName);
			pst.setInt(3, size);
			pst.setString(4, color);
			pst.setString(5, categoryId);
			
			pst.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateUnitPrice(String productName, String categoryId, String supplierId, double price)
	{
		String sql = """
				update p set p.unitPrice= ?  from
				Product p join ProductDetail pd on p.productId = pd.productId 
				where p.productName = ? and p.categoryId = ? and p.supplierId = ?
			""";
		
		try (
				Connection con = ConnectDB.getConnection();
				PreparedStatement pst = con.prepareStatement(sql);
				
		)
		{
			pst.setDouble(1, price);
			pst.setString(2, productName);
			pst.setString(3, categoryId);
			pst.setString(4, supplierId);

			
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addProduct(Product product)
	{
		String sql = "insert into Product values(?, ?, ?, ?, ?)";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, product.getProductId());
			pst.setString(2, product.getProductName());
			pst.setDouble(3, product.getUnitPrice());
			pst.setString(4, product.getCategory().getCategoryId());
			pst.setString(5, product.getSupplier().getSupplierId());
			
			pst.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}