package control;

import control.ExperimentReader;

import java.io.IOException;

import evolution.Generation;
import evolution.Simulation;

/**
 * This class exists to house the main method of the simulator,
 * as well as handling connection to parts external to the simulator,
 * such as loading in config files and writing results to files.
 * 
 * @author Jacob Ashworth
 *
 */
public class ExperimentRunner {
	
	public static void main(String[] args) throws IOException 
	{
		//The first argument passed into ExperimentRunner is the configuration file.
		//If no configuration file is specified, it runs using defaultConfig.properties
		
		int exaptFile = -1;
		if(args.length>=1)
		{
			System.out.println("Using the configuration file: " + args[0]);
			PropParser.load(args[0]);
			//for multiple runs
			if(args.length >= 2) { 
				
				exaptFile = Integer.parseInt(args[1]);
			}
		}
		else
		{
			System.out.println("No configuration file specified. Continuing with default paramaters.");
			PropParser.load(PropParser.defaultFilename);
		}
		if(Constants.STATE == 2) {
			
			//Add state about reading from .csv, running
			ExperimentReader.readAgents("C:\\Users\\renneram\\git\\2023-2024_EvoDevoResearch\\output\\ExaptTesting5.csv", Constants.NUM_GENERATIONS);
		
	}else {
		if(Constants.STATE == 3) {
				int counter2 = 0;
				for(int tot = 0; tot < Constants.POTENTIATION_RUN_NUM; tot++) {
					int tester = ExperimentReader.readAgents("C:\\Users\\renneram\\git\\2023-2024_EvoDevoResearch\\output\\GenerationTesting.csv", Constants.NUM_GENERATIONS);
					if(tester>0) {
						counter2++;
					}
				
				//Number getting final fitness from some run
				System.out.println();
				System.out.println();
				System.out.println("Counter!" + counter2 + " Out of " + Constants.POTENTIATION_RUN_NUM);
				
			}
		}else {

			ExperimentWriter writer = new ExperimentWriter();
			if(Constants.STATE == 4) {
				ExaptPotentiation.exaptPotentiation(0);
			
			}else {
				if(Constants.STATE == 5) {
					ExaptPotentiation.exaptPotentiation(1);
				}else {
					if(Constants.STATE == 6) {
						RewindGen.reWind("C:\\Users\\renneram\\git\\2023-2024_EvoDevoResearch\\output\\"+Constants.ZOOM_IN_FILE_NAME+".csv");
					}else {
						if(Constants.STATE == 9) {
							ReWindPotentiationExperiment.reWindPotentiationExperiment();
						}else {
							if(Constants.STATE == 10) {
									NewPotentiation.newPotentiation();
							}
						}
					}
				}
			}
		
		System.out.println("Writing to csv file " + ExperimentWriter.rename(Constants.FILENAME));
		//Run all of our experiments, and write them to the file as we go. Original:
		long startTime = System.currentTimeMillis()/1000;

		if(Constants.STATE == 0) {
			for(int gen = 0; gen < Constants.NUM_GENERATIONS; gen++) {
				Simulation sim = new Simulation();
				
				sim.runSimulation();
				if(gen == Constants.GEN_STOP_NUM) {
					sim.runSimulation(Constants.GEN_STOP_NUM);
					writer.writeGen(sim.getGenerations().get(gen - 1), Integer.toString(gen), Constants.GENERATION_SIZE);
				}
			}
		}else {
			if(Constants.STATE == 1) {
				for(int simulationNum=0; simulationNum<Constants.SAMPLE_SIZE; simulationNum++)
				{
					Simulation sim = new Simulation();
					sim.runSimulation();
					writer.writeSim(sim, Constants.GENERATION_SPACING, Constants.REQUIRE_LAST_GENERATION);
					
					long endTime = System.currentTimeMillis()/1000;
					long estimatedRemainingTime = (endTime-startTime)/(simulationNum+1)*(Constants.SAMPLE_SIZE-simulationNum-1);
					System.out.println("Simulation " + (simulationNum+1) + " of " + Constants.SAMPLE_SIZE + " complete, estimated minutes remaining: " + Math.round(100.0*estimatedRemainingTime/60.0)/100.0);
				}
			} else if(Constants.STATE == 7){
				for(int simulationNum=0; simulationNum<Constants.SAMPLE_SIZE; simulationNum++){
					Simulation sim = new Simulation(exaptFile);
						sim.runSimulation();
						writer.writeSim(sim, Constants.GENERATION_SPACING, Constants.REQUIRE_LAST_GENERATION);
						
						long endTime = System.currentTimeMillis()/1000;
						long estimatedRemainingTime = (endTime-startTime)/(simulationNum+1)*(Constants.SAMPLE_SIZE-simulationNum-1);
						System.out.println("Simulation " + (simulationNum+1) + " of " + Constants.SAMPLE_SIZE + " complete, estimated minutes remaining: " + Math.round(100.0*estimatedRemainingTime/60.0)/100.0);
						
						
					}

				}
			}
		//
		writer.closePrintWriter();
		}
		
	}
		System.out.println("");
		System.out.println("Completed, experiment written to " + ExperimentWriter.rename(Constants.FILENAME));
		
		//Test reading, make sure it works

		//finish up
		
	}


	}

