package baseline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Comparator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.mutable.MutableDouble;

import lombok.extern.slf4j.Slf4j;
import utils.Edge;
import utils.EdgeLists;
import utils.Graph;
import utils.ReadGraph;
import utils.Triangle;
import utils.Vertex;

@Slf4j
public class Baseline {

	protected ArrayList<Edge> array;
	protected ArrayList<Edge> arrayP;
	private double[][] HS;
	private double[][] L;
	protected PriorityQueue<Triangle> T;
	protected HashSet<Triangle> TSet;

	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("a", "algorithm", true, "The algorithm that will be used: baseline|");

		DefaultParser parser = new DefaultParser();

		try {
			CommandLine cl = parser.parse(options, args);
			cl.getOptionValue('a');
		} catch (ParseException e) {
			e.printStackTrace();
			new HelpFormatter().printHelp("apache args...", options);
		}

		Baseline base = new Baseline();
		base.execute(args[0]);
	}

	private void execute(String fname) {
		ReadGraph rg = new ReadGraph(fname);
		rg.readVVWP();
		Graph graph = rg.getGraph();

	}

	public PriorityQueue<Triangle> findTriangles(Graph graph, int size) {
		int l = -1;
		int h = -1;
		double ar = 1.5;
		array = graph.getArray();
		arrayP = graph.getArrayP();
		HS = graph.getHS();
		L = graph.getL();
		T = new PriorityQueue<Triangle>(size, new Comparator<Triangle>() {
			@Override
			public int compare(Triangle lhs, Triangle rhs) {
				return lhs.getWeight() > rhs.getWeight() ? 1 : (lhs.getWeight() < rhs.getWeight()) ? -1 : 0;
			}
		});
		TSet = new HashSet<>();
		Triangle currentPeek = null;
		int currentSize = 0;
		double r = 0;
		double threshold = 0;
		MutableDouble thresholdP = new MutableDouble(2);
		int p = 1;
		while (currentPeek == null || currentSize < size || (currentPeek.getProbability() < thresholdP.getValue() && currentPeek.getWeight() < threshold )) {
			log.info("{}", thresholdP.getValue());
			if (array.get(l + 1).getWeight() > Math.pow(array.get(h + 1).getWeight(), ar) || l == h) {
				this.move(L, HS, array, l);
				EdgeLists[] e = null;
				Edge edge = array.get(l + 1);
				int v1 = edge.getVertex1();
				int v2 = edge.getVertex2();
				e = createEdgeListsLow(edge, graph);
				findTriangles(e[0], size);
				findTriangles(e[1], size);
				findTriangles(e[2], size);
				l += 1;
			} else {
				Edge edge = array.get(h + 1);
				int v1 = edge.getVertex1();
				int v2 = edge.getVertex2();
				EdgeLists e = createEdgeListsHigh(edge, graph);
				findTriangles(e, size);
				h += 1;
			}
			threshold = this.computeThreshold(h, l, p, thresholdP);
			currentSize = this.T.size();
			currentPeek = this.T.peek();
		}
		return T;

	}
	
	protected double computeThreshold(int h, int l, int p, MutableDouble thresholdP) {
		double r;
		if (h != -1)
			r = Math.pow((double) (array.get(h).getWeight()), p)
					+ 2 * Math.pow((double) (array.get(l).getWeight()), p);
		else
			r = Math.pow((double) (array.get(0).getWeight()), p)
					+ 2 * Math.pow((double) (array.get(l).getWeight()), p);
		return r;
	}

	protected EdgeLists[] createEdgeListsLow(Edge edge, Graph graph) {
		EdgeLists[] e = new EdgeLists[3];
		int v1 = edge.getVertex1();
		int v2 = edge.getVertex2();
		e[0] = new EdgeLists(edge, HS[v1], HS[v2]);
		e[1] = new EdgeLists(edge, HS[v1], L[v2]);
		e[2] = new EdgeLists(edge, HS[v2], L[v1]);
		return e;
	}
	
	protected EdgeLists createEdgeListsHigh(Edge edge, Graph graph) {
		int v1 = edge.getVertex1();
		int v2 = edge.getVertex2();
		EdgeLists e = new EdgeLists(edge, L[v1], L[v2]);
		return e;
	}

	protected void move(double[][] rm, double[][] add, ArrayList<Edge> array, int l) {
		Edge tmp = array.get(l + 1);
		int v1 = tmp.getVertex1();
		int v2 = tmp.getVertex2();
		double tmpW = rm[v1][v2];
		rm[v1][v2] = 0;
		rm[v2][v1] = 0;
		add[v1][v2] = tmpW;
		add[v2][v1] = tmpW;
	}

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
