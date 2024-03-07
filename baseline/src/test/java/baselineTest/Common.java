package baselineTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import utils.Triangle;

public class Common {
	protected ArrayList<Triangle> fullEnum(double[][] graph) {
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
	
	protected ArrayList<Triangle> fullEnumP(double[][] graphW, double[][] graphP, double probability) {
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
}
