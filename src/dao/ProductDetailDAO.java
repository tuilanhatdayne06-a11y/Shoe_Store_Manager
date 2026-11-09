package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectDB.ConnectDB;
import entity.Category;
import entity.Product;
import entity.ProductDetail;
import entity.Supplier;

public class ProductDetailDAO {
	
	public ArrayList<ProductDetail> findProduct(String name)
	{
		CategoryDAO categoryDao = new CategoryDAO();
		SupplierDAO supplierDAO = new SupplierDAO();
		
		ArrayList<ProductDetail> list = new ArrayList<ProductDetail>();
		String keyword = "%" + name + "%";
		String sql = """
					select *
					from ProductDetail pd join Product p on pd.productId = p.productId 
					join Category c on c.categoryId = p.categoryId
					join Supplier sp on sp.supplierId = p.supplierId
					where p.productName like ?
			""";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, keyword);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Category category = categoryDao.getCategoryByName(rs.getString("categoryName"));
				Supplier supplier = supplierDAO.getSupplierByName(rs.getString("supplierName"));
				
				Product product = new Product(
						rs.getString("productId"),
						rs.getString("productName"),
						rs.getDouble("unitPrice"),
						category,
						supplier
				);
				
				ProductDetail pd = new ProductDetail(
						rs.getString("productDetailId"),
						rs.getInt("size"),
						rs.getString("color"),
						rs.getInt("stockQuantity"),
						product,
						rs.getString("imgProductColor")
				);
				
				list.add(pd);
			}
			
		} catch (Exception e) {}
		
		return list.size() > 0? list : null;
	}
	
	public boolean insertProductDetail(String productId, ProductDetail productDetail)
	{
		String sql = "insert into ProductDetail values(?, ?, ?, ?, ?, ?)";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, productDetail.getProductDetailId());
			pst.setInt(2, productDetail.getSize());
			pst.setString(3, productDetail.getColor());
			pst.setInt(4, productDetail.getStockQuantity());
			pst.setString(5, productId);
			pst.setString(6, productDetail.getImgProductColor());
			
			return pst.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public void addProductDetail(Product product, ProductDetail productDetail)
	{
		boolean isAddProductDetail = insertProductDetail(product.getProductId(), productDetail);
		if(!isAddProductDetail)
			JOptionPane.showMessageDialog(null, "Lỗi khi thêm biến thể của sản phẩm");
	}
	public boolean removeProductDetail(String removeId)
	{
		String sql = "delete ProductDetail where productDetailId = ?";
		String sqlCheckInvoice = "select count(productDetailId) as total from InvoiceDetail where productDetailId = ?";
		int detailCount = 0;
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement pst = con.prepareStatement(sqlCheckInvoice);
			
			pst.setString(1, removeId);
			ResultSet rs = pst.executeQuery();
			if(rs.next() && rs.getInt("total") > 0)
			{
				detailCount = rs.getInt("total");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(detailCount == 0)
		{
			try {
				Connection con = ConnectDB.getConnection();
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, removeId);
				return pst.executeUpdate() > 0;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		JOptionPane.showMessageDialog(null, "Không thể xóa sản phẩm này");
		return false;
	}
}
