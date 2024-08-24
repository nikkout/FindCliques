package baseline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

import utils.Edge;
import utils.EdgeLists;
import utils.FindTriangles;
import utils.Graph;
import utils.Triangle;
import utils.Iterator;

@Slf4j
public class Prune {

	protected Iterator<Edge> array;
	protected Iterator<Edge> arrayP;
	protected HashMap<Integer, HashMap<Integer, Double>> HS;
	protected HashMap<Integer, HashMap<Integer, Double>> L;
	protected HashMap<Integer, HashMap<Integer, Double>> HSP;
	protected HashMap<Integer, HashMap<Integer, Double>> LP;
	protected PriorityQueue<Triangle> T;
	protected HashSet<Triangle> TSet;
	protected Graph graph;
	protected int size;
	protected double ar;
	protected double thresholdP;
	private double r, rp =1;
	
	public Prune(Graph graph, int size, double ar, double thresholdP){
		array = graph.getSortedArrayWeight();
		arrayP = graph.getSortedArrayProbability();
		HS = graph.getHS();
		L = graph.getL();
		HSP = graph.getHSP();
		LP = graph.getLP();
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
		this.thresholdP = thresholdP;
	}

	public PriorityQueue<Triangle> findTriangles() {
		int l = -1;
		int h = -1;
		int lp = -1;
		int hp = -1;
		Triangle currentPeek = null;
		int currentSize = 0;
		int p = 1;
		while (currentPeek == null || currentSize < size || currentPeek.getWeight() < r) {
			if(rp < this.thresholdP){
				log.info("Prunning due to probability too low");
				break;
			} 
			if(lp < l || hp < h){
				if (arrayP.get(lp + 1).getProbability() > Math.pow(arrayP.get(hp + 1).getProbability(), ar) || lp == hp) {
					lp = hsSearch(lp, arrayP);
				} else {
					hp = lSearch(hp, arrayP);
				}
			}
			else{
				if (array.get(l + 1).getWeight() > Math.pow(array.get(h + 1).getWeight(), ar) || l == h) {
					l = hsSearch(l, array);
				} else {
					h = lSearch(h, array);
				}
			}
			this.computeThreshold(h, l, hp, lp >= 0 ? lp : 0, p);
			currentSize = this.T.size();
			currentPeek = this.T.peek();
		}
		log.info(r+"");
		log.info(rp+"");
		return T;
	}
	
	protected int hsSearch(int l, Iterator<Edge> array) {
		this.move(L, HS, LP, HSP, array, l);
		EdgeLists[] e = null;
		Edge edge = array.get(l + 1);
		e = createEdgeListsLow(edge, graph);
		findTriangles(e[0], size);
		findTriangles(e[1], size);
		findTriangles(e[2], size);
		return l + 1;
	}
	
	protected int lSearch(int h, Iterator<Edge> array) {
		Edge edge = array.get(h + 1);
		EdgeLists e = createEdgeListsHigh(edge, graph);
		findTriangles(e, size);
		return h + 1;
	}

	protected void computeThreshold(int h, int l, int hp, int lp, int p) {
		if (h != -1){
			r = Math.pow((double) (array.get(h).getWeight()), p) * Math.pow((double) (array.get(l).getWeight()), p);
		}
		else{
			r = Math.pow((double) (array.get(0).getWeight()), p) + 2 * Math.pow((double) (array.get(l).getWeight()), p);
		}

		if (hp != -1){
			rp = (arrayP.get(hp).getProbability()) * Math.pow((double) (arrayP.get(lp).getProbability()), 2);
		}
		else{
			rp = (arrayP.get(0).getProbability()) * Math.pow((double) (arrayP.get(lp).getProbability()), 2);
		}
	}

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

	protected EdgeLists createEdgeListsHigh(Edge edge, Graph graph) {
		int v1 = edge.getVertex1();
		int v2 = edge.getVertex2();
		HashMap<Integer, HashMap<Integer, Double>> L = graph.getL();
		HashMap<Integer, HashMap<Integer, Double>> LP = graph.getLP();
		EdgeLists e = new EdgeLists(edge, L.get(v1), L.get(v2), LP.get(v1), LP.get(v2));
		return e;
	}

	protected void move(HashMap<Integer, HashMap<Integer, Double>> rm, HashMap<Integer, HashMap<Integer, Double>> add,
	HashMap<Integer, HashMap<Integer, Double>> rmp, HashMap<Integer, HashMap<Integer, Double>> addp,
			Iterator<Edge> array, int l) {
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

	protected void findTriangles(EdgeLists e, int size) {
		if (e == null)
			return;
		FindTriangles ft = new FindTriangles();
		ArrayList<Triangle> newTriangles = ft.findTrianglesP(e, this.T.peek() != null ? this.T.peek().getWeight() : 0);
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
			if (newT.getProbability() < this.thresholdP) {
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
