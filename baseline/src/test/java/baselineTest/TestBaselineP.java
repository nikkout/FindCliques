package baselineTest;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;

import org.testng.annotations.Test;

import baseline.BaselineP;
import lombok.extern.slf4j.Slf4j;
import utils.Graph;
import utils.ReadGraph;
import utils.Time;
import utils.Triangle;

@Slf4j
public class TestBaselineP extends Common {

	//mvn -Dtest=TestBaselineP#testBaseline100_0001 test
	@Test
	public void testBaseline100_0001() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV_WP();
		Graph graph = rg.getGraph();
		graph.toFileWP(new File("email-Eu-coreWP.txt"));
		BaselineP bl = new BaselineP(graph, 100, 1.5, 0.0001);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnumP, graph.getAdjacencyMatrixW(),
				graph.getAdjacencyMatrixP(), 0.0001, "Brute Force");
		PriorityQueue<Triangle> topKT = time.executeAlgorithm(bl::findTriangles, "Algorithm BaselineP");
		for (int i = 99; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
		log.info("Ending test");
		log.info("====================================");
	}

	@Test
	public void testBaseline1000_0001() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV_WP();
		Graph graph = rg.getGraph();
		graph.toFileWP(new File("email-Eu-coreWP.txt"));
		BaselineP bl = new BaselineP(graph, 1000, 1.5, 0.0001);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnumP, graph.getAdjacencyMatrixW(),
				graph.getAdjacencyMatrixP(), 0.0001, "Brute Force");
		PriorityQueue<Triangle> topKT = time.executeAlgorithm(bl::findTriangles, "Algorithm BaselineP");
		for (int i = 999; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
		log.info("Ending test");
		log.info("====================================");
	}
}
