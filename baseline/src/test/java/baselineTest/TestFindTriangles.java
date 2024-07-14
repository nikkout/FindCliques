package baselineTest;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import org.testng.annotations.Test;

import lombok.extern.slf4j.Slf4j;
import utils.Edge;
import utils.EdgeLists;
import utils.FindTriangles;
import utils.Graph;
import utils.ReadGraph;
import utils.Triangle;

@Slf4j
public class TestFindTriangles {

	private ArrayList<Triangle> findTrianglesForEdge(double[][] graph, Edge e) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		HashSet<Triangle> trianglesSet = new HashSet<>();
		int v1 = e.getVertex1();
		int v2 = e.getVertex2();
		for (int i = 0; i < graph.length; i++) {
			if (graph[v1][i] > 0 && graph[v2][i] > 0 && v1 != i && v2 != i) {
				Triangle triangle = new Triangle(i, v1, v2, graph[v1][i] + graph[v2][i] + graph[v1][v2]);
				if (!trianglesSet.contains(triangle)) {
					triangles.add(triangle);
					trianglesSet.add(triangle);
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
	public void testFindTriangles100() {
		log.info("Starting test");
		FindTriangles ft = new FindTriangles();
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.toFileW(new File("email-Eu-coreW.txt"));
		for (int y = 0; y < 100; y++) {
			Edge edge = graph.getArray().get(y);
			ArrayList<Triangle> triangles = findTrianglesForEdge(graph.getAdjacencyMatrixW(), edge);
			if (triangles.size() == 0)
				continue;
			EdgeLists e = new EdgeLists(edge, graph.getL().get(edge.getVertex1()), graph.getL().get(edge.getVertex2()));
			ArrayList<Triangle> triangles2 = ft.findTriangles(e, 0);
			Collections.sort(triangles2, new Comparator<Triangle>() {
				@Override
				public int compare(Triangle lhs, Triangle rhs) {
					return lhs.getWeight() > rhs.getWeight() ? -1 : (lhs.getWeight() < rhs.getWeight()) ? 1 : 0;
				}
			});
			assertTrue(triangles.size() + " is not equal to " + triangles2.size(),
					triangles.size() == triangles2.size());
			for (int i = 0; i < triangles.size(); i++) {
				Triangle t1 = triangles.get(i);
				Triangle t2 = triangles2.get(i);
				assertTrue(t1 + " is not equal to " + t2, t1.equals(t2));
			}
		}
		log.info("Ending test");
		log.info("====================================");
	}
}
