package com.slr.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.slr.model.Customer;
import com.slr.model.PaintFinish;
import com.slr.model.PaintStyle;

public class FileConverter {

	public static final String WORD_SEPARATOR = " ";
	
	/**
	 * Opens an input file and reads the first line to retrieve the number of paints
	 * @param inputPath file to read
	 * @return number of paints or 0 if line is empty
	 * @throws IllegalArgumentException if the content of the first line is not an integer
	 */
	public int readNbPaints(String inputPath) {
		int nbPaints = 0;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))){
			String firstLine = reader.readLine();
			if (firstLine == null || firstLine.isEmpty()) {
				return 0;
			}
			nbPaints = Integer.parseInt(firstLine);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(ErrorMessages.ERROR_MSG_WRONG_FIRST_LINE);
		}
		return nbPaints;
	}
	
	/**
	 * Creates a customer from a list of requested (colour, finish) and 
	 * adds the customer to the customers List
	 * @param customersList List of customer where customer must be added
	 * @param line couples of colours and finishes requested by the customer
	 * @param nbPaints number max of paints
	 * @throws IllegalArgumentException if the file is not formatted correctly
	 */
	private void addCustomerToList(List<Customer> customersList, 
								   List<String> line, 
								   int nbPaints) {
		
		if (line == null) {
			return;
		}
		
		Customer customer = new Customer();
		
		for (int i = 0; i < line.size(); i+=2) {
	
			//Check that we have a couple of values
			if ( (i + 1) >= line.size()) {
				throw new IllegalArgumentException(ErrorMessages.ERROR_MSG_WRONG_LINE_FORMAT);
			}

			Integer colour = Integer.parseInt(line.get(i));
			PaintFinish finish = PaintFinish.convertFromFirstLetter(line.get(i+1));
			
			//Do not accept invalid colour numbers
			if(colour <= 0 || colour > nbPaints) {
				throw new IllegalArgumentException(ErrorMessages.ERROR_MSG_UNDEFINED_COLOUR);
			}
			
			//Do not accept unrecognized finish type
			if(finish == PaintFinish.UNDEFINED) {
				throw new IllegalArgumentException(ErrorMessages.ERROR_MSG_UNDEFINED_FINISH);
			}
			
			customer.addPaintToRequestList(new PaintStyle(colour, finish));
		}

		customersList.add(customer);
	}
	
	/**
	 * Reads the paints requested by the customers from an input file
	 * @param inputPath file holding the number of paints and requested paints for each customer
	 * @return list of customers and their individual list of requested paints
	 */
	public List<Customer> readFromInputFile(String inputPath) { 
		List<Customer> customersList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))){
		
			String firstLine = reader.readLine();
			if(firstLine != null && !firstLine.isEmpty()) {
				int nbPaints = Integer.parseInt(firstLine);
				List<List<String>> values = reader.lines().map(line -> Arrays.asList(line.split(WORD_SEPARATOR))).collect(Collectors.toList());	
				values.forEach(value -> addCustomerToList(customersList, value,nbPaints));
			}
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} 
		return customersList;
	}
	
	/**
	 * Writes an array finishes to a file
	 * @param prodFinishesArray array of finishes that will be produced
	 * @param filePath output file
	 */
	public void saveProductionListToOutputFile(PaintFinish[] prodFinishesArray, String filePath) {
		FileWriter fWriter;
		
		try {
			fWriter = new FileWriter(filePath);
			BufferedWriter bufWriter = new BufferedWriter(fWriter);
			
			if (prodFinishesArray != null) {	
				StringBuilder sb = new StringBuilder();
				for (PaintFinish finish: prodFinishesArray) {
					if (sb.length() > 0) {
						sb.append(WORD_SEPARATOR);
					}
					sb.append(finish.toString());
				}
				bufWriter.write(sb.toString());
			}
			
			bufWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
