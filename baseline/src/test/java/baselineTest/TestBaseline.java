package baselineTest;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;

import org.testng.annotations.Test;

import baseline.Baseline;
import lombok.extern.slf4j.Slf4j;
import utils.Graph;
import utils.ReadGraph;
import utils.Time;
import utils.Triangle;

@Slf4j
public class TestBaseline extends Common {

	@Test
	public void testBaseline100() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		Baseline bl = new Baseline(graph, 100, 1.5);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityQueue<Triangle> topKT = time.executeAlgorithm(bl::findTriangles, "Algorithm");
		for (int i = 99; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
		log.info("Ending test");
		log.info("====================================");
	}

	@Test
	public void testBaseline1000() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		Baseline bl = new Baseline(graph, 1000, 1.5);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityQueue<Triangle> topKT = time.executeAlgorithm(bl::findTriangles, "Algorithm");
		for (int i = 999; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
		log.info("Ending test");
		log.info("====================================");
	}
}
