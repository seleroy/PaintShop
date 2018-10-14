package com.slr.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PaintFinishTest {

	@Test
	void convertFromFirstLetter_G() {
		PaintFinish pf = PaintFinish.convertFromFirstLetter("G");
		assertEquals(pf, PaintFinish.G);
	}
	
	@Test
	void convertFromFirstLetter_M() {
		PaintFinish pf = PaintFinish.convertFromFirstLetter("M");
		assertEquals(pf, PaintFinish.M);
	}
	
	@Test
	void convertFromFirstLetter_Undefined() {
		PaintFinish pf = PaintFinish.convertFromFirstLetter("ROBERT");
		assertEquals(pf, PaintFinish.UNDEFINED);
	}
	
	@Test
	void convertFromFirstLetter_Empty() {
		PaintFinish pf = PaintFinish.convertFromFirstLetter("");
		assertEquals(pf, PaintFinish.UNDEFINED);
	}

}
