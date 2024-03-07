package baselineTest;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.testng.annotations.Test;

import baseline.Baseline;
import utils.Graph;
import utils.ReadGraph;
import utils.Time;
import utils.Triangle;

public class TestBaseline extends Common {

	@Test
	public void testBaseline100() {
		Time time = new Time();
		System.out.println(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		Baseline bl = new Baseline(graph, 100);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityQueue<Triangle> topKT = time.executeAlgorithm(bl::findTriangles, "Algorithm");
		for (int i = 99; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
	}

	@Test
	public void testBaseline1000() {
		Time time = new Time();
		System.out.println(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		Baseline bl = new Baseline(graph, 1000);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityQueue<Triangle> topKT = time.executeAlgorithm(bl::findTriangles, "Algorithm");
		for (int i = 999; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
	}
}
