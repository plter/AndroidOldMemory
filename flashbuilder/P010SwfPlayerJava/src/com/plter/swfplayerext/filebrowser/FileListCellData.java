package com.plter.swfplayerext.filebrowser;

import java.io.File;

import android.graphics.Bitmap;

import com.plter.swfplayerext.data.Images;

public class FileListCellData {


	public FileListCellData(File file) {
		this.file=file;
		computeFileType();
		
		setLabel(file.getName());
		setIcon(getFileIcon());
	}

	public FileListCellData(File file,String label,Bitmap icon,boolean parentDir) {
		setFile(file);
		computeFileType();
		
		setLabel(label==null?file.getName():label);
		setIcon(icon==null?getFileIcon():icon);
		setParentDir(parentDir);
	}

	private void computeFileType(){
		if (!getFile().isDirectory()) {
			String name = getFile().getName();
			int start=name.lastIndexOf('.');

			if (start>-1) {
				setFileType(name.substring(start+1, name.length()).toLowerCase());				
			}else{
				setFileType("");
			}
		}else{
			setFileType("");
		}
	}


	public File getFile() {
		return file;
	}


	private void setFile(File file) {
		this.file = file;
	}

	public Bitmap getIcon(){
		return icon;
	}

	private void setIcon(Bitmap icon){
		this.icon=icon;
	}

	public String getLabel() {
		return label;
	}


	private void setLabel(String label) {
		this.label = label;
	}

	private Bitmap getFileIcon(){
		if (!getFile().isDirectory()) {			
			if (getFileType().equals("swf")) {
				return Images.getSwfFileIconBitmap();
			}else{
				return Images.getUnknownFileIconBitmap();
			}
		}else{
			return Images.getDirIconBitmap();
		}		
	}

	public boolean isParentDir() {
		return parentDir;
	}

	private void setParentDir(boolean parentDir) {
		this.parentDir = parentDir;
	}

	public String getFileType() {
		return fileType;
	}

	private void setFileType(String fileType) {
		this.fileType = fileType;
	}

	private boolean parentDir=false;
	private File file=null;
	private String label=null;
	private Bitmap icon=null;
	private String fileType="";
}
