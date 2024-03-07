package multiThreadTest;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
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
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		MultiThread mt = new MultiThread(4, graph, TEST100);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		while (topKT.size() > TEST100)
			topKT.poll();
		try {
			for (int i = TEST100_LIMIT; i >= 0; i--) {
				Triangle peak = topKT.poll();
				assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
			}
		}catch(AssertionError e) {
			ArrayList<Triangle> trianglesKT = new ArrayList<Triangle>(triangles.size());
			topKT.drainTo(trianglesKT);
			ArrayList<Triangle> sub = (ArrayList<Triangle>) CollectionUtils.subtract(trianglesKT, triangles);
			log.info("sun size: "+sub.size());
			sub.forEach(triangle -> {
				log.error(""+triangle);
			});
			throw(e);
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
		MultiThread mt = new MultiThread(4, graph, TEST1000);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		while (topKT.size() > TEST1000)
			topKT.poll();
		try {
			for (int i = TEST1000_LIMIT; i >= 0; i--) {
				Triangle peak = topKT.poll();
				assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
			}
		}catch(AssertionError e) {
			ArrayList<Triangle> trianglesKT = new ArrayList<Triangle>(triangles.size());
			topKT.drainTo(trianglesKT);
			ArrayList<Triangle> sub = (ArrayList<Triangle>) CollectionUtils.subtract(trianglesKT, triangles);
			log.info("sub size: "+sub.size());
			sub.forEach(triangle -> {
				log.error(""+triangle);
				log.error("Cardinality: "+IterableUtils.frequency(trianglesKT, triangle));
				log.error("Cardinality: "+IterableUtils.frequency(triangles, triangle));
			});
			throw(e);
		}
		log.info("Ending test");
		log.info("====================================");
	}
	
	@Test
	public void testBaselineAll() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		MultiThread mt = new MultiThread(4, graph, TESTALL);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		try {
			for (int i = TESTALL_LIMIT; i >= 0; i--) {
				Triangle peak = topKT.poll();
				assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
			}
		}catch(AssertionError e) {
			ArrayList<Triangle> trianglesKT = new ArrayList<Triangle>(triangles.size());
			topKT.drainTo(trianglesKT);
			ArrayList<Triangle> sub = (ArrayList<Triangle>) CollectionUtils.subtract(trianglesKT, triangles);
			log.info("sub size: "+sub.size());
			sub.forEach(triangle -> {
				log.error(""+triangle);
				log.error("Cardinality: "+IterableUtils.frequency(trianglesKT, triangle));
				log.error("Cardinality: "+IterableUtils.frequency(triangles, triangle));
			});
			throw(e);
		}
		log.info("Ending test");
		log.info("====================================");
	}
	
	@Test
	public void testBaseline100_Read100() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		MultiThread mt = new MultiThread(4, graph, TEST100, 100, 1.5);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		ArrayList<Triangle> topKL = new ArrayList<>(topKT.size());
		topKT.drainTo(topKL);
		Collections.sort(topKL, new Comparator<Triangle>() {
			@Override
			public int compare(Triangle lhs, Triangle rhs) {
				return lhs.getWeight() > rhs.getWeight() ? -1 : (lhs.getWeight() < rhs.getWeight()) ? 1 : 0;
			}
		});
		while (topKL.size() > TEST100) {
			log.info(topKL.get(topKL.size()-1)+"");
			topKL.remove(topKL.size()-1);
		}
		try {
			for (int i = 0; i <= TEST100_LIMIT; i++) {
				Triangle peak = topKL.get(i);
				assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
			}
		}catch(AssertionError e) {
			ArrayList<Triangle> sub = (ArrayList<Triangle>) CollectionUtils.subtract(topKL, triangles);
			log.info("sub size: "+sub.size());
			sub.forEach(triangle -> {
				log.error(""+triangle);
				log.error("Cardinality: "+IterableUtils.frequency(topKL, triangle));
				log.error("Cardinality: "+IterableUtils.frequency(triangles, triangle));
			});
			throw(e);
		}
		log.info("Ending test");
		log.info("====================================");
	}
	
	@Test
	public void testBaseline100_Read1000() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		MultiThread mt = new MultiThread(4, graph, TEST100, 1000, 1.5);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		ArrayList<Triangle> topKL = new ArrayList<>(topKT.size());
		topKT.drainTo(topKL);
		Collections.sort(topKL, new Comparator<Triangle>() {
			@Override
			public int compare(Triangle lhs, Triangle rhs) {
				return lhs.getWeight() > rhs.getWeight() ? -1 : (lhs.getWeight() < rhs.getWeight()) ? 1 : 0;
			}
		});
		while (topKL.size() > TEST100) {
			log.info(topKL.get(topKL.size()-1)+"");
			topKL.remove(topKL.size()-1);
		}
		try {
			for (int i = 0; i <= TEST100_LIMIT; i++) {
				Triangle peak = topKL.get(i);
				assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
			}
		}catch(AssertionError e) {
			ArrayList<Triangle> sub = (ArrayList<Triangle>) CollectionUtils.subtract(topKL, triangles);
			log.info("sub size: "+sub.size());
			sub.forEach(triangle -> {
				log.error(""+triangle);
				log.error("Cardinality: "+IterableUtils.frequency(topKL, triangle));
				log.error("Cardinality: "+IterableUtils.frequency(triangles, triangle));
			});
			throw(e);
		}
		log.info("Ending test");
		log.info("====================================");
	}
	
	@Test
	public void testBaseline1000_Read100() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		MultiThread mt = new MultiThread(4, graph, TEST1000, 100, 1.5);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		ArrayList<Triangle> topKL = new ArrayList<>(topKT.size());
		topKT.drainTo(topKL);
		Collections.sort(topKL, new Comparator<Triangle>() {
			@Override
			public int compare(Triangle lhs, Triangle rhs) {
				return lhs.getWeight() > rhs.getWeight() ? -1 : (lhs.getWeight() < rhs.getWeight()) ? 1 : 0;
			}
		});
		while (topKL.size() > TEST1000) {
			log.info(topKL.get(topKL.size()-1)+"");
			topKL.remove(topKL.size()-1);
		}
		try {
			for (int i = 0; i <= TEST1000_LIMIT; i++) {
				Triangle peak = topKL.get(i);
				assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
			}
		}catch(AssertionError e) {
			ArrayList<Triangle> sub = (ArrayList<Triangle>) CollectionUtils.subtract(topKL, triangles);
			log.info("sub size: "+sub.size());
			sub.forEach(triangle -> {
				log.error(""+triangle);
				log.error("Cardinality: "+IterableUtils.frequency(topKL, triangle));
				log.error("Cardinality: "+IterableUtils.frequency(triangles, triangle));
			});
			throw(e);
		}
		log.info("Ending test");
		log.info("====================================");
	}
	
	@Test
	public void testBaseline1000_Read1000() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		MultiThread mt = new MultiThread(4, graph, TEST1000, 1000, 1.5);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		ArrayList<Triangle> topKL = new ArrayList<>(topKT.size());
		topKT.drainTo(topKL);
		Collections.sort(topKL, new Comparator<Triangle>() {
			@Override
			public int compare(Triangle lhs, Triangle rhs) {
				return lhs.getWeight() > rhs.getWeight() ? -1 : (lhs.getWeight() < rhs.getWeight()) ? 1 : 0;
			}
		});
		while (topKL.size() > TEST1000) {
			log.info(topKL.get(topKL.size()-1)+"");
			topKL.remove(topKL.size()-1);
		}
		log.info("size: "+topKL.size());
		try {
			for (int i = 0; i <= TEST100_LIMIT; i++) {
				Triangle peak = topKL.get(i);
				assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
			}
		}catch(AssertionError e) {
			ArrayList<Triangle> sub = (ArrayList<Triangle>) CollectionUtils.subtract(topKL, triangles);
			log.info("sub size: "+sub.size());
			sub.forEach(triangle -> {
				log.error(""+triangle);
				log.error("Cardinality: "+IterableUtils.frequency(topKL, triangle));
				log.error("Cardinality: "+IterableUtils.frequency(triangles, triangle));
			});
			throw(e);
		}
		log.info("Ending test");
		log.info("====================================");
		
	}
	
	@Test
	public void testBaselineAll_read100() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		MultiThread mt = new MultiThread(4, graph, TESTALL, 100, 1.5);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		try {
			for (int i = TESTALL_LIMIT; i >= 0; i--) {
				Triangle peak = topKT.poll();
				assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
			}
		}catch(AssertionError e) {
			ArrayList<Triangle> trianglesKT = new ArrayList<Triangle>(triangles.size());
			topKT.drainTo(trianglesKT);
			ArrayList<Triangle> sub = (ArrayList<Triangle>) CollectionUtils.subtract(trianglesKT, triangles);
			log.info("sub size: "+sub.size());
			sub.forEach(triangle -> {
				log.error(""+triangle);
				log.error("Cardinality: "+IterableUtils.frequency(trianglesKT, triangle));
				log.error("Cardinality: "+IterableUtils.frequency(triangles, triangle));
			});
			throw(e);
		}
		log.info("Ending test");
		log.info("====================================");
	}
	
	@Test
	public void testBaselineAll_read1000() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		MultiThread mt = new MultiThread(4, graph, TESTALL, 1000, 1.5);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		try {
			for (int i = TESTALL_LIMIT; i >= 0; i--) {
				Triangle peak = topKT.poll();
				assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
			}
		}catch(AssertionError e) {
			ArrayList<Triangle> trianglesKT = new ArrayList<Triangle>(triangles.size());
			topKT.drainTo(trianglesKT);
			ArrayList<Triangle> sub = (ArrayList<Triangle>) CollectionUtils.subtract(trianglesKT, triangles);
			log.info("sub size: "+sub.size());
			sub.forEach(triangle -> {
				log.error(""+triangle);
				log.error("Cardinality: "+IterableUtils.frequency(trianglesKT, triangle));
				log.error("Cardinality: "+IterableUtils.frequency(triangles, triangle));
			});
			throw(e);
		}
		log.info("Ending test");
		log.info("====================================");
	}
	
	//    mvn -Dtest=TestMultiThread#testBaselineAll_read1000_cpu6 test
	@Test
	public void testBaselineAll_read1000_cpu6() {
		log.info("Starting test");
		Time time = new Time();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		MultiThread mt = new MultiThread(6, graph, TESTALL, 100, 1.5);
		ArrayList<Triangle> triangles = time.executeBruteForce(this::fullEnum, graph.getAdjacencyMatrixW(),
				"Brute Force");
		PriorityBlockingQueue<Triangle> topKT = time.executeAlgorithmMultiThread(mt::findTrianglesMultiThread,
				"Multi Thread Algorithm");
		try {
			for (int i = TESTALL_LIMIT; i >= 0; i--) {
				Triangle peak = topKT.poll();
				assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
			}
		}catch(AssertionError e) {
			ArrayList<Triangle> trianglesKT = new ArrayList<Triangle>(triangles.size());
			topKT.drainTo(trianglesKT);
			ArrayList<Triangle> sub = (ArrayList<Triangle>) CollectionUtils.subtract(trianglesKT, triangles);
			log.info("sub size: "+sub.size());
			sub.forEach(triangle -> {
				log.error(""+triangle);
				log.error("Cardinality: "+IterableUtils.frequency(trianglesKT, triangle));
				log.error("Cardinality: "+IterableUtils.frequency(triangles, triangle));
			});
			throw(e);
		}
		log.info("Ending test");
		log.info("====================================");
	}
}
