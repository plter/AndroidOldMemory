package com.plter.weather.view;

public class DropDownMenuItemCellData {

	
	public static final int ACTION_ABOUT=1,
			ACTION_CHANGE_CITY=2,
			ACTION_CANCEL=3,
			ACTION_REFRESH=4;
	
	
	public DropDownMenuItemCellData(int action,String label) {
		this.action=action;
		this.label=label;
	}
	
	@Override
	public String toString() {
		return label;
	}
	
	
	public String label;
	public int action=0;
}
