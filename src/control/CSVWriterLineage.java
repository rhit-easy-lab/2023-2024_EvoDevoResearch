//package control;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//
//public class CSVWriterLineage {
//	//String[] args, 
//
//	public static void csvWrite(ArrayList<Integer> simulationNums, ArrayList<Integer> genNums, ArrayList<String> agent_Number, ArrayList<Integer> generationSize, ArrayList>) {
//
//		
//		File file;
//		
//		
//		//This is how you choose the file
//		
//		file = new File("output/" + Constants.POTENTIATION_FILENAME + ".csv");
//		
//		file.getParentFile().mkdirs();
//		
//		
//		
//		//This is a printwriter, it is what prints to a file
//		PrintWriter out;
//		try {
//			out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
//		} catch (IOException e) {
//		
//			e.printStackTrace();
//			return;
//		}
//
//		//Creates a header: We make a "line", which we append stuff onto with commas as dividers
//		//Note, this will print on one line/sentence, so you need to use out.print(line) everytime you want a new line
//		StringBuilder line = new StringBuilder();
//		
//		//This is where we create the column headers for the file
//		line.append("Simulation,");
//		line.append("Generation,");
//		line.append("Agent_num,");
//		line.append("Generation_size,");
//		line.append("Function,");
//		line.append("Block,");
//		line.append("Program,");
//		line.append("Strategy,");
//		line.append("Final_Fitness,");
//		
//		line.replace(line.length()-1, line.length(), "\n"); //replace the extra comma with a next line
//		
////		out.print(line);
//		
//		// If you have the simulation go in like a loop somewhere, you can put this following stuff
//		for(int i = 0; i < genNums.size(); i++) { //Whatever loop conditions you want, put them here
//			
//			line.append(genNums.get(i) +",");
//			line.append(exapts.get(i) +",");
////			line.append("A " + ",");
////			line.append("B " + ",");
//			
//			//replace the extra comma with a next line
//			line.replace(line.length()-1, line.length(), "\n"); 
//			 //finally print the line to the file
//		}
//		
//		out.print(line);
//		
//
//
//		
//		
////		line.replace(line.length()-1, line.length(), "\n"); //replace the extra comma with a next line
////		
////		out.print(line); //finally print the line to the file
//
//		
//		out.close();
//
//		
//
//	}
//
//}
//
