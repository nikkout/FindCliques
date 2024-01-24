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

public class TestBaselineP {

	private ArrayList<Triangle> fullEnum(double[][] graphW, double[][] graphP, double probability) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		HashSet<Triangle> trianglesSet = new HashSet<>();
		for (int i = 0; i < graphW.length; i++) {
			for (int j = i + 1; j < graphW.length; j++) {
				for (int k = j + 1; k < graphW.length; k++) {
					if (graphW[i][j] > 0 && graphW[j][k] > 0 && graphW[k][i] > 0 && i != j && i != k && k != j
							&& graphP[i][j] * graphP[j][k] * graphP[k][i] >= probability) {
						Triangle triangle = new Triangle(i, j, k, graphW[i][j] + graphW[j][k] + graphW[k][i], graphP[i][j] * graphP[j][k] * graphP[k][i]);
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
	public void testBaseline100_001() {
		BaselineP bl = new BaselineP();
		System.out.println(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV_WP();
		Graph graph = rg.getGraph();
		graph.toFileWP(new File("email-Eu-coreWP.txt"));
		ArrayList<Triangle> triangles = fullEnum(graph.getAdjacencyMatrixW(), graph.getAdjacencyMatrixP(), 0.0001);
		bl.setProba(0.0001);
		PriorityQueue<Triangle> topKT = bl.findTriangles(graph, 100);
		for (int i = 99; i >= 0; i--) {
			Triangle peak = topKT.poll();
			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
		}
	}

//	@Test
//	public void testBaseline1000() {
//		Baseline bl = new Baseline();
//		System.out.println(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
//		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
//		rg.readVV();
//		Graph graph = rg.getGraph();
//		graph.toFileW(new File("email-Eu-coreW.txt"));
//		ArrayList<Triangle> triangles = fullEnum(graph.getAdjacencyMatrixW());
//		PriorityQueue<Triangle> topKT = bl.findTriangles(graph, 1000);
//		for (int i = 999; i >= 0; i--) {
//			Triangle peak = topKT.poll();
//			assertTrue(peak + " is not equal to " + triangles.get(i), peak.equals(triangles.get(i)));
//		}
//	}
}
