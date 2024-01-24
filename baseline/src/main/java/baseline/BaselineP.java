package baseline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import utils.EdgeLists;
import utils.Triangle;

public class BaselineP extends Baseline{
	
	private double proba;
	
	public void setProba(double proba) {
		this.proba = proba;
	}

	@Override
	protected void findTriangles(EdgeLists e, int size) {
		if (e == null)
			return;
		FindTriangles ft = new FindTriangles();
		ArrayList<Triangle> newTriangles = ft.findTrianglesP(e);
		Collections.sort(newTriangles, new Comparator<Triangle>() {
			@Override
			public int compare(Triangle lhs, Triangle rhs) {
				return lhs.getWeight() > rhs.getWeight() ? -1 : (lhs.getWeight() < rhs.getWeight()) ? 1 : 0;
			}
		});
		int i = 0;
		while (newTriangles.size() > i) {
			Triangle peek = T.peek();
			Triangle newT = newTriangles.get(i);
			if(newT != null && newT.getProbability() < this.proba) {
				i++;
				continue;
			}
			if (newT == null || (T.size() >= size && newT.getWeight() < peek.getWeight()))
				break;
			if (!TSet.contains(newT) && T.size() >= size) {
				TSet.remove(T.poll());
				TSet.add(newT);
				T.add(newT);
			} else if (!TSet.contains(newT)) {
				TSet.add(newT);
				T.add(newT);
			}
			i++;
		}
	}
}
