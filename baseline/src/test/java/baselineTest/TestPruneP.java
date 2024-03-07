package baselineTest;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.testng.annotations.Test;

import baseline.PruneP;
import utils.Graph;
import utils.ReadGraph;
import utils.Time;
import utils.Triangle;

public class TestPruneP extends Common {

	@Test
	public void testBaseline100_001() {
		Time time = new Time();
		System.out.println(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV_WP();
		Graph graph = rg.getGraph();
		graph.toFileWP(new File("email-Eu-coreWP.txt"));
		PruneP pn = new PruneP(graph, 100);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnumP, graph.getAdjacencyMatrixW(),
				graph.getAdjacencyMatrixP(), 0.0001, "Brute Force");
		pn.setProba(0.0001);
		PriorityQueue<Triangle> topKT = time.executeAlgorithm(pn::findTriangles, "Algorithm PruneP");
		for (int i = 99; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
	}

	@Test
	public void testBaseline1000_001() {
		Time time = new Time();
		System.out.println(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV_WP();
		Graph graph = rg.getGraph();
		graph.toFileWP(new File("email-Eu-coreWP.txt"));
		PruneP pn = new PruneP(graph, 1000);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnumP, graph.getAdjacencyMatrixW(),
				graph.getAdjacencyMatrixP(), 0.0001, "Brute Force");
		pn.setProba(0.0001);
		PriorityQueue<Triangle> topKT = time.executeAlgorithm(pn::findTriangles, "Algorithm PruneP");
		for (int i = 999; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
	}
}