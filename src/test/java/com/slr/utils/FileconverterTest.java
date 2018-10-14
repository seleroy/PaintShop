package com.slr.utils;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.slr.model.Customer;
import com.slr.model.PaintFinish;

class FileconverterTest {
	
	public static final String INPUT_PATH_SAMPLE_INPUT = "/input_sample_ok.txt";
	public static final String INPUT_PATH_EMPTY_FILE = "/input_empty_file.txt";
	public static final String INPUT_PATH_UNDEFINED_COLOUR = "/input_undefined_colour.txt";
	public static final String INPUT_PATH_UNDEFINED_FINISH = "/input_undefined_finish.txt";	
	public static final String INPUT_PATH_NO_COLOUR = "/input_no_colour.txt";
	public static final String INPUT_PATH_TRUNCATED_FILE = "/input_truncated_file.txt";
	public static final String INPUT_PATH_FIRSTLINE_NOTINTEGER = "/input_firstline_notinteger.txt";
	
	public static final String OUTPUT_PATH_SAMPLE = "src/test/resources/output_sample_ok.txt";
	public static final String OUTPUT_PATH_EMPTY = "src/test/resources/output_sample_empty.txt";
	public static final String OUTPUT_PATH_NULLLIST = "src/test/resources/output_nulllist.txt";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	@Test
	void readFromInputFile_SampleInput_OK() {
		FileConverter conv = new FileConverter();
		List<Customer> customerList = conv.readFromInputFile(this.getClass().getResource(INPUT_PATH_SAMPLE_INPUT).getPath());
	
		 Customer cust1 = new Customer();
		 cust1.addPaintToRequestList(1,PaintFinish.M);
		 cust1.addPaintToRequestList(3,PaintFinish.G);
		 cust1.addPaintToRequestList(5,PaintFinish.G);
		 Customer cust2 = new Customer();
		 cust2.addPaintToRequestList(2,PaintFinish.G);
		 cust2.addPaintToRequestList(3,PaintFinish.M);
		 cust2.addPaintToRequestList(4,PaintFinish.G);
		 Customer cust3 = new Customer();
		 cust3.addPaintToRequestList(5,PaintFinish.M);
		
		List<Customer> list = new ArrayList<>();
		list.add(cust1);
		list.add(cust2);
		list.add(cust3);

        //Test equals
       assertArrayEquals(customerList.toArray(), list.toArray());
	}
	
	@Test
	void readFromInputFile_EmptyInput_Empty() {
		FileConverter conv = new FileConverter();
		List<Customer> customerList = conv.readFromInputFile(this.getClass().getResource(INPUT_PATH_EMPTY_FILE).getPath());
		assertNotNull(customerList);
		assertEquals(0, customerList.size());
	}
	
	@Test
	void readFromInputFile_UndefinedColour_Exception() {
		FileConverter conv = new FileConverter();
		assertThrows(IllegalArgumentException.class, () -> {
			conv.readFromInputFile(this.getClass().getResource(INPUT_PATH_UNDEFINED_COLOUR).getPath());
		});
	}
	
	@Test
	void readFromInputFile_UndefinedFinish_Exception() {
		FileConverter conv = new FileConverter();
		assertThrows(IllegalArgumentException.class, () -> {
			conv.readFromInputFile(this.getClass().getResource(INPUT_PATH_UNDEFINED_FINISH).getPath());
		});
	}
	
	@Test
	void readFromInputFile_NoColour_Empty() {
		FileConverter conv = new FileConverter();
		List<Customer> customerList = conv.readFromInputFile(this.getClass().getResource(INPUT_PATH_NO_COLOUR).getPath());
		assertNotNull(customerList);
		assertEquals(0, customerList.size());
	}
	
	@Test
	void readFromInputFile_TruncatedFile_Exception() {
		FileConverter conv = new FileConverter();
		assertThrows(IllegalArgumentException.class, () -> {
			conv.readFromInputFile(this.getClass().getResource(INPUT_PATH_TRUNCATED_FILE).getPath());
		});
	}
	
	@Test 
	void saveProductionListToOutputFile_OK() {
		
		String path = OUTPUT_PATH_SAMPLE;
		
		PaintFinish[] prodList = {PaintFinish.G, PaintFinish.M, PaintFinish.M, PaintFinish.G};
		FileConverter conv = new FileConverter();
		conv.saveProductionListToOutputFile(prodList, path);
		
		//Read output file
		try (BufferedReader reader = new BufferedReader(new FileReader(path))){
			String firstLine = reader.readLine();
			assertNotNull(firstLine);
			assertFalse(firstLine.isEmpty());
			String[] finishes = firstLine.split(FileConverter.WORD_SEPARATOR);
			PaintFinish[] finishesFromFile = new PaintFinish[prodList.length];
	
			for (int i=0;i<finishes.length;i++) {
				finishesFromFile[i] = PaintFinish.convertFromFirstLetter(finishes[i]);
			}
			
			assertArrayEquals(prodList, finishesFromFile);			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
	}
	
	@Test 
	void saveProductionListToOutputFile_Empty() {
		
		String path = OUTPUT_PATH_EMPTY;
		
		PaintFinish[] prodList = {};
		FileConverter conv = new FileConverter();
		conv.saveProductionListToOutputFile(prodList, path);
		
		//Read output file
		try (BufferedReader reader = new BufferedReader(new FileReader(path))){
			String firstLine = reader.readLine();
			assertNull(firstLine);

		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	}
	
	@Test 
	void saveProductionListToOutputFile_NullList() {
		
		String path = OUTPUT_PATH_NULLLIST;
		
		PaintFinish[] prodList = null;
		FileConverter conv = new FileConverter();
		conv.saveProductionListToOutputFile(prodList, path);
		
		//Read output file
		try (BufferedReader reader = new BufferedReader(new FileReader(path))){
			String firstLine = reader.readLine();
			assertNull(firstLine);

		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	}
	
	@Test
	void readNbPaints_SampleInput_OK() {
		FileConverter conv = new FileConverter();
		int nbPaints = conv.readNbPaints(this.getClass().getResource(INPUT_PATH_SAMPLE_INPUT).getPath());
		assertEquals(5, nbPaints);
	}

	
	@Test
	void readNbPaints_Empty_Zero() {
		FileConverter conv = new FileConverter();
		int nbPaints = conv.readNbPaints(this.getClass().getResource(INPUT_PATH_EMPTY_FILE).getPath());
		assertEquals(0, nbPaints);
	}
	
	@Test
	void readNbPaints_NotInteger_Exception() {
		FileConverter conv = new FileConverter();
		assertThrows(IllegalArgumentException.class, () -> {
			conv.readNbPaints(this.getClass().getResource(INPUT_PATH_FIRSTLINE_NOTINTEGER).getPath());
		});
	}
	
	
	
}
