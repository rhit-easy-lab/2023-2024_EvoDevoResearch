package control;

import java.io.IOException;
import java.util.ArrayList;

public class NewPotentiation {
public static void newPotentiation(int potentiationType) throws IOException {
	ArrayList startGen = new ArrayList<Integer>();
	ArrayList numTimesReached = new ArrayList<Integer>();
	int stepSized = 0;
	for(int i = 0; i < Constants.NUM_GENERATIONS; i++) {
		if((i+1)*Constants.STEP_SIZE_NUM < Constants.NUM_GENERATIONS) {
			stepSized++;
		}
	}
	System.out.println("Stepsize"+Constants.STEP_SIZE_NUM);
	System.out.println("StepSized"+stepSized);
	
	
	for(int i = 1; i < stepSized + 1; i++) {
		int a = Constants.STEP_SIZE_NUM*i;
		String fileName = "C:\\Users\\renneram\\OneDrive - Rose-Hulman Institute of Technology\\Desktop\\2023-2024_EvoDevoResearch\\output\\GenerationAt"+a+".csv";
		//String fileName = "C:\\Users\\renneram\\git\\2023-2024_EvoDevoResearch\\output\\"+"GenerationAt"+a+".csv";
		startGen.add(a);
		System.out.println("GenerationAt"+a);
		numTimesReached.add(ExaptPotentiation.repeatReader(potentiationType, fileName));
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
///EvoDevoNKFLCoreMerged/output/GenerationAt60.csv
