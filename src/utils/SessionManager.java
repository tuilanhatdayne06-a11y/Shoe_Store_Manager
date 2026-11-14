package utils;
import entity.Employee;

public class SessionManager {
	public static Employee nhanVienDangNhap = null;
    public static boolean isLogin() {
        return nhanVienDangNhap != null;
    }
    public static void logout() {
        nhanVienDangNhap = null;
    }
}
