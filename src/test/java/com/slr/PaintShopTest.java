package com.slr;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.slr.utils.ErrorMessages;
import com.slr.utils.FileConverter;

class PaintShopTest {
	
	public static final String INPUT_PATH_SAMPLE_INPUT = "src/test/resources/input_sample_ok.txt";
	public static final String INPUT_PATH_NO_SOLUTION = "src/test/resources/input_no_solution.txt";
	public static final String INPUT_PATH_RICHER_EXAMPLE = "src/test/resources/input_richer_example.txt";
	public static final String INPUT_PATH_EXAMPLE_3 = "src/test/resources/input_example_3.txt";
	public static final String INPUT_PATH_EXAMPLE_4 = "src/test/resources/input_example_4.txt";
	
	public static final String OUTPUT_PATH_SAMPLE = "src/test/resources/output_sample_ok.txt";
	public static final String OUTPUT_PATH_NO_SOLUTION = "src/test/resources/output_no_solution.txt";
	public static final String OUTPUT_PATH_RICHER_EXAMPLE = "src/test/resources/output_richer_example.txt";
	public static final String OUTPUT_PATH_EXAMPLE_3 = "src/test/resources/output_example_3.txt";
	public static final String OUTPUT_PATH_EXAMPLE_4 = "src/test/resources/output_example_4.txt";
	
	String[] extractArrayFromFile(String path) {
		//Read output file
		try (BufferedReader reader = new BufferedReader(new FileReader(path))){

			String firstLine = reader.readLine();
			if(firstLine == null) {
				return null;
			}
			String[] finishes = firstLine.split(FileConverter.WORD_SEPARATOR);

			return finishes;		

		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		return null;
				
	}

	@Test
	void main_SampleInput() {
		PaintShop shop = new PaintShop(INPUT_PATH_SAMPLE_INPUT, OUTPUT_PATH_SAMPLE);
		shop.process();
		String[] expectedLine = {"G","G","G","G","M"};
		String[] output = extractArrayFromFile(OUTPUT_PATH_SAMPLE);
		Assert.assertArrayEquals(output, expectedLine);		
	}
	
	@Test
	void main_RicherExample() {
		PaintShop shop = new PaintShop(INPUT_PATH_RICHER_EXAMPLE, OUTPUT_PATH_RICHER_EXAMPLE);
		shop.process();
		String[] expectedLine = {"G","M","G","M","G"};
		String[] output = extractArrayFromFile(OUTPUT_PATH_RICHER_EXAMPLE);
		Assert.assertArrayEquals(output, expectedLine);		
	}
	
	@Test
	void main_NoSolution() {
		PaintShop shop = new PaintShop(INPUT_PATH_NO_SOLUTION, OUTPUT_PATH_NO_SOLUTION);

		//Redirect stdout to a an output stream and check we have the expected "no solution exists" output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream sout = System.out;
		System.setOut(ps);

		
		shop.process();
		String[] output = extractArrayFromFile(OUTPUT_PATH_NO_SOLUTION);
		Assert.assertNull(output);
		
		//Revert stdout
		System.out.flush();
		System.setOut(sout);
		
		Assert.assertEquals(baos.toString().trim(), ErrorMessages.ERR_MSG_NO_SOLUTION.trim());
	}
	
	@Test
	void main_ThirdWorkingExample() {
		PaintShop shop = new PaintShop(INPUT_PATH_EXAMPLE_3, OUTPUT_PATH_EXAMPLE_3);
		shop.process();
		String[] expectedLine = {"M","M"};
		String[] output = extractArrayFromFile(OUTPUT_PATH_EXAMPLE_3);
		Assert.assertArrayEquals(output, expectedLine);		
	}
	
	@Test
	void main_FourthWorkingExample() {
		PaintShop shop = new PaintShop(INPUT_PATH_EXAMPLE_4, OUTPUT_PATH_EXAMPLE_4);
		shop.process();
		String[] expectedLine = {"G","G","G"};
		String[] output = extractArrayFromFile(OUTPUT_PATH_EXAMPLE_4);
		Assert.assertArrayEquals(output, expectedLine);		
	}

}
