package baseline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import org.apache.commons.lang3.mutable.MutableDouble;

import lombok.extern.slf4j.Slf4j;
import utils.Edge;
import utils.EdgeLists;
import utils.FindTriangles;
import utils.Graph;
import utils.Triangle;

@Slf4j
public class BaselineP extends Baseline {

	private double proba;
	protected HashMap<Integer, HashMap<Integer, Double>> HSP;
	protected HashMap<Integer, HashMap<Integer, Double>> LP;
	
	public BaselineP(Graph graph, int size, double ar, double proba){
		super(graph, size, ar);
		this.HSP = graph.getHSP();
		this.LP = graph.getLP();
		this.proba = proba;
	}

	@Override
	public PriorityQueue<Triangle> findTriangles() {
		int l = -1;
		int h = -1;
		Triangle currentPeek = null;
		int currentSize = 0;
		double threshold = 0;
		int p = 1;
		while (currentPeek == null || currentSize < size
				|| currentPeek.getProbability() < this.proba || currentPeek.getWeight() < threshold) {
			if (array.get(l + 1).getWeight() > Math.pow(array.get(h + 1).getWeight(), ar) || l == h) {
				l = hsSearch(l);
			} else {
				h = lSearch(h);
			}
			threshold = this.computeThreshold(h, l, p);
			currentSize = this.T.size();
			currentPeek = this.T.peek();
			if(currentPeek != null){
				log.info(currentPeek.getProbability()+"");
			}
		}
		return T;

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
		HashMap<Integer, HashMap<Integer, Double>> HS = graph.getHS();
		HashMap<Integer, HashMap<Integer, Double>> HSP = graph.getHSP();
		HashMap<Integer, HashMap<Integer, Double>> L = graph.getL();
		HashMap<Integer, HashMap<Integer, Double>> LP = graph.getLP();
		e[0] = new EdgeLists(edge, HS.get(v1), HS.get(v2), HSP.get(v1), HSP.get(v2));
		e[1] = new EdgeLists(edge, HS.get(v1), L.get(v2), HSP.get(v1), LP.get(v2));
		e[2] = new EdgeLists(edge, HS.get(v2), L.get(v1), HSP.get(v2), LP.get(v1));
		return e;
	}

	@Override
	protected EdgeLists createEdgeListsHigh(Edge edge, Graph graph) {
		int v1 = edge.getVertex1();
		int v2 = edge.getVertex2();
		HashMap<Integer, HashMap<Integer, Double>> L = graph.getL();
		HashMap<Integer, HashMap<Integer, Double>> LP = graph.getLP();
		EdgeLists e = new EdgeLists(edge, L.get(v1), L.get(v2), LP.get(v1), LP.get(v2));
		return e;
	}

	@Override
	protected int hsSearch(int l) {
		this.move(L, HS, LP, HSP, array, l);
		EdgeLists[] e = null;
		Edge edge = array.get(l + 1);
		e = createEdgeListsLow(edge, graph);
		findTriangles(e[0], size);
		findTriangles(e[1], size);
		findTriangles(e[2], size);
		return l + 1;
	}

	protected void move(HashMap<Integer, HashMap<Integer, Double>> rm, HashMap<Integer, HashMap<Integer, Double>> add,
	HashMap<Integer, HashMap<Integer, Double>> rmp, HashMap<Integer, HashMap<Integer, Double>> addp,
			ArrayList<Edge> array, int l) {
		Edge tmp = array.get(l + 1);
		int v1 = tmp.getVertex1();
		int v2 = tmp.getVertex2();
		rm.get(v1).remove(v2);
		rm.get(v2).remove(v1);
		rmp.get(v1).remove(v2);
		rmp.get(v2).remove(v1);
		if (!add.containsKey(v1))
			add.put(v1, new HashMap<Integer, Double>());
		add.get(v1).put(v2, tmp.getWeight());
		if (!add.containsKey(v2))
			add.put(v2, new HashMap<Integer, Double>());
		add.get(v2).put(v1, tmp.getWeight());

		if (!addp.containsKey(v1))
			addp.put(v1, new HashMap<Integer, Double>());
		addp.get(v1).put(v2, tmp.getProbability());
		if (!addp.containsKey(v2))
			addp.put(v2, new HashMap<Integer, Double>());
		addp.get(v2).put(v1, tmp.getProbability());
	}
}
