package utils;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;

public class ImageUtil {

	/**
	 * 
	 * @return ảnh đã chọn
	 */
	public static File browseImg() {
		
		JFileChooser chooseFile = new JFileChooser();
		
		int result = chooseFile.showOpenDialog(null);
		
		if(result == JFileChooser.APPROVE_OPTION)
			return chooseFile.getSelectedFile();
		
		return null;
	}
	
	/**
	 * 
	 * hàm này lấy đường dẫn đích muốn lưu ảnh, sau đó sẽ ghép tên ảnh đầu vào vô đường dẫn đích đấy.
	 * <br>
	 * cuối cùng là thực hiện sao chép ảnh gốc vào đường dẫn đã tạo
	 * @param image
	 * ảnh đầu vào
	 * @param typeImg 
	 * phân loại thư mục để lưu ảnh
	 * 
	 * @return đường dẫn của ảnh vừa thêm
	 */
	public static String saveImg(File image, String typeImg)
	{
		if(image == null) return null;
		final String PATH = "src/img/" + typeImg;
		
		String pathImg = null;
		try {
			Path path = Paths.get(PATH);
			Files.createDirectories(path);
			
			Path destination = path.resolve(image.getName());
			
			Files.copy(image.toPath(), destination);
			
			pathImg = destination.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pathImg;
	}
}
