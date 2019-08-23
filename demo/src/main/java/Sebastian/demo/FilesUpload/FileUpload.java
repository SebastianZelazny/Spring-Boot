package Sebastian.demo.FilesUpload;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	private MultipartFile fileName;
	
	public MultipartFile getFileName() {
		return fileName;
	}

	public void setFileName(MultipartFile fileName) {
		this.fileName = fileName;
	}
}
