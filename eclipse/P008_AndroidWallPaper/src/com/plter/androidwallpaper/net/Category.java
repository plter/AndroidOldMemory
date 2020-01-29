package com.plter.androidwallpaper.net;

public class Category {
	
	public Category(String ID,String name,String visible) {
		this.ID=ID;
		this.name=name;
		this.visible=visible;
	}
	
	public Category() {
	}
	
	public String ID="";
	public String name="";
	public String visible="";
	
	
	public static final String ID_LOCAL_WALLPAPER_CATETORY="localWallpapers";
	public static final String ID_LOCAL_IMAGE_FILES_CATEGORY="localImageFiles";
	public static final String ID_SETTINGS_CATEGORY="setting";
}
