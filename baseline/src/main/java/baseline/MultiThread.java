package baseline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.mutable.MutableDouble;

import utils.Edge;
import utils.EdgeLists;
import utils.FindTriangles;
import utils.Graph;
import utils.Triangle;
import utils.Vertex;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiThread extends Baseline {

	private ExecutorService executorService;
	private Future<Void>[] futures;
	private int threads;
	protected Set<Triangle> TSet;
	protected PriorityBlockingQueue<Triangle> T;
	
	protected ArrayList<Edge> array;
	protected ArrayList<Edge> arrayP;
	protected HashMap<Integer, ArrayList<Vertex>> HS;
	protected HashMap<Integer, ArrayList<Vertex>> L;
	protected Graph graph;
	protected int size;

	MultiThread(int threads, Graph graph, int size) {
		super(graph, size);
		this.executorService = Executors.newFixedThreadPool(threads);
		this.futures = new Future[threads];
		this.threads = threads;
		
		this.TSet = Collections.synchronizedSet(new HashSet<Triangle>());
		T = new PriorityBlockingQueue<Triangle>(size, new Comparator<Triangle>() {
			@Override
			public int compare(Triangle lhs, Triangle rhs) {
				return lhs.getWeight() > rhs.getWeight() ? 1 : (lhs.getWeight() < rhs.getWeight()) ? -1 : 0;
			}
		});
	}
	
	public PriorityBlockingQueue<Triangle> findTrianglesMultiThread() {
		int l = -1;
		int h = -1;
		double ar = 1.5;
		Triangle currentPeek = null;
		int currentSize = 0;
		double r = 0;
		double threshold = 0;
		MutableDouble thresholdP = new MutableDouble(2);
		int p = 1;
		while (currentPeek == null || currentSize < size
				|| (currentPeek.getProbability() < thresholdP.getValue() && currentPeek.getWeight() < threshold)) {
			if (array.get(l + 1).getWeight() > Math.pow(array.get(h + 1).getWeight(), ar) || l == h) {
				l = hsSearch(l);
			} else {
				h = lSearch(h);
			}
			threshold = this.computeThreshold(h, l, p, thresholdP);
			currentSize = this.T.size();
			currentPeek = this.T.peek();
		}
		return T;
	}

	@Override
	protected int hsSearch(int l) {
		final AtomicInteger atomicL = new AtomicInteger(l);
		final ArrayList<Callable<Object>> callableArray = new ArrayList<>();
		for (int i = 0; i < this.threads; i++) {
			this.move(L, HS, array, atomicL.get());
			Edge edge = array.get(atomicL.incrementAndGet());
			int v1 = edge.getVertex1();
			int v2 = edge.getVertex2();
			final EdgeLists[] e = createEdgeListsLow(edge, graph);
			callableArray.add(() -> {
				findTriangles(e[0], size);
				findTriangles(e[1], size);
				findTriangles(e[2], size);
				return null;
			});
		}
		try {
			this.executorService.invokeAll(callableArray);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return atomicL.get();
	}

	@Override
	protected int lSearch(int h) {
		final AtomicInteger atomicH = new AtomicInteger(h);
		final ArrayList<Callable<Object>> callableArray = new ArrayList<>();
		for (int i = 0; i < this.threads; i++) {
			Edge edge = array.get(atomicH.incrementAndGet());
			int v1 = edge.getVertex1();
			int v2 = edge.getVertex2();
			final EdgeLists e = createEdgeListsHigh(edge, graph);
			callableArray.add(() -> {
				findTriangles(e, size);
				return null;
			});
		}
		try {
			this.executorService.invokeAll(callableArray);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return atomicH.get();
	}

	@Override
	protected void findTriangles(EdgeLists e, int size) {
		if (e == null)
			return;
		FindTriangles ft = new FindTriangles();
		ArrayList<Triangle> newTriangles = ft.findTriangles(e);
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
