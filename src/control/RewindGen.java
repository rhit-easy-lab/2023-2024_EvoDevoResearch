package control;

import java.io.IOException;
import java.util.ArrayList;

public class RewindGen {

	public static void reWind(String fileName) throws IOException {
		//Creates lists of number of times program reaches exaptations and the generation number
		ArrayList exaptationList = new ArrayList<Integer>();
		ArrayList genRunningFrom = new ArrayList<Integer>();
		//Gets the generation we're currently on
		int currentNum = ExperimentReader.getCurrentNum(fileName);
		int numReRuns = Constants.EARLY_STOP_GEN_NUM - currentNum;
		ExperimentReader.readAgents(fileName, Constants.NUM_GENERATIONS);
		for(int i = 0; i < numReRuns; i++) {
			genRunningFrom.add(currentNum + i);
			//Repeat Reader must work
			exaptationList.add(ExaptPotentiation.repeatReader(Constants.POTENTIATION_TYPE, fileName));
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
