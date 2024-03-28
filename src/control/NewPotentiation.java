package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewPotentiation {
public static void newPotentiation(int potentiationType) throws IOException {
	ArrayList startGen = new ArrayList<Integer>();
	ArrayList<Integer> numTimesReached = new ArrayList<Integer>();
	ArrayList<Double> exaptPercent = new ArrayList<Double>();
	int stepSized = 0;
	for(int i = 0; i < Constants.NUM_GENERATIONS; i++) {
		if((i)*Constants.STEP_SIZE_NUM < Constants.NUM_GENERATIONS) {
			stepSized++;
		}
	}
	System.out.println("Stepsize"+Constants.STEP_SIZE_NUM);
	System.out.println("StepSized"+stepSized);
	
	
	for(int k = 0; k < stepSized + 1; k++) {
		int a = Constants.STEP_SIZE_NUM*k;

		
		String fileName = new File("output/GenerationAt"+a+".csv").getAbsolutePath();
		//String fileName = "C:\\Users\\renneram\\git\\2023-2024_EvoDevoResearch\\output\\"+"GenerationAt"+a+".csv";
		startGen.add(a);
		System.out.println("GenerationAt"+a);
		numTimesReached.add(ExaptPotentiation.repeatReader(potentiationType, fileName));
	}
	
	
	
	System.out.println();
	System.out.println();
	for(int j = 0; j < numTimesReached.size(); j++) {
		double percent = (1.0*numTimesReached.get(j)/Constants.POTENTIATION_RUN_NUM)*100;
		exaptPercent.add(percent);
		
	}
	for(int m = 0; m < numTimesReached.size(); m++) {
		System.out.println(startGen.get(m) + ";" + exaptPercent.get(m));
		
	}
	System.out.println("HI");
	CSVWriterSample.csvWrite(exaptPercent, startGen);
}
}
///EvoDevoNKFLCoreMerged/output/GenerationAt60.csv
