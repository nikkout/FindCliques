package multiThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.mutable.MutableDouble;

import utils.Edge;
import utils.EdgeLists;
import utils.FindTriangles;
import utils.Graph;
import utils.Triangle;
import utils.Vertex;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiThread {

	private ExecutorService executorService;
	private int threads;
	protected Set<Triangle> TSet;
	protected PriorityBlockingQueue<Triangle> T;

	protected ArrayList<Edge> array;
	protected ArrayList<Edge> arrayP;
	protected HashMap<Integer, ArrayList<Vertex>> HS;
	protected HashMap<Integer, ArrayList<Vertex>> L;
	protected Graph graph;
	protected int size;
	private double ar;

	private int edgesToReadPerThread;
	private int heavyEdgesToReadPerThread;

	public MultiThread(int threads, Graph graph, int size, int edgesToReadPerThread, int heavyEdgesToReadPerThread, double ar) {
		this.executorService = Executors.newFixedThreadPool(threads);
		this.threads = threads;
		this.edgesToReadPerThread = edgesToReadPerThread;
		this.heavyEdgesToReadPerThread = heavyEdgesToReadPerThread;
		this.ar = ar;

		array = graph.getArray();
		arrayP = graph.getArrayP();
		HS = graph.getHS();
		L = graph.getL();
		this.graph = graph;
		this.size = size;

		this.TSet = Collections.synchronizedSet(new HashSet<Triangle>());
		T = new PriorityBlockingQueue<Triangle>(size, new Comparator<Triangle>() {
			@Override
			public int compare(Triangle lhs, Triangle rhs) {
				return lhs.getWeight() > rhs.getWeight() ? 1 : (lhs.getWeight() < rhs.getWeight()) ? -1 : 0;
			}
		});
		log.debug("L size: " + L.size());
		log.debug("array size: " + array.size());
	}

	public MultiThread(int threads, Graph graph, int size) {
		this(threads, graph, size, 1, 1, 1.5);
	}

	public PriorityBlockingQueue<Triangle> findTrianglesMultiThread() {
		int l = -1;
		int h = -1;
		Triangle currentPeek = null;
		int currentSize = 0;
		double threshold = 0;
		MutableDouble thresholdP = new MutableDouble(2);
		int p = 1;
		while (currentPeek == null || currentSize < size
				|| (currentPeek.getProbability() < thresholdP.getValue() && currentPeek.getWeight() < threshold)) {
			if (l + 1 < this.array.size()
					&& (Math.pow(array.get(l + 1).getWeight(), ar) > array.get(h + 1).getWeight() || l == h)) {
				l = hsSearch(l);
			} else {
				h = lSearch(h, l);
			}
			threshold = this.computeThreshold(h, l, p, thresholdP);
			currentSize = this.T.size();
			currentPeek = this.T.peek();
			log.debug("Threshold: "+ threshold);
			log.debug("Size: "+ currentSize);
			log.debug("Peek: "+ currentPeek);
		}
		return T;
	}

	protected int hsSearch(int l) {
		final AtomicInteger atomicL = new AtomicInteger(l);
		Thread[] threadsArray = new Thread[this.threads];
		final int tmp;
		final int rem;
		if (l + 1 + (this.edgesToReadPerThread * this.threads) >= this.array.size()) {
			tmp = (this.array.size() - l - 1) / this.threads;
			rem = (this.array.size() - l - 1) % this.threads;
		} else {
			tmp = this.edgesToReadPerThread;
			rem = 0;
		}
		for (int i = 0; i < this.threads; i++) {
			ArrayList<EdgeLists> e = new ArrayList<>();
			final int lim = i == 0 ? rem + tmp : tmp;
			for (int y = 0; y < lim; y++) {
				this.move(L, HS, array, atomicL.get()+y);
			}
			for (int y = 0; y < lim; y++) {
//				this.move(L, HS, array, atomicL.get());
				Edge edge = array.get(atomicL.incrementAndGet());
				e.addAll(Arrays.asList(createEdgeListsLow(edge, graph)));
			}
			threadsArray[i] = new Thread(() -> {
				findTriangles(e, size);
		    });
			threadsArray[i].start();
			
		}
		for (int i = 0; i < this.threads; i++) {
			try {
				threadsArray[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return atomicL.get();
	}

	protected int lSearch(int h, int l) {
		final AtomicInteger atomicH = new AtomicInteger(h);
		Thread[] threadsArray = new Thread[this.threads];
		final int tmp;
		final int rem;
		final int limit = l-h > this.heavyEdgesToReadPerThread ? this.heavyEdgesToReadPerThread : l-h;
		if (h + 1 + (limit * this.threads) >= this.array.size()) {
			tmp = (this.array.size() - h - 1) / this.threads;
			rem = (this.array.size() - h - 1) % this.threads;
		} else {
			tmp = limit/this.threads;
			rem = limit%this.threads;
		}
		for (int i = 0; i < this.threads; i++) {
			ArrayList<EdgeLists> e = new ArrayList<>();
			final int lim = i == 0 ? rem + tmp : tmp;
			for (int y = 0; y < lim; y++) {
				Edge edge = array.get(atomicH.incrementAndGet());
				e.addAll(Arrays.asList(createEdgeListsHigh(edge, graph)));
			}
			threadsArray[i] = new Thread(() -> {
				findTriangles(e, size);
		    });
			threadsArray[i].start();
			
		}
		for (int i = 0; i < this.threads; i++) {
			try {
				threadsArray[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return atomicH.get();
	}

	protected double computeThreshold(int h, int l, int p, MutableDouble thresholdP) {
		double r;
		if (h != -1)
			r = Math.pow((double) (array.get(h).getWeight()), p) + 2 * Math.pow((double) (array.get(l).getWeight()), p);
		else
			r = Math.pow((double) (array.get(0).getWeight()), p) + 2 * Math.pow((double) (array.get(l).getWeight()), p);
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

	protected void move(HashMap<Integer, ArrayList<Vertex>> rm, HashMap<Integer, ArrayList<Vertex>> add,
			ArrayList<Edge> array, int l) {
		Edge tmp = array.get(l + 1);
		int v1 = tmp.getVertex1();
		int v2 = tmp.getVertex2();
		rm.get(v1).remove(rm.get(v1).indexOf(new Vertex(v2, 0)));
		rm.get(v2).remove(rm.get(v2).indexOf(new Vertex(v1, 0)));
		if (!add.containsKey(v1))
			add.put(v1, new ArrayList<Vertex>());
		add.get(v1).add(new Vertex(v2, tmp.getWeight()));
		if (!add.containsKey(v2))
			add.put(v2, new ArrayList<Vertex>());
		add.get(v2).add(new Vertex(v1, tmp.getWeight()));
	}

	protected void findTriangles(ArrayList<EdgeLists> e, int size) {
		if (e == null)
			return;
		FindTriangles ft = new FindTriangles();
		Triangle[] newTriangles = ft.findTriangles(e, this.T.size() >= size ? this.T.peek().getWeight() : 0);
		int i = 0;
		Triangle peek = T.peek();
		while (newTriangles.length > i) {
			Triangle newT = newTriangles[i];
			if ((T.size() >= size && newT.getWeight() < peek.getWeight()))
				break;
			if (T.size() >= size && TSet.add(newT)) {
				TSet.remove(T.poll());
				T.put(newT);
				peek = T.peek();
			} else if (TSet.add(newT)) {
				T.put(newT);
			}
			i++;
		}
	}
}
