package com.plter.puzzle.ui;


/**
 * CIDI(ChangeImageDialogItem)
 * @author xtiqin
 *
 */
public class CIDIListCellData {

	public CIDIListCellData(String label,int iconRes) {
		
		setIconRes(iconRes);
		setLabel(label);
	}
	
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	public int getIconRes() {
		return iconRes;
	}


	public void setIconRes(int iconRes) {
		this.iconRes = iconRes;
	}


	private String label="";
	
	private int iconRes=0;;
	
	
}
