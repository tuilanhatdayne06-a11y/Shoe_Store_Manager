package utils;

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
	public String autoGenerateId(String prefix, int code)
	{
		boolean isExist = false;
		String generateCode = null;
		String[] checkId = {"NV", "KH", "NCC", "CAT", "SP", "PD", "PO", "HD"};
		
		for(String id : checkId)
		{
			if(id.equals(prefix))
				isExist = true;
		}
		
		if(isExist)
			generateCode = prefix + String.format("%03d", code);
		else
			System.out.println("Kí tự đầu không khớp");
		return generateCode;
	}
}
