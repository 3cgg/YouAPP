package com.youappcorp.template.ftl;

import java.io.File;

public class FileWrapper {

	private File file;
	
	private byte[] data;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
}
