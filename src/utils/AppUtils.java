package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connectDB.connectDB;

public class AppUtils {
	/**
	 * 
	 * @param prefix
	 * : tiền tố của id
	 * <br>
	 * chỉ chấp nhận NV, KH, NCC, CAT, SP, PD, PO, HD
	 * @param code
	 * : mã số theo sau(tối thiểu 3 số)
	 * @return mã đã được generate
	 * 
	 */
	
	public String autoGenerateId(String prefix) {
	    String sql = """
	    	    UPDATE IDCounter
	    	    SET currentValue = currentValue + 1
	    	    OUTPUT INSERTED.currentValue
	    	    WHERE prefix = ?
	    	    """;
	    String[] checkId = {"NV", "KH", "NCC", "CAT", "SP", "PD", "PO", "HD"};

	    boolean isExist = false;

	    for (String id : checkId) {
	        if (id.equals(prefix)) {
	            isExist = true;
	            break;
	        }
	    }
	    
	    try (
	        Connection con = connectDB.getConnection();
	        PreparedStatement pst = con.prepareStatement(sql)
	    ) {
	        pst.setString(1, prefix);

	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next() && isExist) {
	                int code = rs.getInt("currentValue");
	                return prefix + String.format("%03d", code);
	            } 
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}
