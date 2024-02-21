package control;

import java.io.IOException;
import java.util.ArrayList;

import evolution.Simulation;

public class ExaptPotentiation {
	public static void exaptPotentiation(int potentiationType) throws IOException {
		//PotentiationType = 0 indicates regular potentiation
		//PotentiationType = 1 indicates constant run potentiation
		if(potentiationType != 0) {
			if(potentiationType != 1) {
				potentiationType = 0;
			}
			
		}
		int stat = 0;
		int stepSized = 0;
		ArrayList startGen = new ArrayList<Integer>();
		ArrayList numTimesReached = new ArrayList<Integer>();
		String fileNameThing = "C:\\Users\\renneram\\git\\2023-2024_EvoDevoResearch\\output\\"+Constants.FILENAME+".csv";
		// Runs program for the specified number of steps
		startRun(Constants.STEP_SIZE_NUM);
		for(int i = 0; i < Constants.NUM_GENERATIONS; i++) {
			if(i*Constants.STEP_SIZE_NUM < Constants.NUM_GENERATIONS) {
				stepSized++;
			}
		}
		startGen.add(Constants.STEP_SIZE_NUM);
		numTimesReached.add(repeatReader(potentiationType, fileNameThing));
		
		for(int j = 2; j < stepSized; j++) {
			runForGenStepSize(fileNameThing,Constants.STEP_SIZE_NUM*j);
			int throwaway = j*Constants.STEP_SIZE_NUM;
			startGen.add(j*Constants.STEP_SIZE_NUM);
			numTimesReached.add(repeatReader(potentiationType, fileNameThing));
			
		}
		
		System.out.println();
		System.out.println();
		for(int k=0; k<numTimesReached.size(); k++) {
			System.out.print(startGen.get(k)+" ,");
			System.out.print(numTimesReached.get(k));
			System.out.println();
			
		}	

		
	}
	//Runs original generation for some number of generations-- each "step"
	
	public static void startRun(int startStopPoint) throws IOException {

		ExperimentWriter writer = new ExperimentWriter();

		ExperimentWriter writer2 = new ExperimentWriter("GenerationAt"+startStopPoint);
		for(int gen = 0; gen < Constants.NUM_GENERATIONS; gen++) {
			//Issue could be here:
			Simulation sim = new Simulation();
			
			sim.reRunSimulation(0, startStopPoint);
			if(gen == startStopPoint) {
				writer.writeGen(sim.getGenerations().get(gen - 1), Integer.toString(gen), Constants.GENERATION_SIZE);
				writer2.writeGen(sim.getGenerations().get(gen - 1), Integer.toString(gen), Constants.GENERATION_SIZE);
			}
		}
		writer.closePrintWriter();
		writer2.closePrintWriter();
	}
	
public static void runForGenStepSize(String file,int constNum) throws IOException {
		ExperimentReader.advanceStep(file, constNum);	
}

//Re-runs the simulation for some number of generations from the passed-in file
	public static int repeatReader(int yesNo, String file) throws IOException {
		int count = 0;
		//Gets number to resume on
		int resuming = ExperimentReader.getResumeNum(file);
		if(yesNo == 0) {
			//ReadAgents runs the file
			for(int tot = 0; tot < Constants.POTENTIATION_RUN_NUM; tot++) {
				
				int tester = ExperimentReader.readAgents(file, Constants.NUM_GENERATIONS);

				if(tester>0) {
					count++;
				}
			}
		}else {
			for(int tot = 0; tot < Constants.POTENTIATION_RUN_NUM; tot++) {
				
				int tester = ExperimentReader.readAgents(file, Constants.CONSTANT_POTENTIATION_RUN_NUM + resuming);

				if(tester>0) {
					count++;
				}
			}
		}
		
		//Number getting final fitness from some run
		return count;
		
	}

}
