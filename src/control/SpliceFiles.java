package control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SpliceFiles {
public static void spliceFiles(String potentiation, String conditions) {
	ArrayList<Double> col0 = readInGenerations(conditions);
	ArrayList<Double> col1 = readInA(conditions);
	ArrayList<Double> col2 = readInB(conditions);
	ArrayList<Double> col3 = readInC(conditions);
	ArrayList<Double> col4 = readInBC(conditions);
	ArrayList<Double> col5 = readInAvgFit(conditions);
	ArrayList<Double> col6 = readInBestFit(conditions);
	ArrayList<Double> col7 = readInPotentiation(potentiation);
	

		
		File file;
		
		
		file = new File("output/" + Constants.FINAL_FILENAME + ".csv");
		
		file.getParentFile().mkdirs();
		
		
		
		//This is a printwriter, it is what prints to a file
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
		
			e.printStackTrace();
			return;
		}

		//Creates a header: We make a "line", which we append stuff onto with commas as dividers
		//Note, this will print on one line/sentence, so you need to use out.print(line) everytime you want a new line
		StringBuilder line = new StringBuilder();
		
		//This is where we create the column headers for the file
		line.append("Generation,");
		line.append("A,");
		line.append("B,");
		line.append("C,");
		line.append("BC,");
		line.append("AvgFit,");
		line.append("BestFit,");
		line.append("Potentiation,");
		
		line.replace(line.length()-1, line.length(), "\n"); //replace the extra comma with a next line

		// If you have the simulation go in like a loop somewhere, you can put this following stuff
		for(int i = 0; i < col0.size(); i++) { //Whatever loop conditions you want, put them here
			
			line.append(col0.get(i) +",");
			line.append(col1.get(i) +",");
			line.append(col2.get(i) +",");
			line.append(col3.get(i) +",");
			line.append(col4.get(i) +",");
			line.append(col5.get(i) +",");
			line.append(col6.get(i) +",");
			line.append(col7.get(i) +",");
			//replace the extra comma with a next line
			line.replace(line.length()-1, line.length(), "\n"); 
			 //finally print the line to the file
		}
		
		out.print(line);
				
		out.close();
	

	}
public static ArrayList<Double> readInPotentiation(String potentiation) {
	String[] potentiationNumbers = ReadColumnCSV.readCol(1, potentiation, ",");
	ArrayList<Double> potentiationList = new ArrayList<Double>();
	for(int i = 1; i < potentiationNumbers.length; i++) {
		potentiationList.add(Double.parseDouble(potentiationNumbers[i]));
	}
	return potentiationList;
	
}
public static ArrayList<Double> readInGenerations(String conditions) {
	String[] generationNumbers = ReadColumnCSV.readCol(0, conditions, ",");
	ArrayList<Double> generationList = new ArrayList<Double>();
	for(int i = 1; i < generationNumbers.length; i++) {
		generationList.add(Double.parseDouble(generationNumbers[i]));
	}
	return generationList;
	
}
public static ArrayList<Double> readInA(String conditions) {
	String[] aNumbers = ReadColumnCSV.readCol(1, conditions, ",");
	ArrayList<Double> aList = new ArrayList<Double>();
	for(int i = 1; i < aNumbers.length; i++) {
		aList.add(Double.parseDouble(aNumbers[i]));
	}
	return aList;
	
}
public static ArrayList<Double> readInB(String conditions) {
	String[] bNumbers = ReadColumnCSV.readCol(2, conditions, ",");
	ArrayList<Double> bList = new ArrayList<Double>();
	for(int i = 1; i < bNumbers.length; i++) {
		bList.add(Double.parseDouble(bNumbers[i]));
	}
	return bList;
	
}
public static ArrayList<Double> readInC(String conditions) {
	String[] cNumbers = ReadColumnCSV.readCol(3, conditions, ",");
	ArrayList<Double> cList = new ArrayList<Double>();
	for(int i = 1; i < cNumbers.length; i++) {
		cList.add(Double.parseDouble(cNumbers[i]));
	}
	return cList;
	
}
public static ArrayList<Double> readInBC(String conditions) {
	String[] bcNumbers = ReadColumnCSV.readCol(4, conditions, ",");
	ArrayList<Double> bcList = new ArrayList<Double>();
	for(int i = 1; i < bcNumbers.length; i++) {
		bcList.add(Double.parseDouble(bcNumbers[i]));
	}
	return bcList;
	
}
public static ArrayList<Double> readInAvgFit(String conditions) {
	String[] avgNumbers = ReadColumnCSV.readCol(5, conditions, ",");
	ArrayList<Double> avgList = new ArrayList<Double>();
	for(int i = 1; i < avgNumbers.length; i++) {
		avgList.add(Double.parseDouble(avgNumbers[i]));
	}
	return avgList;
	
}
public static ArrayList<Double> readInBestFit(String conditions) {
	String[] bestNumbers = ReadColumnCSV.readCol(6, conditions, ",");
	ArrayList<Double>bestList = new ArrayList<Double>();
	for(int i = 1; i < bestNumbers.length; i++) {
		bestList.add(Double.parseDouble(bestNumbers[i]));
	}
	return bestList;
	
}

}
