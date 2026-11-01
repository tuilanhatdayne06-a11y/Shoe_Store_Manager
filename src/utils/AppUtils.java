package utils;

public class AppUtils {
	public String autoGenerateId(String prefix, int code)
	{
		String generateCode = prefix + String.format("%03d", code);
		return generateCode;
	}
}
