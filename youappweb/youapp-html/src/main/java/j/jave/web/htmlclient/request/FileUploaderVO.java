package j.jave.web.htmlclient.request;

import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.model.JModel;

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
