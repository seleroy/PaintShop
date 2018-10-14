package com.slr.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PaintStyleTest {

	@Test
	void equals_OK() {
		PaintStyle ps = new PaintStyle(1,PaintFinish.G);
		PaintStyle ps2 = new PaintStyle(1,PaintFinish.G);
		
		assertTrue(ps.equals(ps2));
	}
	
	@Test
	void equals_DifferentColour() {
		PaintStyle ps = new PaintStyle(1,PaintFinish.G);
		PaintStyle ps2 = new PaintStyle(2,PaintFinish.G);
		
		assertFalse(ps.equals(ps2));
	}
	
	@Test
	void equals_DifferentFinish() {
		PaintStyle ps = new PaintStyle(1,PaintFinish.G);
		PaintStyle ps2 = new PaintStyle(2,PaintFinish.M);
		
		assertFalse(ps.equals(ps2));
	}
	
	@Test
	void equals_Null() {
		PaintStyle ps = null;
		PaintStyle ps2 = new PaintStyle(2,PaintFinish.M);
		
		assertFalse(ps2.equals(ps));
	}
	
	@Test
	void equals_NotPaintStyle() {
		PaintStyle ps = new PaintStyle(1,PaintFinish.G);
		Customer cust = new Customer();
		
		assertFalse(ps.equals(cust));
	}
	
	@Test
	void equals_SameInstance() {
		PaintStyle ps = new PaintStyle(1,PaintFinish.G);
		PaintStyle ps2 = ps;
		
		assertTrue(ps2.equals(ps));
	}

}
