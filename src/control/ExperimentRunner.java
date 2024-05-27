package control;

import control.ExperimentReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import evolution.Generation;
import evolution.Simulation;
import landscape.ExaptFitness;
import landscape.NKLandscape;
import landscape.NumOnes;

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
		}else{
			System.out.println("No configuration file specified. Continuing with default paramaters.");
		//	PropParser.load(PropParser.defaultFilename);
		}
		ExperimentWriter writer = new ExperimentWriter();
		System.out.println("Writing to csv file " + ExperimentWriter.rename(Constants.FILENAME));
		long startTime = System.currentTimeMillis()/1000;
		switch(Constants.STATE) {
//		case 0:
//			for(int simulationNum=0; simulationNum<Constants.SAMPLE_SIZE; simulationNum++)
//			{
//				Simulation sim = new Simulation();
//				sim.runSimulation();
//				writer.writeSim(sim, Constants.GENERATION_SPACING, Constants.REQUIRE_LAST_GENERATION);
//				
//				long endTime = System.currentTimeMillis()/1000;
//				long estimatedRemainingTime = (endTime-startTime)/(simulationNum+1)*(Constants.SAMPLE_SIZE-simulationNum-1);
//				System.out.println("Simulation " + (simulationNum+1) + " of " + Constants.SAMPLE_SIZE + " complete, estimated minutes remaining: " + Math.round(100.0*estimatedRemainingTime/60.0)/100.0);
//			}
		case 10:
			NewPotentiation.newPotentiation(Constants.POTENTIATION_TYPE);
			break;
		case 11:
			ExperimentReader.runAndPrint();
			break;
		case 13:
			String fileName = new File("output/" + Constants.LINEAGE_POT_FILENAME + ".csv").getAbsolutePath();
			
			LineageReadIn.readAgentsLineage(fileName);
			break;
//		case 7:
//			for(int simulationNum=0; simulationNum<Constants.SAMPLE_SIZE; simulationNum++){
//				Simulation sim = new Simulation(exaptFile);
//					sim.runSimulation();
//					writer.writeSim(sim, Constants.GENERATION_SPACING, Constants.REQUIRE_LAST_GENERATION);
//					
//					long endTime = System.currentTimeMillis()/1000;
//					long estimatedRemainingTime = (endTime-startTime)/(simulationNum+1)*(Constants.SAMPLE_SIZE-simulationNum-1);
//					System.out.println("Simulation " + (simulationNum+1) + " of " + Constants.SAMPLE_SIZE + " complete, estimated minutes remaining: " + Math.round(100.0*estimatedRemainingTime/60.0)/100.0);
//					
//					
//				}
//			break;
		default:
			System.out.println("Invalid State Detected");
	}
		

	
		//
		writer.closePrintWriter();

		System.out.println("");
		System.out.println("Completed, experiment written to " + ExperimentWriter.rename(Constants.FILENAME));
		
		//Test reading, make sure it works

		//finish up
		
	}
}

