package api;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import lombok.extern.slf4j.Slf4j;
import utils.Graph;

@Slf4j
public class Api {

	public static void main(String[] args) {
		String algorithm;
		String graphFile;
		String format;
		int threads;
		int triangles;
		int edges;
		int hEdges;
		double ar;
		int arraySize;
		Options options = new Options();
		options.addOption("a", "algorithm", true, "The algorithm that will be used: multiThread|");
		options.addOption("g", "graph", true, "The graph file containing the graph");
		options.addOption("f", "format", true, "The graph format in the file");
		options.addOption("t", "threads", true, "The number of threads the multiThread algorithm will use");
		options.addOption("k", "triangles", true, "Topk heaviest triangles to compute");
		options.addOption("e", "edges", true, "Number of Edges each thread should read");
		options.addOption("he", "heavyEdges", true, "Number of Heavy Edges each thread should read");
		options.addOption("ar", "AR", true, "AR variable, based on the graph's weights distribution. Default ");
		options.addOption("as", "arraySize", true, "The number of edges the graph has(Optional)");

		DefaultParser parser = new DefaultParser();

		try {
			CommandLine cl = parser.parse(options, args);
			algorithm = cl.getOptionValue('a');
			graphFile = cl.getOptionValue('g');
			format = cl.getOptionValue('f');
			threads = Integer.parseInt(cl.getOptionValue('t'));
			triangles = Integer.parseInt(cl.getOptionValue('k'));
			edges = Integer.parseInt(cl.getOptionValue('e'));
			hEdges = Integer.parseInt(cl.getOptionValue("he"));
			ar = cl.getOptionValue("ar") != null ? Double.parseDouble(cl.getOptionValue("ar")) : 1.5;
			arraySize = cl.getOptionValue("as") != null ? Integer.parseInt(cl.getOptionValue("as")) : 0;
			log.info("Algorithm: " + algorithm);
			log.info("Graph File: " + graphFile);
			log.info("Format: " + format);
			log.info("Threads: " + threads);
			log.info("Triangles: " + triangles);
			log.info("Edges: " + edges);
			log.info("Heavy Edges: " + hEdges);
			log.info("AR: " + ar);
			log.info("arraySize: " + arraySize);

			Graph graph = Read.readGraph(graphFile, format, arraySize);
			Execute.execute(algorithm, threads, triangles, graph, edges, hEdges, ar);

		} catch (ParseException e) {
			e.printStackTrace();
			new HelpFormatter().printHelp("apache args...", options);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			new HelpFormatter().printHelp("apache args...", options);
		}
		System.exit(0);
	}
}
