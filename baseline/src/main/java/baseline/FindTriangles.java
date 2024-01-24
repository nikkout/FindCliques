package baseline;

import java.util.ArrayList;

import utils.EdgeLists;
import utils.Triangle;
import utils.Vertex;

public class FindTriangles {

	/**
	 * Find triangles for a specific Edge
	 */
	public ArrayList<Triangle> findTriangles(EdgeLists e) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		ArrayList<Vertex> A = e.getA();
		ArrayList<Vertex> B = e.getB();
		A.forEach(vertex -> {
			if (B.contains(vertex)) {
				double weight = e.getEdge().getWeight() + vertex.getWeight()
						+ B.get(B.indexOf(vertex)).getWeight();
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(),
						vertex.getVertex(), weight);
				triangles.add(tr);
			}
		});
		return triangles;
	}
	
	public ArrayList<Triangle> findTrianglesP(EdgeLists e) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		ArrayList<Vertex> A = e.getA();
		ArrayList<Vertex> B = e.getB();
		A.forEach(vertex -> {
			if (B.contains(vertex)) {
				double weight = e.getEdge().getWeight() + vertex.getWeight()
						+ B.get(B.indexOf(vertex)).getWeight();
				double probability = e.getEdge().getProbability() * vertex.getProbability()
				* B.get(B.indexOf(vertex)).getProbability();
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(),
						vertex.getVertex(), weight, probability);
				triangles.add(tr);
			}
		});
		return triangles;
	}
}
