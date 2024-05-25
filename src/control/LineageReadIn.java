package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import agent.Agent;
import agent.Step;
import evolution.Generation;
import evolution.SelectionStrategy;
import evolution.SelectionTournament;
import evolution.SelectionTruncation;
import evolution.Simulation;
import landscape.ExaptFitness;
import landscape.FitnessFunction;
import landscape.NKLandscape;
import landscape.NumOnes;

public class LineageReadIn {
	public static void readExcel(String file) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(file));  
		scanner.useDelimiter(",");   //For CSV files
		while (scanner.hasNext()){  //Loops while there still is something to loop over
							
		}   
		
		
	}
	
	public static void readAgentsLineage(String file) throws IOException {
		//Reads in important info from file
		String[] genNumberThing = ReadColumnCSV.readCol(1, file, ",");
		System.out.println(genNumberThing.length);
		String[] fitnessReadThing = ReadColumnCSV.readCol(2, file, ",");
		String[] agentIDs = ReadColumnCSV.readCol(3, file, ",");
		String[] blockOptions = ReadColumnCSV.readCol(9, file, ",");
		String[] programCurrent = ReadColumnCSV.readCol(10, file, ",");
		ArrayList<ArrayList<String>> agentLists = new ArrayList<ArrayList<String>>();
		for(int i = 1; i < genNumberThing.length; i++) {
			ArrayList<String> agent = new ArrayList<String>();
			agent.add(genNumberThing[i]);
			agent.add(fitnessReadThing[i]);
			agent.add(blockOptions[i]);
			agent.add(programCurrent[i]);
			agentLists.add(agent);
			
		}
		ArrayList<Agent> agentsReCreated = new ArrayList<Agent>();
		for(int k = 0; k < agentLists.size(); k++) {
			
		}
		FitnessFunction fitFunction = new ExaptFitness();
		SelectionStrategy select = null;
		if(Constants.RERUN_SELECTION_TYPE.toLowerCase().equals("truncation")) {
			select = new SelectionTruncation();
		}else {
			if(Constants.RERUN_SELECTION_TYPE.toLowerCase().equals("tournament")) {
				select = new SelectionTournament();
			}else {
				System.out.println("RERUN_SELECTION_TYPE not recognized");
			}
		}
		ArrayList<Double> inties = new ArrayList<Double>();
	
		for(int k = 0; k < agentLists.size(); k++) {
			List<List<Step>> blocky = getBlockNew(agentLists.get(k).get(2));
			List<Integer> programy = getProgramNew(agentLists.get(k).get(3));
			agentsReCreated.add(generateNewAgent(agentIDs[k + 1], programy, blocky, fitFunction, Double.parseDouble(agentLists.get(k).get(1))));
			inties.add(Double.parseDouble(agentLists.get(k).get(1)));
			 
		}
		ArrayList<Generation> generationList = new ArrayList<Generation>();
		int agentGenLength = agentsReCreated.size();
		for(int j = 0; j < agentsReCreated.size(); j++) {
			ArrayList<Agent> agentPlaceHolder = new ArrayList<Agent>();
			for(int h = 0; h < Constants.GENERATION_SIZE; h++) {
				agentPlaceHolder.add(agentsReCreated.get(agentGenLength - 1 - j));
				
			}
			Generation generationPlaceHolder= new Generation(agentPlaceHolder);
			generationList.add(generationPlaceHolder);
		}
			
		for(int m = 0; m < generationList.size(); m++) {
				ExperimentWriter writer2 = new ExperimentWriter("GenerationAt"+(m )+"");
				writer2.writeGen(generationList.get(m), genNumberThing[genNumberThing.length- (m + 1)], Constants.GENERATION_SIZE, inties.get(inties.size() - (m + 1)));
				writer2.closePrintWriter();
			
		}
	}
		
//		//Total number of generations
//		int totalGenNum = Integer.parseInt(totalGens[1]);
//		int resumeNum = totalGenNum+1;
//		//Column Reading works
//		String functionString = function[1].toLowerCase();
//		//Gets the correct fitness function
//		FitnessFunction fitFunction = null;
//		if(functionString.equals("nklandscape")) {
//			fitFunction = new NKLandscape(SeededRandom.getInstance().nextInt());
//		}else {
//			if(functionString.equals("numones")) {
//				fitFunction = new NumOnes();
//			}else {
//				if(functionString.equals("exaptfitness")) {
//					fitFunction = new ExaptFitness();
//				}else {
//					System.out.println("FITNESS_FUNCTION_TYPE not recognized");
//				}
//			}
//		}
//		//Gets the correct selection type
//		SelectionStrategy select = null;
//		if(Constants.RERUN_SELECTION_TYPE.toLowerCase().equals("truncation")) {
//			select = new SelectionTruncation();
//		}else {
//			if(Constants.RERUN_SELECTION_TYPE.toLowerCase().equals("tournament")) {
//				select = new SelectionTournament();
//			}else {
//				System.out.println("RERUN_SELECTION_TYPE not recognized");
//			}
//		}
//		
//		
//
//		List programList = getProgramNew(programCurrent);
//		List<List<List<Step>>> blockyList = getBlocksNew(blockOptions);
//		ArrayList<Agent> agList = generateNewAgents(agentNums, programList, blockyList, fitFunction);
//		Generation gen30 = getNewGen(agList);
//
//		//Run all of our experiments, and write them to the file as we go. Original:
//		long startTime = System.currentTimeMillis()/1000;
//		int finalFitCount = 0;
//		for(int simulationNum=0; simulationNum<Constants.SAMPLE_SIZE; simulationNum++)
//		{
//			//edit here
//			//Creates simulation using the new generation, the selected fitness function, and selection strategy
//			Simulation sim = new Simulation(gen30, fitFunction, select);
//			sim.reRunSimulation(resumeNum, stoppingGenNum);
//			if(sim.getFinalFitness() == Constants.GLOBAL_MAX) {
//				finalFitCount=1;
//			}
//			//System.out.print(sim.getGenerations().get(sim.getGenerations().size() - 1).getAgents());
//		
//			}
//		
//	//	writer.closePrintWriter();
//		return finalFitCount;
//		
		
		
	
	public static Agent generateNewAgent(String agentIDs, List<Integer> listProgram, List<List<Step>> listBlocks, FitnessFunction fittyFunc, double fitnessThing1) {
		ArrayList<Agent> agy = new ArrayList<Agent>();
		//Just exapt fitness for now, will change later\
		Agent aget = new Agent(fittyFunc, listBlocks, agentIDs, listProgram, fitnessThing1);
		
		return aget;
		
	}
	
	public static int getGenCurrent(String genString) {
		int genCurrent = Integer.parseInt(genString);
		return genCurrent;
	}
	public static double getFitnessCurrent(String fitString) {
		double fitnessCurrent = Double.parseDouble(fitString);
		return fitnessCurrent;
	}
	public static List<List<Step>> getBlockNew(String blocks){
		List<List<Step>> bigBlocks = new ArrayList<List<Step>>();
			String[] blocksNow = blocks.split(";");
			ArrayList<String> arria = new ArrayList<String>();
			for(int i = 0; i<blocksNow.length; i++) {
				arria.add(blocksNow[i]);
			}
			arria.removeAll(Collections.singleton(";"));
			
			for(int j = 0; j < arria.size(); j++) {
				String stri = arria.get(j).substring(1, arria.get(j).length() - 1);
				arria.set(j, stri);
			}
			for(int n = 0; n < arria.size(); n++) {
				String[] b = arria.get(n).split(":");
				
				List<Step> reStep = new ArrayList<Step>();
				for(int h = 0; h < b.length; h++) {
					if(b[h].equals("SteepestFall")) {
						
						reStep.add(Step.SteepestFall);
					}else if(b[h].equals("SteepestClimb")) {
						
						reStep.add(Step.SteepestClimb);
					}else {
						reStep.add(Step.SameStep);
					}
				}
				
				bigBlocks.add(reStep);
			


			}
			
		
		return bigBlocks;
		
	}
	
	
	public static List<Integer> getProgramNew(String program){
		String[] numberDenoters = program.split("|");
		ArrayList<String> arry = new ArrayList<String>();
		for(int j = 0; j<numberDenoters.length;j++) {
			if(numberDenoters[j]!="|") {
				//Adds program in order
				arry.add(numberDenoters[j]);
			}
		}
			arry.removeAll(Collections.singleton("|"));

			List<Integer> individProgram = new ArrayList<Integer>();
			for(int k = 0; k < arry.size(); k++) {
				//Adds the blocks in order
				individProgram.add(Integer.parseInt(arry.get(k)));
			}
			//Adds the program for each agent
		
		
		return individProgram;
		
	}
}
