package Sebastian.demo.FilesUpload;

public class FileName {
	private String fullPath;
	private char extensionSeparator;
	private char pathSeparator;

	public FileName(String str, char sep, char ext) {
		fullPath = str;
		pathSeparator = sep;
		extensionSeparator = ext;
	}

	public String extension() {
		fullPath = fullPath.replace("\\", "/");
		int dot = fullPath.lastIndexOf(extensionSeparator);
		return fullPath.substring(dot+1);
	}

	public String filename() { // gets filename without extension
		fullPath = fullPath.replace("\\", "/");
		int dot = fullPath.lastIndexOf(extensionSeparator);
		int sep = fullPath.lastIndexOf(pathSeparator);
		return fullPath.substring(sep+1, dot);
	}

	public String path() {
		fullPath = fullPath.replace("\\", "/");
		int sep = fullPath.lastIndexOf(pathSeparator);
		return fullPath.substring(0, sep);
	}
	public String index() {
		fullPath = fullPath.replace("\\", "/");
		int sep = fullPath.lastIndexOf("_");
		System.out.println(sep);
		return fullPath.substring(sep+1, 1);
	}
}