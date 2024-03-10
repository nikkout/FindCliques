package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FindTriangles {

	/**
	 * Find triangles for a specific Edge
	 */
	public ArrayList<Triangle> findTriangles(EdgeLists e, double w) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		ArrayList<Vertex> A = new ArrayList<Vertex>(e.getA());
		ArrayList<Vertex> B = new ArrayList<Vertex>(e.getB());
		if (A.size() < B.size()) {
			A.retainAll(B);
			A.forEach(vertex -> {
				double weight = e.getEdge().getWeight() + vertex.getWeight() + B.get(B.indexOf(vertex)).getWeight();
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), vertex.getVertex(),
						weight);
				if (tr.getWeight() > w)
					triangles.add(tr);
			});
		} else {
			B.retainAll(A);
			B.forEach(vertex -> {
				double weight = e.getEdge().getWeight() + vertex.getWeight() + A.get(A.indexOf(vertex)).getWeight();
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), vertex.getVertex(),
						weight);
				if (tr.getWeight() > w)
					triangles.add(tr);
			});
		}
		return triangles;
	}

	public Triangle[] findTriangles(ArrayList<EdgeLists> edgeLists, double w) {
		HashSet<Triangle> triangles = new HashSet<>();
		for (int y = 0; y < edgeLists.size(); y++) {
			EdgeLists e = edgeLists.get(y);
			ArrayList<Vertex> A = new ArrayList<Vertex>(e.getA());
			ArrayList<Vertex> B = new ArrayList<Vertex>(e.getB());
			if (A.size() == 0 || B.size() == 0)
				continue;
			if (A.size() < B.size()) {
				A.retainAll(B);
				for (int i = 0; i < A.size(); i++) {
					Vertex vertex = A.get(i);
					double weight = e.getEdge().getWeight() + vertex.getWeight() + B.get(B.indexOf(vertex)).getWeight();
					Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), vertex.getVertex(),
							weight);
//					if (tr.getWeight() > w)
					triangles.add(tr);
				}

			} else {
				B.retainAll(A);
				for (int i = 0; i < B.size(); i++) {
					Vertex vertex = B.get(i);
					double weight = e.getEdge().getWeight() + vertex.getWeight() + A.get(A.indexOf(vertex)).getWeight();
					Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), vertex.getVertex(),
							weight);
//					if (tr.getWeight() > w)
					triangles.add(tr);
				}
			}
		}

		Triangle[] triangleArray = new Triangle[triangles.size()];
		triangleArray = triangles.toArray(triangleArray);
		Arrays.sort(triangleArray, new Comparator<Triangle>() {
			@Override
			public int compare(Triangle lhs, Triangle rhs) {
				return lhs.getWeight() > rhs.getWeight() ? -1 : (lhs.getWeight() < rhs.getWeight()) ? 1 : 0;
			}
		});
		return triangleArray;
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
				triangles.add(tr);
			}
		});
		return triangles;
	}
}