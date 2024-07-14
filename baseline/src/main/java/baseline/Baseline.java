package baseline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Comparator;

import org.apache.commons.lang3.mutable.MutableDouble;

import utils.Edge;
import utils.EdgeLists;
import utils.FindTriangles;
import utils.Graph;
import utils.Triangle;

public class Baseline {

	protected ArrayList<Edge> array;
	protected ArrayList<Edge> arrayP;
	protected HashMap<Integer, HashMap<Integer, Double>> HS;
	protected HashMap<Integer, HashMap<Integer, Double>> L;
	protected PriorityQueue<Triangle> T;
	protected HashSet<Triangle> TSet;
	protected Graph graph;
	protected int size;
	protected double ar;
	
	public Baseline(Graph graph, int size, double ar){
		array = graph.getArray();
		arrayP = graph.getArrayP();
		HS = graph.getHS();
		L = graph.getL();
		this.ar = ar;
		T = new PriorityQueue<Triangle>(size, new Comparator<Triangle>() {
			@Override
			public int compare(Triangle lhs, Triangle rhs) {
				return lhs.getWeight() > rhs.getWeight() ? 1 : (lhs.getWeight() < rhs.getWeight()) ? -1 : 0;
			}
		});
		TSet = new HashSet<>();
		this.graph = graph;
		this.size = size;
	}

	public PriorityQueue<Triangle> findTriangles() {
		int l = -1;
		int h = -1;
		Triangle currentPeek = null;
		int currentSize = 0;
		double threshold = 0;
		int p = 1;
		while (currentPeek == null || currentSize < size || currentPeek.getWeight() < threshold) {
			if (array.get(l + 1).getWeight() > Math.pow(array.get(h + 1).getWeight(), ar) || l == h) {
				l = hsSearch(l);
			} else {
				h = lSearch(h);
			}
			threshold = this.computeThreshold(h, l, p);
			currentSize = this.T.size();
			currentPeek = this.T.peek();
		}
		return T;

	}
	
	protected int hsSearch(int l) {
		this.move(L, HS, array, l);
		EdgeLists[] e = null;
		Edge edge = array.get(l + 1);
		e = createEdgeListsLow(edge, graph);
		findTriangles(e[0], size);
		findTriangles(e[1], size);
		findTriangles(e[2], size);
		return l + 1;
	}
	
	protected int lSearch(int h) {
		Edge edge = array.get(h + 1);
		EdgeLists e = createEdgeListsHigh(edge, graph);
		findTriangles(e, size);
		return h + 1;
	}

	protected double computeThreshold(int h, int l, int p) {
		double r;
		if (h != -1){
			r = Math.pow((double) (array.get(h).getWeight()), p) + 2 * Math.pow((double) (array.get(l).getWeight()), p);
		}
		else{
			r = Math.pow((double) (array.get(0).getWeight()), p) + 2 * Math.pow((double) (array.get(l).getWeight()), p);
		}
		return r;
	}

	protected EdgeLists[] createEdgeListsLow(Edge edge, Graph graph) {
		EdgeLists[] e = new EdgeLists[3];
		int v1 = edge.getVertex1();
		int v2 = edge.getVertex2();
		e[0] = new EdgeLists(edge, HS.get(v1), HS.get(v2));
		e[1] = new EdgeLists(edge, HS.get(v1), L.get(v2));
		e[2] = new EdgeLists(edge, HS.get(v2), L.get(v1));
		return e;
	}

	protected EdgeLists createEdgeListsHigh(Edge edge, Graph graph) {
		int v1 = edge.getVertex1();
		int v2 = edge.getVertex2();
		EdgeLists e = new EdgeLists(edge, L.get(v1), L.get(v2));
		return e;
	}

	protected void move(HashMap<Integer, HashMap<Integer, Double>> rm, HashMap<Integer, HashMap<Integer, Double>> add,
			ArrayList<Edge> array, int l) {
		Edge tmp = array.get(l + 1);
		int v1 = tmp.getVertex1();
		int v2 = tmp.getVertex2();
		rm.get(v1).remove(v2);
		rm.get(v2).remove(v1);
		if (!add.containsKey(v1))
			add.put(v1, new HashMap<Integer, Double>());
		add.get(v1).put(v2, tmp.getWeight());
		if (!add.containsKey(v2))
			add.put(v2, new HashMap<Integer, Double>());
		add.get(v2).put(v1, tmp.getWeight());
	}

	protected void findTriangles(EdgeLists e, int size) {
		if (e == null)
			return;
		FindTriangles ft = new FindTriangles();
		ArrayList<Triangle> newTriangles = ft.findTriangles(e, this.T.peek() != null ? this.T.peek().getWeight() : 0);
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
