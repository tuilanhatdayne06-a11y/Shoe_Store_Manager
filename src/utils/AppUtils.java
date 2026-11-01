package utils;

public class AppUtils {
	/**
	 * 
	 * @param prefix
	 * : tiền tố của id
	 * @param code
	 * : mã số theo sau(tối thiểu 3 số)
	 * @return mã đã được generate
	 * 
	 */
	public String autoGenerateId(String prefix, int code)
	{
		String generateCode = prefix + String.format("%03d", code);
		return generateCode;
	}
}
