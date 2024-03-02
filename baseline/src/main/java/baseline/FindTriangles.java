package baseline;

import java.util.ArrayList;
import java.util.Arrays;

import utils.EdgeLists;
import utils.Triangle;
import utils.Vertex;

public class FindTriangles {

	/**
	 * Find triangles for a specific Edge
	 */
	public ArrayList<Triangle> findTriangles(EdgeLists e) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		double[] A = e.getA();
		double[] B = e.getB();
		for (int i = 0; i < A.length; i++) {
			if (A[i] > 0 && B[i] > 0) {
				double weight = e.getEdge().getWeight() + A[i] + B[i];
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), i,
						weight);
				triangles.add(tr);
			}
		}
		return triangles;
	}

	public ArrayList<Triangle> findTrianglesP(EdgeLists e) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		double[] A = e.getA();
		double[] B = e.getB();
		double[] AP = e.getAP();
		double[] BP = e.getBP();
		for (int i = 0; i < A.length; i++) {
			if (A[i] > 0 && B[i] > 0) {
				double weight = e.getEdge().getWeight() + A[i] + B[i];
				double probability = e.getEdge().getProbability() * AP[i] * BP[i];
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), i,
						weight, probability);
				triangles.add(tr);
			}
		}
		return triangles;
	}
}
