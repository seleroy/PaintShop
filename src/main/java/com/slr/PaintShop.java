package com.slr;

import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.slr.model.Customer;
import com.slr.model.PaintFinish;
import com.slr.utils.ErrorMessages;
import com.slr.utils.FileConverter;

public class PaintShop {

	public static final String DEFAULT_INPUT_FILE = "src/main/resources/input_sample_ok.txt";
	public static final String DEFAULT_OUTPUT_FILE = "src/main/resources/output_sample_ok.txt";

	
	private static final Logger LOGGER = Logger.getLogger(PaintShop.class.getName());
	
	//Stores the number of paint to produce:
	private int nbPaintStyles = 0;
	//Stores the customers and their requests:
	private List<Customer> customerRequestsList;
	//Stores the final list of paints that will be produced:
	private PaintFinish[] productionList;
	private String inputPath;
	private String outputPath;
	
	public PaintShop(String inputPath, String outputPath) {
		this.inputPath = inputPath;
		this.outputPath = outputPath;
	}
	
	//Setters and getters:
	public void setNbPaintStyles(int nbPaintStyles) {
		this.nbPaintStyles = nbPaintStyles;
	}
	
	public int getNbPaintStyles() {
		return nbPaintStyles;
	}

	public List<Customer> getCustomerRequestsList() {
		return customerRequestsList;
	}


	public void setCustomerRequestsList(List<Customer> customerRequestsList) {
		this.customerRequestsList = customerRequestsList;
	}


	public PaintFinish[] getProductionList() {
		return productionList;
	}


	public void setProductionList(PaintFinish[] productionList) {
		this.productionList = productionList;
	}
	
	
	/**
	 * Browses the list of requested paints from the customers
	 * and fills in the productionList with final list of paints
	 * to produces
	 */
	void decideColoursToProduce() {
		
		if (nbPaintStyles == 0) {
			return;
		}
		
		//Start by setting all paint to Glossy = best case scenario
		productionList = new PaintFinish[nbPaintStyles];
		Arrays.fill(productionList, PaintFinish.G);
		
		/* Browse all customers:
		 * - If there is a match between the current production list and a requested paint, the request is satisfied
		 * - Otherwise, we need to produce their Matt paint (if possible) and recheck that it still works for all customers
		 * Worst case scenario is O(colours x customers) but the number of matts is optimised
		 */
		boolean needToCheckAllCustomers = true;
		
		while (needToCheckAllCustomers) {
			needToCheckAllCustomers = false;
			
			for(Customer cust : customerRequestsList) {
				
				if(!cust.hasAMatchingPaintStyle(productionList)) {
					int colourToSwap = cust.getColourOfMattPaintOrZero();
					if (colourToSwap == 0) {
						//Production list does not work for customer and customer has no Matt -> no solution
						System.out.println(ErrorMessages.ERR_MSG_NO_SOLUTION);
						//The instructions do not tell if a file must be created in this case
						//Assumption: create an output file but keeps it empty as we won't produce any paint
						productionList = null;
						return;
					}
					
					//We swap the style to Matt to satisfy our customer -> need to recheck other customers
					if (productionList[colourToSwap-1] == PaintFinish.G) {
						productionList[colourToSwap-1] = PaintFinish.M;
						needToCheckAllCustomers = true;
					}
				}
			}
		}
	}
	
	/**
	 * Entry point of the program
	 * - Reads the paints requested by the customer from a file
	 * - Determines the list of paints to produce
	 * - Writes the production list to an output file
	 */
	void process() {
		
		//Logger settings
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.ALL);
		LOGGER.addHandler(handler);
		LOGGER.setLevel(Level.INFO);
		
		FileConverter fileConverter = new FileConverter();
		this.nbPaintStyles = fileConverter.readNbPaints(this.inputPath);
		LOGGER.fine("Nb of paints: " +Integer.toString(this.nbPaintStyles));
		
		this.customerRequestsList = fileConverter.readFromInputFile(this.inputPath);
		LOGGER.fine("Request List after parsing: " + this.customerRequestsList);
		
		this.decideColoursToProduce();
		fileConverter.saveProductionListToOutputFile(this.productionList, this.outputPath);
		
	}
	
	
	public static void main(String[] args) {
				
		//Uses the following default input files and output files if not provided in arguments:
		String outputFilePath=DEFAULT_OUTPUT_FILE;
		String inputFilePath=DEFAULT_INPUT_FILE;
			
		// Gives the ability to set the file paths in a command line argument to be able to test different files
		// Command <inputFilePath> <outputFilePath>
		if (args.length > 1) {
			inputFilePath=args[0];
			outputFilePath=args[1];
		} else if (args.length == 1) {
			inputFilePath=args[0];
		}
		
		PaintShop paintShop = new PaintShop(inputFilePath, outputFilePath);
		paintShop.process();		
		
	}
}
