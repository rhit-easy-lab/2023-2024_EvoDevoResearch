package control;

import java.io.IOException;
import java.util.ArrayList;

public class ReWindPotentiationExperiment {
	public static void reWindPotentiationExperiment() throws IOException {
		//Creates lists of number of times program reaches exaptations and the generation number
		ArrayList exaptationList = new ArrayList<Integer>();
		ArrayList genRunningFrom = new ArrayList<Integer>();
		//Runs up to gen 5
		int stepSize = 5;
		ExaptPotentiation.startRun(stepSize);
		String fileName = "C:\\Users\\renneram\\git\\2023-2024_EvoDevoResearch\\output\\"+"GenerationAt"+stepSize+".csv";
		int currentNum = ExperimentReader.getCurrentNum(fileName);
		int numReRuns = Constants.NUM_GENERATIONS - 5;
		ExperimentReader.readAgents(fileName, Constants.NUM_GENERATIONS);
		int m = numReRuns/stepSize;
		int counter = 1;
		for(int i = 1; i < numReRuns+1; i++) {
			if(i%stepSize == 0) {
				genRunningFrom.add(currentNum + i);
				//Repeat Reader must work
				
				exaptationList.add(ExaptPotentiation.repeatReader(Constants.POTENTIATION_TYPE, fileName));
			}
//			genRunningFrom.add(currentNum + i);
//			//Repeat Reader must work
//			
//			exaptationList.add(ExaptPotentiation.repeatReader(Constants.POTENTIATION_TYPE, fileName));
			if(i!= numReRuns - 1) {
				//Advances 1
				ExaptPotentiation.runForGenStepSize(fileName,currentNum+(i+2));
			}
		}
		for(int k = 0; k < exaptationList.size(); k++) {
			System.out.println();
			System.out.println(genRunningFrom.get(k) + " , " + exaptationList.get(k));
			System.out.println();
			
		}
	}
}
