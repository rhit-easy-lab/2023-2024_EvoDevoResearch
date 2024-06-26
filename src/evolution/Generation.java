package evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.Agent;
import agent.Phenotype;
import control.Constants;
import landscape.FitnessFunction;

/**
 * The Generation class is primarily just a set of utility functions
 * built around a list of agents. It represents a single generation of
 * agents in the simulation.
 * 
 * @author Jacob Ashworth
 *
 */
public class Generation {
	
	private List<Agent> agents;
	public static int genNum = 0;
	/**
	 * Constructor that creates a random generation of agents
	 * @param f
	 */
	public Generation(FitnessFunction f)
	{
		agents = new ArrayList<Agent>();
		if(Constants.SINGLE_START)
		{
			Phenotype p = Agent.getRandomPhenotype();
			for(int agent=0; agent<Constants.GENERATION_SIZE; agent++)
			{
				String id = agent + "~"+ "XXX";

				agents.add(new Agent(f,p,id));
			}
		}
		else
		{
			for(int agent=0; agent<Constants.GENERATION_SIZE; agent++)
			{
				agents.add(new Agent(f));
			}
		}
	}
	
	/**
	 * Constructor that creates a generation using a list of agents
	 * @param agents
	 */
	public Generation(List<Agent> agents)
	{
		genNum++;
		this.agents = agents;
	}
	public static int returnCounter() {
		return genNum;
	}
	/**
	 * Runs each agent's strategy
	 */
	public void executeAllStrategies()
	{
		for(Agent a : agents)
		{
			a.executeStrategy();
		}
		//sort at the end
		this.sortAgents();
		for(int i = 0; i < agents.size();i++) {
			agents.get(i).setID(i);
		}
	}
	
	public List<Agent> getAgents()
	{
		return agents;
	}
	
	//Since the best agent is accessed far more frequently than the worst, a sorted
	//generation has the best agent at index 0
	public void sortAgents()
	{
		Collections.sort(agents);
		Collections.reverse(agents);
	}

	public Agent getBest() {
		this.sortAgents();
		return agents.get(0);
	}
	
	public double getAverageFinalFitness() {
		double totalFitness = 0;
		for(Agent a : agents)
		{
			totalFitness += a.getFinalFitness();
		}
		return totalFitness / agents.size();
	}
}
 