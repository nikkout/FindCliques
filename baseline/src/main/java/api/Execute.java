package api;

import baseline.Baseline;
import baseline.BaselineP;
import baseline.PruneP;
import multiThread.MultiThread;
import utils.Graph;
import utils.Time;

public class Execute {

	public static void execute(String algorithm, int threads, int triangles, Graph graph, int edgesToReadPerThread,
			int heavyEdgesToReadPerThread, double ar, double proba) {
		switch (algorithm) {
			case "multiThread": {
				executeMultiThread(threads, graph, triangles, edgesToReadPerThread, heavyEdgesToReadPerThread, ar);
				break;
			}
			case "Baseline": {
				executeBaseline(threads, graph, triangles, ar);
				break;
			}
			case "BaselineP": {
				executeBaselineP(threads, graph, triangles, ar, proba);
				break;
			}
			case "PruneP": {
				executePruneP(threads, graph, triangles, ar, proba);
				break;
			}
		}
	}

	private static void executeMultiThread(int threads, Graph graph, int size, int edgesToReadPerThread,
			int heavyEdgesToReadPerThread, double ar) {
		MultiThread mt = new MultiThread(threads, graph, size, edgesToReadPerThread, heavyEdgesToReadPerThread, ar);
		Time time = new Time();
		String tag = String.format(
				"Algorithm %s, threads: %d, triangles: %d, edges to read: %d, heavy edge to read: %d, AR: %f",
				"MultiThread", threads, size, edgesToReadPerThread, heavyEdgesToReadPerThread, ar);
		time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread, tag);
	}

	private static void executeBaseline(int threads, Graph graph, int size, double ar){
		Baseline bl = new Baseline(graph, size, ar);
		Time time = new Time();
		String tag = String.format(
				"Algorithm %s, threads: %d, triangles: %d, AR: %f",
				"Baseline", threads, size, ar);
		time.executeAlgorithm(bl::findTriangles, tag);
	}

	private static void executeBaselineP(int threads, Graph graph, int size, double ar, double proba){
		BaselineP bl = new BaselineP(graph, size, ar, proba);
		Time time = new Time();
		String tag = String.format(
				"Algorithm %s, threads: %d, triangles: %d, AR: %f",
				"Baseline", threads, size, ar);
		time.executeAlgorithm(bl::findTriangles, tag);
	}

	private static void executePruneP(int threads, Graph graph, int size, double ar, double proba){
		PruneP bl = new PruneP(graph, size, ar, proba);
		Time time = new Time();
		String tag = String.format(
				"Algorithm %s, threads: %d, triangles: %d, AR: %f",
				"Baseline", threads, size, ar);
		time.executeAlgorithm(bl::findTriangles, tag);
	}

}
