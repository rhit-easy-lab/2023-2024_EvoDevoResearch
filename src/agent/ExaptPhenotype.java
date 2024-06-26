package agent;

import java.util.ArrayList;
import java.util.List;

import control.Constants;

public class ExaptPhenotype extends Phenotype{
	private List<Phenotype> neighbors;
	private int nodeNumber;
	private double fitness;
	private int tracking = 1;
	public ExaptPhenotype(int nodenumber1, double fitness1, List<Phenotype> neighbors1) {
		this.neighbors = neighbors1;
		this.nodeNumber = nodenumber1;
		this.fitness = fitness1;
	}
	public ExaptPhenotype(ExaptPhenotype pheno) {
		this.nodeNumber = pheno.getNumber();
		this.fitness = pheno.getFitness();
		this.neighbors = pheno.getNeighbors();
		this.tracking = tracking+1;
	}

	
//The mainbranchNumber is the number of nodes that should be on the main branch. 
//The localmax is the local maximum 
//The globalmax is the globalmaximum
//The junction1 is the node number of the place where the downwards branch starts. It is important to note that node numbers start at 0.
//The downBranchNumber is the number of nodes on the downwards branch
//The upBranchNumber is the number of nodes on the branch starting at the end of the downwards branch. Do not include the last node of the downBranch in this number.
//The nodeNumbers start at 0, juntion1 is the nodenumber of the first junction.
	
	

public static Phenotype getFirst(int mainbranchNumber, double localMax, double globalMax, int junction1, double localMin, int downBranchNumber, int upBranchNumber) {
	List<Phenotype> mainBranch = new ArrayList<Phenotype>();
	List<ArrayList<Phenotype>> edges = new ArrayList<ArrayList<Phenotype>>();
	double stepSize1 = localMax/(mainbranchNumber - 1);
//	System.out.println("UP STEP SIZE " + stepSize1 + "!");
	//Makes the main Branch
	for(int k = 0; k < mainbranchNumber; k++) {
		List<Phenotype> neighbors = new ArrayList<Phenotype>();
		mainBranch.add(new ExaptPhenotype(k, stepSize1*k, neighbors));
		if( k== mainbranchNumber - 1) {
	//	System.out.println("New Branch Local" +  k*stepSize1);
		}
		if(k == 0) {
		//	System.out.println("Node A"+ stepSize1*k);
		}
	}
	//Finds the junction
	int junctionA = junction1;
	//Makes the downwards branch
	
	double stepSize2 = (junctionA*stepSize1 - localMin)/downBranchNumber;
//	System.out.println("DOWN STEP SIZE " + stepSize2 + "!");
	for(int h = 1; h < downBranchNumber + 1; h++) {
		List<Phenotype> neighbors2 = new ArrayList<Phenotype>();
		//2*h as a test
		double ytb = (stepSize1*junctionA- h*stepSize2);
		mainBranch.add(new ExaptPhenotype(mainbranchNumber + h, ytb, neighbors2));
		if(h == downBranchNumber) {
		//	System.out.println("LocalMin" + ytb);
		}
	}
//
//	double firstFitness = junctionA*stepSize1 - (downBranchNumber)*stepSize2;
	double stepSize3 = (globalMax - localMin)/(upBranchNumber);
//	System.out.print("!" + stepSize2 + "!");
//	System.out.print("?" + localMin + "?");
//	System.out.print("..." + upBranchNumber + "...");
	//Makes upwards Branch
	//upBranchNumber or upBranchNumber + 1?
	for(int m = 1; m < upBranchNumber + 1; m++) {
		List<Phenotype> neighbors3 = new ArrayList<Phenotype>();
		double yta = localMin + (m)*stepSize3;
		mainBranch.add(new ExaptPhenotype((mainBranch.size() - 1) + m, yta, neighbors3));
//		if(m == upBranchNumber) {
//			System.out.print("w" + yta + "w");
//		}
	}
	mainBranch.get(0).getNeighbors().add(mainBranch.get(1));
	//Makes the main branch of the graph (above); adds the neighbors (below) (If this doesn't work, possibly make the getNeighbors an arraylist and re-make ExaptPhenotype with the new neighbors?
	for(int j = 1; j < (mainbranchNumber - 1); j++) {
		mainBranch.get(j).getNeighbors().add(mainBranch.get(j - 1));
		mainBranch.get(j).getNeighbors().add(mainBranch.get(j + 1));
	}
	mainBranch.get(mainbranchNumber - 1).getNeighbors().add(mainBranch.get(mainbranchNumber - 2));
	//New branch 3 & 4 stuff
	mainBranch.get(junctionA).getNeighbors().add(mainBranch.get(mainbranchNumber));
	mainBranch.get(mainbranchNumber).getNeighbors().add(mainBranch.get(junctionA));
	mainBranch.get(mainbranchNumber).getNeighbors().add(mainBranch.get(mainbranchNumber + 1));
	for(int y = mainbranchNumber + 1; y < (mainBranch.size() - 1); y++) {
		mainBranch.get(y).getNeighbors().add(mainBranch.get(y - 1));
		mainBranch.get(y).getNeighbors().add(mainBranch.get(y + 1));
	}
	mainBranch.get(mainBranch.size() - 1).getNeighbors().add(mainBranch.get(mainBranch.size() - 2));
	
	for(int i = 0; i < mainbranchNumber; i++) {
		ArrayList<Phenotype> placeholder = new ArrayList<Phenotype>();
		if(i == 0) {
			placeholder.add(mainBranch.get(1));
		}else {
			//Checks if we have junctionA
			if(i == junctionA) {
				placeholder.add(mainBranch.get(i - 1));
				placeholder.add(mainBranch.get(i + 1));
				placeholder.add(mainBranch.get(mainbranchNumber));
			}else {
				if(i == mainbranchNumber - 1) {
					placeholder.add(mainBranch.get(mainbranchNumber - 2));
				}else {
					placeholder.add(mainBranch.get(i - 1));
					placeholder.add(mainBranch.get(i + 1));
				}
				}
			}
		edges.add(placeholder);
		
		///Add the rest to edges, the list of edges in the network. Then extend this to encompass the entire graph.
	}
	
	//Adds the branch3 edges
	for(int g = mainbranchNumber; g < mainBranch.size(); g++) {
		ArrayList<Phenotype> placeholder3 = new ArrayList<Phenotype>();
		if(g == mainbranchNumber) {
			placeholder3.add(mainBranch.get(junctionA));
			placeholder3.add(mainBranch.get(g + 1));
		}else {
			if(g == mainBranch.size() - 1) {
				placeholder3.add(mainBranch.get(g - 1));
			}else {
				placeholder3.add(mainBranch.get(g - 1));
				placeholder3.add(mainBranch.get(g + 1));
			}
		}
		edges.add(placeholder3);
	}
//	//Testing
//	System.out.print("Start" + mainBranch.get(0).getNeighbors().size() + "END");
//	System.out.print("Start" + mainBranch.get(junctionA).getNeighbors().size() + "END");
//	System.out.print("Edges" + edges.get(junctionA +1).size() +"END");
//	//Testing
//	for(int c = 0; c < mainBranch.size(); c++) {
//		if(mainBranch.get(c).getNeighbors().size() == 3) {
//			System.out.print("+");
//			mainBranch.get(c).getNeighbors().get(2);
//		}else {
//			System.out.print("-");
//		}
//	}
//	System.out.print(mainBranch.size() + "L");
	//Testing 
	return mainBranch.get(0);
	
}
	

public double getFitness() {
	return this.fitness;
}

@Override
public List<Phenotype> getNeighbors() {
	return this.neighbors;
}


@Override
public Phenotype getIdenticalCopy() {
	// TODO Auto-generated method stub
	Phenotype copy = new ExaptPhenotype((ExaptPhenotype) ExaptPhenotype.getFirst(Constants.MAIN_BRANCH_NUMBER, 
			Constants.LOCAL_MAX, Constants.GLOBAL_MAX, Constants.JUNCTION_NUM, Constants.LOCAL_MIN, 
			Constants.DOWN_BRANCH_NUMBER, Constants.UP_BRANCH_NUMBER));
	return copy;
}

public int getNumber() {
	return this.nodeNumber;
}
public int tracker() {
	return this.tracking;
}
@Override
public void mutate() {
	// TODO Auto-generated method stub
	
}
}
