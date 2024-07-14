package baseline;

import java.util.ArrayList;
import java.util.HashMap;


import utils.Edge;
import utils.Graph;

public class PruneP extends BaselineP{
	
	public PruneP(Graph graph, int size, double ar, double proba){
		super(graph, size, ar, proba);
	}
	
	@Override
	protected double computeThreshold(int h, int l, int p) {
		double r;
		if (h != -1)
			r = Math.pow((double) (array.get(h).getWeight()), p)
					+ 2 * Math.pow((double) (array.get(l).getWeight()), p);
		else
			r = Math.pow((double) (array.get(0).getWeight()), p)
					+ 2 * Math.pow((double) (array.get(l).getWeight()), p);
		return r;
	}
	
	@Override
	protected void move(HashMap<Integer, HashMap<Integer, Double>> rm, HashMap<Integer, HashMap<Integer, Double>> add,
			ArrayList<Edge> array, int l) {
		Edge tmp = array.get(l + 1);
		super.move(rm, add, array, l);
		//Addition to the overridden function of Baseline
		//Required to have an accurate probabilities threshold
		this.arrayP.remove(tmp);
	}
}
