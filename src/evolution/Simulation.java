package evolution;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import agent.Agent;
import control.Constants;
import control.SeededRandom;
import landscape.ExaptFitness;
import landscape.FitnessFunction;
import landscape.NKLandscape;
import landscape.NumOnes;

/**
 * Represents a single evolutionary simulation on a single landscape.
 * 
 * @author Jacob Ashworth, and some changes by Emile Marois
 * 
 *
 */
public class Simulation {
	
	//Fitness Function used in the simulation
	private FitnessFunction fitFunction;
	//Stores all generations of the simulation
	private ArrayList<Generation> generations = new ArrayList<>();
	//Used to generate new generations
	private SelectionStrategy selectionStrategy;
	private int exaptNum;
	
	//keeps track of condition

	
	public Simulation(int exaptNum)
	{
		this.exaptNum = exaptNum;
		//Switch statement to control which fitness function is initialized
		switch(Constants.FITNESS_FUNCTION_TYPE.toLowerCase()) {
			case "nklandscape":
				this.fitFunction = new NKLandscape(SeededRandom.getInstance().nextInt());
				break;
			case "numones":
				this.fitFunction = new NumOnes();
				break;
			case "exaptfitness":
				this.fitFunction = new ExaptFitness();
				break;
			default:
				System.out.println("FITNESS_FUNCTION_TYPE not recognized");
				this.fitFunction = null;
		}
		
		//Switch statement to control selection type used in evolutionary loop
		switch(Constants.SELECTION_TYPE.toLowerCase()) {
			case "truncation":
				this.selectionStrategy = new SelectionTruncation();
				break;
			case "tournament":
				this.selectionStrategy = new SelectionTournament();
				break;
			default:
				System.out.println("SELECTION_TYPE not recognized");
				this.fitFunction = null;
		}
		
		Generation initialGeneration = new Generation(fitFunction);
		initialGeneration.executeAllStrategies();
		generations.add(initialGeneration);
	}
	public Simulation(String stringy)
	{
		//Switch statement to control which fitness function is initialized
		switch(Constants.FITNESS_FUNCTION_TYPE.toLowerCase()) {
			case "nklandscape":
				this.fitFunction = new NKLandscape(SeededRandom.getInstance().nextInt());
				break;
			case "numones":
				this.fitFunction = new NumOnes();
				break;
			case "exaptfitness":
				this.fitFunction = new ExaptFitness();
				break;
			default:
				System.out.println("FITNESS_FUNCTION_TYPE not recognized");
				this.fitFunction = null;
		}
		
		//Switch statement to control selection type used in evolutionary loop
		switch(Constants.SELECTION_TYPE.toLowerCase()) {
			case "truncation":
				this.selectionStrategy = new SelectionTruncation();
				break;
			case "tournament":
				this.selectionStrategy = new SelectionTournament();
				break;
			default:
				System.out.println("SELECTION_TYPE not recognized");
				this.fitFunction = null;
		}
		
		Generation initialGeneration = new Generation(fitFunction);
		initialGeneration.executeAllStrategies();
		generations.add(initialGeneration);
	}
	public Simulation()
	{
		this.exaptNum = -1;
		//Switch statement to control which fitness function is initialized
		switch(Constants.FITNESS_FUNCTION_TYPE.toLowerCase()) {
			case "nklandscape":
				this.fitFunction = new NKLandscape(SeededRandom.getInstance().nextInt());
				break;
			case "numones":
				this.fitFunction = new NumOnes();
				break;
			case "exaptfitness":
				this.fitFunction = new ExaptFitness();
				break;
			default:
				System.out.println("FITNESS_FUNCTION_TYPE not recognized");
				this.fitFunction = null;
		}
		
		//Switch statement to control selection type used in evolutionary loop
		switch(Constants.SELECTION_TYPE.toLowerCase()) {
			case "truncation":
				this.selectionStrategy = new SelectionTruncation();
				break;
			case "tournament":
				this.selectionStrategy = new SelectionTournament();
				break;
			default:
				System.out.println("SELECTION_TYPE not recognized");
				this.fitFunction = null;
		}
		
		Generation initialGeneration = new Generation(fitFunction);
		initialGeneration.executeAllStrategies();
		generations.add(initialGeneration);
	}
	
	public Simulation(Generation initialGeneration, FitnessFunction fitFunction, SelectionStrategy selectStrategy)
	{
		this.fitFunction = fitFunction;
		this.selectionStrategy = selectStrategy;
		initialGeneration.executeAllStrategies();
		generations.add(initialGeneration);
	}
	
	
	public void runSimulation()
	{
		runSimulation(Constants.NUM_GENERATIONS);
	}
	
	/**
	 * Runs an evolutionary loop for numGenerations
	 * 
	 * @param numGenerations number of generations in evolutionary loop
	 */
	
	public void runSimulation(int numGenerations)
	{
		
		File file = new File("output/" + Constants.CONDITION_FILENAME + "_" + exaptNum + ".csv");
		
		file.getParentFile().mkdirs();
		
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
		
			e.printStackTrace();
			return;
		}
		StringBuilder header = new StringBuilder();
		header.append("Generation" + ",");
		header.append("A" + ",");
		header.append("B" + ",");
		header.append("C" + ",");
		header.replace(header.length()-1, header.length(), "\n");
		out.print(header);
		
		for(int generationNumber = 1; generationNumber < numGenerations+1; generationNumber++)
		{
			//if enough generations have passed, invoke the fitness function's dynamic behavior
			if(Constants.GENERATIONS_PER_CYCLE % Constants.GENERATIONS_PER_CYCLE == 0)
			{
				fitFunction.changeCycle();
			}
			//make and run the next generation
			Generation nextGeneration = selectionStrategy.getNextGeneration(generations.get(generations.size()-1));
			nextGeneration.executeAllStrategies();
			generations.add(nextGeneration);
			
			int condA = 0;
			int condB = 0;
			int condC = 0;
			
			int trackA = 0;
			int trackB = 0;
			int trackC = 0;
			//detects conditions
			for(Agent a: nextGeneration.getAgents()) {
				
				if(a.detectCopy()) {
					System.out.println("Agent " + a.getID() + "has reached condition A");
					condA++;
				}
				

				if(a.detectFunctionalMutation()) {
					System.out.println("Agent " + a.getID() + "has reached condition B");
					condB++;
				}
				
				
				if(a.detectReintroduction()) {
					System.out.println("Agent " + a.getID() + "has reached condition C");
					condC++;
				}
				
				if(a.getOccuredA()) {
					trackA++;
				}
				if(a.getOccuredB()) {
					trackB++;
				}
				if(a.getOccuredC()) {
					trackC++;
				}
				
			}
			
			System.out.println("Condition A has been reached " + condA + " times");
			System.out.println("Condition B has been reached " + condB + " times");
			System.out.println("Condition C has been reached " + condC + " times");
			
			
			//condition printing
			
			StringBuilder line = new StringBuilder();
			double aPercent = (100 * trackA)/Constants.GENERATION_SIZE;
			double bPercent = (100 * trackB)/Constants.GENERATION_SIZE;
			double cPercent = (100 * trackC)/Constants.GENERATION_SIZE;
			
			line.append(generationNumber + ",");
			line.append(aPercent + ",");
			line.append(bPercent + ",");
			line.append(cPercent + ",");
			line.replace(line.length()-1, line.length(), "\n");
			out.print(line);
			
			
			
			
		}
		if(Constants.FITNESS_FUNCTION_TYPE.toLowerCase().equals("exaptfitness")) {
			
			
			
			
			
			this.printLineage();
		}
		
		out.close();
		
	}
//** Should run the simulation starting from the starting generation number passed in.
	public void reRunSimulation(int startGenNum, int numGenerations)
	{
		for(int generationNumber = startGenNum; generationNumber < numGenerations; generationNumber++)
		{
			//if enough generations have passed, invoke the fitness function's dynamic behavior
			if(Constants.GENERATIONS_PER_CYCLE % Constants.GENERATIONS_PER_CYCLE == 0)
			{
				fitFunction.changeCycle();
			}
			//make and run the next generation
			Generation nextGeneration = selectionStrategy.getNextGeneration(generations.get(generations.size()-1));
			nextGeneration.executeAllStrategies();
			generations.add(nextGeneration);
		}
	}
	
	
	
	public ArrayList<Generation> getGenerations() {
		return generations;
	}
	public double getFinalFitness(){
		List<Agent> agentList = generations.get(generations.size()-1).getAgents();
		double fitnessFirst = 0;
		for(int t=0; t < agentList.size();t++) {
			if(agentList.get(t).getFinalFitness() > fitnessFirst) {
				fitnessFirst = agentList.get(t).getFinalFitness();
			}
		}
		return fitnessFirst;
	}
	
	
	//Finds an agent with max fitness for exaptation
	public void printLineage() {
		Agent guy = null;
		//finds the latest max fitness one? I'll change this once we've decided what we want to focus on.
		for(int i = generations.size() - 1; i > -1; i--) {
			if(generations.get(i).getBest().exaptBest()) {
				guy = generations.get(i).getBest();
				break;
			}
		}

		if(guy != null) {
			this.setUpPrinting(guy);
	
		}
		
	}
	
	//makes a filewriter, sets up a header and begins a recursive call
	public void setUpPrinting(Agent guy) {
		File file;

		if(exaptNum != -1)
			file = new File("output/" + Constants.LINEAGE_FILENAME + "_" + exaptNum + ".csv");
		else
			file = new File("output/" + Constants.LINEAGE_FILENAME + ".csv");
		
		file.getParentFile().mkdirs();
		
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
		
			e.printStackTrace();
			return;
		}

		//Creates a header
		StringBuilder line = new StringBuilder();
		line.append("Run,");
		line.append("Generation,");
		line.append("Final_Fitness,");
		line.append("Agent_ID,");
		line.append("Cond A,");
		line.append("Cond B,");
		line.append("Cond C,");
	
		line.append("Function,");
		
		//spacing
		line.append(" ,");
		
		for(int i = 0; i < Constants.UPPER_NUMBER_OF_BLOCKS; i++) {
			
			for(int j = 0; j < Constants.BLOCK_LENGTH; j++) {
				line.append("Block " + i + "." );
				line.append(j + ",");
			}
			line.append(" ,");
		}
		

		for(int i = 0; i < Constants.PROGRAM_LENGTH; i++) {
			line.append("P" + i + ",");
		}
		
		


		


		

//		line.append("Parent_number,");

		
		
		line.replace(line.length()-1, line.length(), "\n"); //replace the extra comma with a next line
		out.print(line);
		
		guy.printLineage(out, 1, generations.size()-1);
		
		out.close();
		
		System.out.println("Lineage written");
		
	}
	
	

}




