package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FindTriangles {

	/**
	 * Find triangles for a specific Edge
	 */
	public ArrayList<Triangle> findTriangles(EdgeLists e) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		ArrayList<Vertex> A = new ArrayList<Vertex>(e.getA());
        ArrayList<Vertex> B = new ArrayList<Vertex>(e.getB());
		if(A.size() < B.size()){
			A.retainAll(B);
			A.forEach(vertex -> {
				double weight = e.getEdge().getWeight() + vertex.getWeight() + B.get(B.indexOf(vertex)).getWeight();
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), vertex.getVertex(),
						weight);
				triangles.add(tr);
			});
		}
		else{
			B.retainAll(A);
			B.forEach(vertex -> {
				double weight = e.getEdge().getWeight() + vertex.getWeight() + A.get(A.indexOf(vertex)).getWeight();
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), vertex.getVertex(),
						weight);
				triangles.add(tr);
			});
		}
		return triangles;
		}

	public ArrayList<Triangle> findTrianglesP(EdgeLists e) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		ArrayList<Vertex> A = e.getA();
		ArrayList<Vertex> B = e.getB();
		A.forEach(vertex -> {
			if (B.contains(vertex)) {
				double weight = e.getEdge().getWeight() + vertex.getWeight() + B.get(B.indexOf(vertex)).getWeight();
				double probability = e.getEdge().getProbability() * vertex.getProbability()
						* B.get(B.indexOf(vertex)).getProbability();
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), vertex.getVertex(),
						weight, probability);
				;
				triangles.add(tr);
			}
		});
		return triangles;
	}
}