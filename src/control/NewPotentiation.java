package control;

import java.io.IOException;
import java.util.ArrayList;

public class NewPotentiation {
public static void newPotentiation() throws IOException {
	ArrayList startGen = new ArrayList<Integer>();
	String fileNameThing = "C:\\Users\\renneram\\git\\2023-2024_EvoDevoResearch\\output\\"+Constants.FILENAME+".csv";
	ArrayList numTimesReached = new ArrayList<Integer>();
	
	ExaptPotentiation.startRun(5);
	for(int k = 1; k < 10; k++) {
		ExaptPotentiation.runForGenStepSize(fileNameThing,5*k);
	}
	
	
	
	
	
	
	
	for(int i = 1; i < 10 ; i++) {
		int a = 5*i;
		String fileName = "C:\\Users\\renneram\\git\\2023-2024_EvoDevoResearch\\output\\"+"GenerationAt"+a+".csv";
		startGen.add(a);
		numTimesReached.add(ExaptPotentiation.repeatReader(0, fileName));
	}
	
	
	
	System.out.println();
	System.out.println();
	for(int j = 0; j < numTimesReached.size(); j++) {
		System.out.println(startGen.get(j) + "; " + numTimesReached.get(j));
		System.out.println();
		System.out.println();
	}
}
}
