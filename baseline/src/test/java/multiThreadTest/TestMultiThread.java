package multiThreadTest;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import org.testng.annotations.Test;

import baselineTest.Common;
import multiThread.MultiThread;
import utils.Graph;
import utils.ReadGraph;
import utils.Time;
import utils.Triangle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMultiThread extends Common {
	
	private final int TEST100 = 100;
	private final int TEST100_LIMIT = 99;
	private final int TEST1000 = 1000;
	private final int TEST1000_LIMIT = 999;
	private final int TESTALL = 105461;
	private final int TESTALL_LIMIT = 105460;

	@Test
	public void testBaseline100() {
		Time time = new Time();
		System.out.println(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		MultiThread mt = new MultiThread(4, graph, TEST100);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		log.info(topKT.size() + "");
		while (topKT.size() > TEST100)
			topKT.poll();
		for (int i = TEST100_LIMIT; i >= 0; i--) {
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
		MultiThread mt = new MultiThread(4, graph, TEST1000);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		while (topKT.size() > TEST1000)
			topKT.poll();
		for (int i = TEST1000_LIMIT; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
	}
	
	@Test
	public void testBaselineAll() {
		Time time = new Time();
		System.out.println(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		MultiThread mt = new MultiThread(4, graph, TESTALL);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		ArrayList<Triangle> trianglesAlgorithm = new ArrayList<>(topKT.size());
		for (int i = TESTALL_LIMIT; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
	}
}
