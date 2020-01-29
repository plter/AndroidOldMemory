package com.plter.androidwallpaper.net;

import java.util.HashMap;

public class CachedPhotos {
	
	private static final HashMap<String, PagedImages> cachedPagedPhotos = new HashMap<String, PagedImages>();
	
	public static void put(String key,PagedImages value){
		cachedPagedPhotos.put(key, value);
	}
	
	public static PagedImages get(String key){
		return cachedPagedPhotos.get(key);
	}
	
	public static boolean has(String key){
		return cachedPagedPhotos.containsKey(key);
	}
}
