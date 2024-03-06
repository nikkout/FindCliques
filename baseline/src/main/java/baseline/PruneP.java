package baseline;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.mutable.MutableDouble; 



import utils.Edge;
import utils.Vertex;

public class PruneP extends BaselineP{
	
	@Override
	protected double computeThreshold(int h, int l, int p, MutableDouble thresholdP) {
		double r;
		if (h != -1)
			r = Math.pow((double) (array.get(h).getWeight()), p)
					+ 2 * Math.pow((double) (array.get(l).getWeight()), p);
		else
			r = Math.pow((double) (array.get(0).getWeight()), p)
					+ 2 * Math.pow((double) (array.get(l).getWeight()), p);
		thresholdP.setValue(Math.pow(arrayP.get(0).getProbability(), 3));
		return r;
	}
	
	@Override
	protected void move(HashMap<Integer, ArrayList<Vertex>> rm, HashMap<Integer, ArrayList<Vertex>> add,
			ArrayList<Edge> array, int l) {
		Edge tmp = array.get(l + 1);
		super.move(rm, add, array, l);
		//Addition to the overridden function of Baseline
		//Required to have an accurate probabilities threshold
		this.arrayP.remove(tmp);
	}
}
