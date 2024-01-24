package baseline;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.testng.annotations.Test;

import utils.Graph;
import utils.ReadGraph;
import utils.Triangle;

public class TestBaseline {

	private ArrayList<Triangle> fullEnum(double[][] graph) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		HashSet<Triangle> trianglesSet = new HashSet<>();
		for (int i = 0; i < graph.length; i++) {
			for (int j = i+1; j < graph.length; j++) {
				for (int k = j+1; k < graph.length; k++) {
					if (graph[i][j] > 0 && graph[j][k] > 0 && graph[k][i] > 0 && i != j && i != k && k != j) {
						Triangle triangle = new Triangle(i, j, k, graph[i][j] + graph[j][k] + graph[k][i]);
						if (!trianglesSet.contains(triangle)) {
							triangles.add(triangle);
							trianglesSet.add(triangle);
						}
					}
				}
			}
		}
		Collections.sort(triangles, new Comparator<Triangle>() {
			@Override
			public int compare(Triangle lhs, Triangle rhs) {
				return lhs.getWeight() > rhs.getWeight() ? -1 : (lhs.getWeight() < rhs.getWeight()) ? 1 : 0;
			}
		});
		return triangles;
	}

	@Test
	public void testBaseline100() {
		Baseline bl = new Baseline();
		System.out.println(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		ArrayList<Triangle> triangles = fullEnum(graph.getAdjacencyMatrixW());
		PriorityQueue<Triangle> topKT = bl.findTriangles(graph, 100);
		for (int i = 99; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
	}
	
	@Test
	public void testBaseline1000() {
		Baseline bl = new Baseline();
		System.out.println(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		ArrayList<Triangle> triangles = fullEnum(graph.getAdjacencyMatrixW());
		PriorityQueue<Triangle> topKT = bl.findTriangles(graph, 1000);
		for (int i = 999; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
	}
}
