package com.slr.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	
	private List<PaintStyle> requestedPaintList = new ArrayList<>();
	//We can use the index in this list as we do not provide any API to insert in the middle or delete elements
	private int indexMattPaint =-1;
	
	/**
	 * Gets the unique Matt paint requested by the customer
	 * @return colour of the Matt paint, or 0 if the customer did not request any Matt Paint
	 */
	public int getColourOfMattPaintOrZero() {
		if (indexMattPaint == -1) {
			return 0;
		}
		return requestedPaintList.get(indexMattPaint).getColour();
	}

	public void addPaintToRequestList(int colour, PaintFinish finish) {
		addPaintToRequestList(new PaintStyle(colour, finish));
	}
	
	public void addPaintToRequestList(PaintStyle paint) {
		requestedPaintList.add(paint);
		if (paint.getFinish() == PaintFinish.M) {
			indexMattPaint = requestedPaintList.size() - 1;
		}
	}

	public List<PaintStyle> getRequestedPaintList() {
		return requestedPaintList;
	}
	
	public int getNbRequestedPaints() {
		return requestedPaintList.size();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Customer: ");
		sb.append("<");
		for(PaintStyle paint: requestedPaintList) {
			sb.append(paint.getColour()).append(paint.getFinishString()).append(" ");
		}
		sb.append(">");
		return sb.toString();
	}
	
	public boolean equals(Object other) {
		
		if (other == null)  {
			return false;
		}
	    if (other == this) {
	    	return true;
	    }
	    if (!(other instanceof Customer)) {
	    	return false;
	    }
	    Customer cust = (Customer) other;
		
		if (this.requestedPaintList == null && cust.getRequestedPaintList() == null){
			return true;
		}

		if(this.requestedPaintList != null && cust.getRequestedPaintList() == null
				|| this.requestedPaintList.size() != cust.getRequestedPaintList().size()){
			return false;
		}

		return this.requestedPaintList.containsAll(cust.getRequestedPaintList()) && cust.getRequestedPaintList().containsAll(this.requestedPaintList);
	}

	/**
	 * Checks if one of the paints in the input list has been requested by the customer
	 * @param productionList list of paints
	 * @return true if there is a match between an item of the input list and a paint requested by the customer, false otherwise
	 */
	public boolean hasAMatchingPaintStyle(PaintFinish[] productionList) {
		
		for(int i=0; i<productionList.length;i++) {
			PaintStyle ps = new PaintStyle(i+1, productionList[i]);
			if (this.requestedPaintList.contains(ps)) {
				return true;
			}
		}
		return false;
	}
}
