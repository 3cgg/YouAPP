package j.jave.web.htmlclient.request;

import me.bunny.kernel._c.io.JFile;
import me.bunny.kernel._c.model.JModel;

public class FileUploaderVO implements JModel {
	
	private JFile file;
	
	private FileAttachedParamVO fileAttachedParamVO=new FileAttachedParamVO();

	public JFile getFile() {
		return file;
	}

	public void setFile(JFile file) {
		this.file = file;
	}

	public FileAttachedParamVO getFileAttachedParamVO() {
		return fileAttachedParamVO;
	}

	public void setFileAttachedParamVO(FileAttachedParamVO fileAttachedParamVO) {
		this.fileAttachedParamVO = fileAttachedParamVO;
	}
}
