package legacyMultiThreadTest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import baselineTest.Common;
import legacy.Main;
import multiThread.MultiThread;
import utils.Graph;
import utils.ReadGraph;
import utils.Triangle;

public class MultiThreadTest extends Common {
	
	private boolean checkTriangles(legacy.Triangle t1, Triangle t2){
		return (Math.abs(t1.get_weight() - t2.getWeight()) < 0.00001 && t1.vertex1 == t2.getVertex1() && t1.vertex2 == t2.getVertex2() && t1.vertex3 == t2.getVertex3());
	}

	@Test
	public void testBaseline100() {
		String[] arguments = { "6000", "4", "100", "", "3", "1.05", "1300" };

		Main main = new Main(arguments);
		main.read("email-Eu-coreW.txt");

		ReadGraph rg = new ReadGraph("email-Eu-coreW2.txt");
		rg.readVVW();
		Graph graph = rg.getGraph();
		PriorityBlockingQueue<legacy.Triangle> topKT = main.T;
		MultiThread mt = new MultiThread(4, graph, 100, 100, 50, 1.5);
		
		PriorityBlockingQueue<Triangle> triangles = mt.findTrianglesMultiThread();
		while (topKT.size() > 100)
			topKT.poll();
		while (triangles.size() > 100)
			triangles.poll();
		for (int i = 99; i >= 0; i--) {
			legacy.Triangle peak = topKT.poll();
			Triangle triangle = triangles.poll();
			assertTrue(peak + " is not equal to " + triangle, checkTriangles(peak, triangle));
		}
	}
	
	@Test
	public void testBaseline1000() {
		String[] arguments = { "6000", "4", "1000", "", "3", "1.05", "1300" };

		Main main = new Main(arguments);
		main.read("email-Eu-coreW.txt");

		ReadGraph rg = new ReadGraph("email-Eu-coreW2.txt");
		rg.readVVW();
		Graph graph = rg.getGraph();
		PriorityBlockingQueue<legacy.Triangle> topKT = main.T;
		MultiThread mt = new MultiThread(4, graph, 1000, 100, 50, 1.5);
		
		PriorityBlockingQueue<Triangle> triangles = mt.findTrianglesMultiThread();
		while (topKT.size() > 1000)
			topKT.poll();
		while (triangles.size() > 1000)
			triangles.poll();
		for (int i = 999; i >= 0; i--) {
			legacy.Triangle peak = topKT.poll();
			Triangle triangle = triangles.poll();
			assertTrue(peak + " is not equal to " + triangle, checkTriangles(peak, triangle));
		}
	}
	
	@Test
	public void testBaselineAll() {
		String[] arguments = { "6000", "4", "105460", "", "3", "1.05", "1300" };

		Main main = new Main(arguments);
		main.read("email-Eu-coreW.txt");

		ReadGraph rg = new ReadGraph("email-Eu-coreW2.txt");
		rg.readVVW();
		Graph graph = rg.getGraph();
		PriorityBlockingQueue<legacy.Triangle> topKT = main.T;
		MultiThread mt = new MultiThread(4, graph, 105461, 100, 50, 1.5);
		
		PriorityBlockingQueue<Triangle> triangles = mt.findTrianglesMultiThread();
		for (int i = 105460; i >= 0; i--) {
			legacy.Triangle peak = topKT.poll();
			Triangle triangle = triangles.poll();
			assertTrue(peak + " is not equal to " + triangle, checkTriangles(peak, triangle));
		}
	}

}
