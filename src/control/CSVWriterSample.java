package control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVWriterSample {

	public static void csvWrite(ArrayList<Double> exapts, ArrayList<Integer> genNums) {

		
		File file;
		
		
		//This is how you choose the file
		
		file = new File("output/" + Constants.POTENTIATION_FILENAME + ".csv");
		
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
		line.append("Generation Number,");
		line.append("potentiation,");
		
		line.replace(line.length()-1, line.length(), "\n"); //replace the extra comma with a next line

		// If you have the simulation go in like a loop somewhere, you can put this following stuff
		for(int i = 1; i < genNums.size(); i++) { //Whatever loop conditions you want, put them here
			
			line.append(genNums.get(i) +",");
			line.append(exapts.get(i) +",");
			
			//replace the extra comma with a next line
			line.replace(line.length()-1, line.length(), "\n"); 
			 //finally print the line to the file
		}
		
		out.print(line);
				
		out.close();

		

	}

}
