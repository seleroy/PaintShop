package com.slr.model;

public class PaintStyle {
	
	private int colour;
	private PaintFinish finish;
	
	public PaintStyle(int colour, PaintFinish finish) {
		this.colour = colour;
		this.finish = finish;
	}
	
	public int getColour() {
		return colour;
	}
	public void setColour(int colour) {
		this.colour = colour;
	}
	public PaintFinish getFinish() {
		return finish;
	}
	
	public String getFinishString() {
		return finish.toString();
	}
	
	public void setFinish(PaintFinish finish) {
		this.finish = finish;
	}
	
	public boolean equals(Object other) {
		if (other == null)  {
			return false;
		}
		if (other == this) {
			return true;
		}
		if (!(other instanceof PaintStyle)) {
			return false;
		}
		PaintStyle paint = (PaintStyle) other;
		
		return (paint.colour == this.colour) && (paint.finish == this.finish);
		
	}
	
	

}
