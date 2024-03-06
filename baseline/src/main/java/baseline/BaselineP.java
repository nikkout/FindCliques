package baseline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import utils.Edge;
import utils.EdgeLists;
import utils.Graph;
import utils.Triangle;
import utils.Vertex;

public class BaselineP extends Baseline {

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
			if (newT == null || (T.size() >= size && newT.getWeight() < peek.getWeight()))
				break;
			if (newT.getProbability() < this.proba) {
				i++;
				continue;
			}
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

	@Override
	protected EdgeLists[] createEdgeListsLow(Edge edge, Graph graph) {
		EdgeLists[] e = new EdgeLists[3];
		int v1 = edge.getVertex1();
		int v2 = edge.getVertex2();
		HashMap<Integer, ArrayList<Vertex>> HS = graph.getHS();
		HashMap<Integer, ArrayList<Vertex>> HSP = graph.getHSP();
		HashMap<Integer, ArrayList<Vertex>> L = graph.getL();
		HashMap<Integer, ArrayList<Vertex>> LP = graph.getLP();
		e[0] = new EdgeLists(edge, HS.get(v1), HS.get(v2));
		e[1] = new EdgeLists(edge, HS.get(v1), L.get(v2));
		e[2] = new EdgeLists(edge, HS.get(v2), L.get(v1));
		return e;
	}

	@Override
	protected EdgeLists createEdgeListsHigh(Edge edge, Graph graph) {
		int v1 = edge.getVertex1();
		int v2 = edge.getVertex2();
		HashMap<Integer, ArrayList<Vertex>> L = graph.getL();
		HashMap<Integer, ArrayList<Vertex>> LP = graph.getLP();
		EdgeLists e = new EdgeLists(edge, L.get(v1), L.get(v2));
		return e;
	}
}
