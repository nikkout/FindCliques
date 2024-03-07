package api;

import lombok.extern.slf4j.Slf4j;
import multiThread.MultiThread;
import utils.Graph;
import utils.Time;

@Slf4j
public class Execute {
	
	public static void execute(String algorithm, int threads, int triangles, Graph graph, int edgesToReadPerThread, double ar) {
		switch(algorithm) {
		case "multiThread": {
			executeMultiThread(threads, graph, triangles, edgesToReadPerThread, ar);
		}
		}
	}

	private static void executeMultiThread(int threads, Graph graph, int size, int edgesToReadPerThread, double ar) {
		MultiThread mt = new MultiThread(threads, graph, size, edgesToReadPerThread, ar);
		Time time = new Time();
		String tag = String.format("Algorithm %s, threads: %d, triangles: %d, edges to read: %d, AR: %f", "MultiThread", threads, size, edgesToReadPerThread, ar);
		time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread, tag);
	}
	
}
