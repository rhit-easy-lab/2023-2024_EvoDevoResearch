package control;
import agent.Agent;
import agent.Phenotype;
import agent.Step;
import agent.ExaptPhenotype;



import control.ReadColumnCSV;
import evolution.Generation;
import evolution.SelectionStrategy;
import evolution.SelectionTournament;
import evolution.SelectionTruncation;
import evolution.Simulation;
import landscape.ExaptFitness;
import landscape.FitnessFunction;
import landscape.NKLandscape;
import landscape.NumOnes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ExperimentReader {
	//Used to generate new generations
	public static void readExcel(String file) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(file));  
		scanner.useDelimiter(",");   //For CSV files
		while (scanner.hasNext()){  //Loops while there still is something to loop over
							
		}   
		
		
	}
	
	public static int readAgents(String file, int stoppingGenNum) throws IOException {

		String[] totalGens = ReadColumnCSV.readCol(1, file, ",");
		String[] agentNums = ReadColumnCSV.readCol(2, file, ",");
		String[] function = ReadColumnCSV.readCol(4, file, ",");
		String[] blockOptions = ReadColumnCSV.readCol(5, file, ",");
		String[] programCurrent = ReadColumnCSV.readCol(6, file, ",");
		String[] parentNum = ReadColumnCSV.readCol(9, file, ",");
		//Total number of generations
		int totalGenNum = Integer.parseInt(totalGens[1]);
		int resumeNum = totalGenNum+1;
		//Column Reading works
		String functionString = function[1].toLowerCase();
		FitnessFunction fitFunction = null;
		if(functionString.equals("nklandscape")) {
			fitFunction = new NKLandscape(SeededRandom.getInstance().nextInt());
		}else {
			if(functionString.equals("numones")) {
				fitFunction = new NumOnes();
			}else {
				if(functionString.equals("exaptfitness")) {
					fitFunction = new ExaptFitness();
				}else {
					System.out.println("FITNESS_FUNCTION_TYPE not recognized");
				}
			}
		}
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
		
		

		List programList = getProgramNew(programCurrent);
		List<List<List<Step>>> blockyList = getBlocksNew(blockOptions);
		ArrayList<Agent> agList = generateNewAgents(agentNums, programList, blockyList, fitFunction);
		Generation gen30 = getNewGen(agList);
//		System.out.println(resumeNum);
	//	ExperimentWriter writer = new ExperimentWriter();
				
//		System.out.println("Reading/Writing to csv file " + ExperimentWriter.rename(Constants.FILENAME));
//		
		//Run all of our experiments, and write them to the file as we go. Original:
		long startTime = System.currentTimeMillis()/1000;
		int finalFitCount = 0;
		for(int simulationNum=0; simulationNum<Constants.SAMPLE_SIZE; simulationNum++)
		{
			//edit here
			Simulation sim = new Simulation(gen30, fitFunction, select);
			sim.reRunSimulation(resumeNum, stoppingGenNum);
			if(sim.getFinalFitness() == Constants.GLOBAL_MAX) {
				finalFitCount=1;
			}
			
		
			}
	//	writer.closePrintWriter();
		return finalFitCount;
//		
		
		
	}
	public static int getResumeNum(String file) {
		String[] totalGens = ReadColumnCSV.readCol(1, file, ",");
		//Total number of generations
		int totalGenNum = Integer.parseInt(totalGens[1]);
		int resumeNum = totalGenNum+1;
		return resumeNum;
	}
	public static int getCurrentNum(String file) {
		String[] currentGens = ReadColumnCSV.readCol(1, file, ",");
		//Total number of generations
		int currentGen = Integer.parseInt(currentGens[1]);
		//Generation file is on
		return currentGen;
	}
	public static void advanceStep(String file, int stoppingGenNum) throws IOException {

		String[] totalGens = ReadColumnCSV.readCol(1, file, ",");
		String[] agentNums = ReadColumnCSV.readCol(2, file, ",");
		int totalGenNum = Integer.parseInt(totalGens[1]);
		int resumeNum = totalGenNum+1;
		String[] function = ReadColumnCSV.readCol(4, file, ",");
		String functionString = function[1].toLowerCase();
		FitnessFunction fitFunction = null;
		if(functionString.equals("nklandscape")) {
			fitFunction = new NKLandscape(SeededRandom.getInstance().nextInt());
		}else {
			if(functionString.equals("numones")) {
				fitFunction = new NumOnes();
			}else {
				if(functionString.equals("exaptfitness")) {
					fitFunction = new ExaptFitness();
				}else {
					System.out.println("FITNESS_FUNCTION_TYPE not recognized");
				}
			}
		}
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
		
		String[] blockOptions = ReadColumnCSV.readCol(5, file, ",");
		String[] programCurrent = ReadColumnCSV.readCol(6, file, ",");

		List programList = getProgramNew(programCurrent);
		List<List<List<Step>>> blockyList = getBlocksNew(blockOptions);
		ArrayList<Agent> agList = generateNewAgents(agentNums, programList, blockyList, fitFunction);
		Generation gen30 = getNewGen(agList);
//		System.out.println(resumeNum);
		ExperimentWriter writer = new ExperimentWriter();
		ExperimentWriter writer2 = new ExperimentWriter("GenerationAt"+stoppingGenNum+"");
//		System.out.println("Reading/Writing to csv file " + ExperimentWriter.rename(Constants.FILENAME));
//		
		//Run all of our experiments, and write them to the file as we go. Original:
		for(int gen = resumeNum; gen < Constants.NUM_GENERATIONS; gen++) {
			Simulation sim = new Simulation(gen30, fitFunction, select);
			
			sim.reRunSimulation(resumeNum, stoppingGenNum);
			if(gen == stoppingGenNum) {
				//Have to subtract resumeNum from index because length(sim.getGenerations) = gen - resumeNum
				writer.writeGen(sim.getGenerations().get(gen - resumeNum - 1), Integer.toString(gen), Constants.GENERATION_SIZE);
				writer2.writeGen(sim.getGenerations().get(gen - resumeNum - 1), Integer.toString(gen), Constants.GENERATION_SIZE);
			}
		}
		

		writer2.closePrintWriter();
		writer.closePrintWriter();

		
		
	}
	//Re-start simulation at our new generation starting number, instantiate more agents with the programs and blocks
	public static ArrayList<Agent> generateNewAgents(String[] agentIDs, List<List<Integer>> listProgram, List<List<List<Step>>> listBlocks, FitnessFunction fittyFunc) {
		ArrayList<Agent> agy = new ArrayList<Agent>();
		//Just exapt fitness for now, will change later\
		for(int r = 0; r<listProgram.size(); r++) {
			Agent aget = new Agent(fittyFunc, listBlocks.get(r), agentIDs[r+1], listProgram.get(r));
			agy.add(aget);
		}
		
		return agy;
		
	}
	public static Generation getNewGen(ArrayList<Agent> agentList) {
		Generation newest = new Generation(agentList);
		return newest;
	}
	
	
	public static List<List<List<Step>>> getBlocksNew(String[] blocks){
		List<List<List<Step>>> stepList = new ArrayList<List<List<Step>>>();
		for(int k = 1; k < blocks.length; k++) {
			String[] blocksNow = blocks[k].split(";");
			ArrayList<String> arria = new ArrayList<String>();
			for(int i = 0; i<blocksNow.length; i++) {
				arria.add(blocksNow[i]);
			}
			arria.removeAll(Collections.singleton(";"));
			List<List<Step>> bigBlocks = new ArrayList<List<Step>>();
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
				

			stepList.add(bigBlocks);
		}
		return stepList;
		
	}
	public static List<List<Integer>> getProgramNew(String[] programs){
		List<List<Integer>> newPrograms = new ArrayList<List<Integer>>();
		for(int i = 1; i < programs.length; i++) {
			String[] numberDenoters = programs[i].split("|");
			ArrayList<String> arry = new ArrayList<String>();
			for(int j = 0; j<numberDenoters.length;j++) {
				if(numberDenoters[j]!="|") {
					arry.add(numberDenoters[j]);
				}
			}
			arry.removeAll(Collections.singleton("|"));

			List<Integer> individProgram = new ArrayList<Integer>();
			for(int k = 0; k < arry.size(); k++) {
				individProgram.add(Integer.parseInt(arry.get(k)));
			}
			newPrograms.add(individProgram);
		}
		
		return newPrograms;
		
	}
	

	

	

}
