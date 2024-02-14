package control;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadColumnCSV {
	public static void run(int column, String filename, String delim) {
		String[] data = readCol(column, filename, delim);
		for(int i = 0; i < data.length; i++) {
			System.out.println(data[i]);
		}
	}
	public static String[] readCol(int col, String filePath, String delimiter) {
		String data[];
		String currentLine;
		ArrayList<String> colData = new ArrayList<String>();
		
		try {
			FileReader fileReader = new FileReader(filePath); 
			BufferedReader bufferReader = new BufferedReader(fileReader); //Sets up thing to read file
			while((currentLine = bufferReader.readLine()) != null) {
				//Continues iterating as long as there is a next line in the file
				data = currentLine.split(delimiter); //Splits a string into an array, each thing that doesn't have a comma between it and something else is an element
				colData.add(data[col]); //Adds data in desired column to ArrayList
			}
		}
		catch(Exception e) {
			System.out.println(e); //Prints what went wrong
			return null;
		}
		return colData.toArray(new String[0]); //Converts to a string array
		
	}
}
