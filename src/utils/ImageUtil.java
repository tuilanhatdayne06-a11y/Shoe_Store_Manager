package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
	 * 
	 * @return đường dẫn của ảnh vừa thêm
	 */
	public void saveImg(File image)
	{
		if(image == null) return;
		final String PATH = "src/img/";
		try {
			Path path = Paths.get(PATH);
			Files.createDirectories(path);
			
			Path destination = path.resolve(image.getName());
			
			Files.copy(image.toPath(), destination,StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
