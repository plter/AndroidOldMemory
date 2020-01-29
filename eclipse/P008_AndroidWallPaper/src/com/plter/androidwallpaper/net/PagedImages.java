package com.plter.androidwallpaper.net;

import java.util.ArrayList;

public class PagedImages {

	public PagedImages(ArrayList<Image> imgData,int count,int perPageCount,int page) {
		this.setImgData(imgData);
		this.setCount(count);
		this.setPerPageCount(perPageCount);
		this.setPage(page);
	}
	
	public PagedImages() {
	}
	
	
	private ArrayList<Image> imgData=null;
	private int count=0;
	private int perPageCount=0;
	private int page=1;
	
	
	public void contact(PagedImages photos){
		if (getImgData()!=null) {
			getImgData().addAll(photos.getImgData());
			
		}else{
			setImgData(photos.getImgData());
		}
		
		setCount(photos.getCount());
		setPage(photos.getPage());
		setPerPageCount(photos.getPerPageCount());
	}

	public ArrayList<Image> getImgData() {
		return imgData;
	}

	public void setImgData(ArrayList<Image> imgData) {
		this.imgData = imgData;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPerPageCount() {
		return perPageCount;
	}

	public void setPerPageCount(int perPageCount) {
		this.perPageCount = perPageCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
}
