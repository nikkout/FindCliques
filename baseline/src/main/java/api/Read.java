package api;

import utils.Graph;
import utils.ReadGraph;

public class Read {

	public static Graph readGraph(String graph, String format) {
		ReadGraph readGraph = new ReadGraph(graph);
		switch (format) {
		case "V V W": {
			readGraph.readVVW();
			return readGraph.getGraph();
		}
		}
		return null;
	}

}
