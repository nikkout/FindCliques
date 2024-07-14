package api;

import utils.Graph;
import utils.ReadGraph;

public class Read {

	public static Graph readGraph(String graph, String format, int arraySize) {
		ReadGraph readGraph = new ReadGraph(graph, arraySize);
		switch (format) {
			case "V V W": {
				readGraph.readVVW();
				return readGraph.getGraph();
			}
			case "V V W P": {
				readGraph.readVVW_P();
				return readGraph.getGraph();
			}
		}
		return null;
	}

}
