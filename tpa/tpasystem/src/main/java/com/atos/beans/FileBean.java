package com.atos.beans;

import java.io.Serializable;

public class FileBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3475624458727634824L;
	private String fileName;
	private String contentType;
	private byte[] contents;

	
	public FileBean(String fileName, String contentType, byte[] contents) {
		super();
		this.fileName = fileName;
		this.contentType = contentType;
		this.contents = contents;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getContents() {
		return contents;
	}
	public void setContents(byte[] contents) {
		this.contents = contents;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileBean other = (FileBean) obj;
		if (contentType == null) {
			if (other.contentType != null)
				return false;
		} else if (!contentType.equals(other.contentType))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FileBean [fileName=" + fileName + ", contentType=" + contentType + "]";
	}
	
	
	/*String fileName = uploadedFile.getFileName();
    String contentType = uploadedFile.getContentType();
    byte[] contents = uploadedFile.getContents()*/
}
