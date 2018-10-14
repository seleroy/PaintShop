package com.slr.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CustomerTest {

	@Test
	void equals_OK() {
		Customer cust = new Customer();
		Customer cust2 = new Customer();
		
		cust.addPaintToRequestList(1,PaintFinish.G);
		cust.addPaintToRequestList(3,PaintFinish.M);
		
		cust2.addPaintToRequestList(3,PaintFinish.M);
		cust2.addPaintToRequestList(new PaintStyle(1, PaintFinish.G));
		
		assertTrue(cust.equals(cust2));
	}
	
	@Test
	void equals_EmptyLists() {
		Customer cust = new Customer();
		Customer cust2 = new Customer();
				
		assertTrue(cust.equals(cust2));
	}
	
	@Test
	void equals_DifferentListSize() {
		Customer cust = new Customer();
		Customer cust2 = new Customer();
		
		cust.addPaintToRequestList(1,PaintFinish.G);
		
		cust2.addPaintToRequestList(3,PaintFinish.M);
		cust2.addPaintToRequestList(1,PaintFinish.G);
		
		assertFalse(cust.equals(cust2));
	}
	
	@Test
	void equals_DifferentContentSameSize() {
		Customer cust = new Customer();
		Customer cust2 = new Customer();
		
		cust.addPaintToRequestList(1,PaintFinish.G);
		cust.addPaintToRequestList(3,PaintFinish.G);
		
		cust2.addPaintToRequestList(3,PaintFinish.M);
		cust2.addPaintToRequestList(1,PaintFinish.G);
		
		assertFalse(cust.equals(cust2));
	}
	
	@Test
	void equals_Null() {
		Customer cust = null;
		Customer cust2 = new Customer();
		
		assertFalse(cust2.equals(cust));
	}
	
	@Test
	void equals_NotCustomer() {
		PaintStyle ps = new PaintStyle(1,PaintFinish.G);
		Customer cust = new Customer();
		
		assertFalse(cust.equals(ps));
	}
	
	@Test
	void equals_SameInstance() {
		Customer cust = new Customer();
		Customer cust2 = cust;
		
		assertTrue(cust2.equals(cust));
	}

}
