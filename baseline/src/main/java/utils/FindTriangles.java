package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FindTriangles {

	/**
	 * Find triangles for a specific Edge
	 */
	public ArrayList<Triangle> findTriangles(EdgeLists e, double w) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		HashMap<Integer, Double> A = new HashMap<Integer, Double>(e.getA());
		HashMap<Integer, Double> B = new HashMap<Integer, Double>(e.getB());
		if (A.size() < B.size()) {
			A.keySet().retainAll(B.keySet());
			A.keySet().forEach(key -> {
				double weight = e.getEdge().getWeight() + A.get(key) + B.get(key);
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), key,
						weight);
				if (tr.getWeight() > w)
					triangles.add(tr);
			});

		} else {
			B.keySet().retainAll(A.keySet());
			B.keySet().forEach(key -> {
				double weight = e.getEdge().getWeight() + B.get(key) + A.get(key);
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), key,
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
			HashMap<Integer, Double> A = e.getA();
			HashMap<Integer, Double> B = e.getB();
			if (A.size() == 0 || B.size() == 0)
				continue;
			if (A.size() < B.size()) {
				Set<Integer> tmp = A.keySet().stream().filter(key -> {
					return B.containsKey(key);
				}).collect(Collectors.toSet());
				tmp.forEach(key -> {
					double weight = e.getEdge().getWeight() + A.get(key) + B.get(key);
					Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), key,
							weight);
					if (tr.getWeight() > w)
						triangles.add(tr);
				});

			} else {
				Set<Integer> tmp = B.keySet().stream().filter(key -> {
					return A.containsKey(key);
				}).collect(Collectors.toSet());
				tmp.forEach(key -> {
					double weight = e.getEdge().getWeight() + B.get(key) + A.get(key);
					Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), key,
							weight);
					if (tr.getWeight() > w)
						triangles.add(tr);
				});
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
		HashMap<Integer, Double> A = e.getA();
		HashMap<Integer, Double> B = e.getB();
		HashMap<Integer, Double> AP = e.getAP();
		HashMap<Integer, Double> BP = e.getBP();
		if (A.size() == 0 || B.size() == 0)
			return triangles;
		if (A.size() < B.size()) {
			Set<Integer> tmp = A.keySet().stream().filter(key -> {
				return B.containsKey(key);
			}).collect(Collectors.toSet());
			tmp.forEach(key -> {
				double weight = e.getEdge().getWeight() + A.get(key) + B.get(key);
				double probability = e.getEdge().getProbability() * AP.get(key) * BP.get(key);
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), key,
						weight, probability);
				// if (tr.getWeight() > w)
					triangles.add(tr);
			});
		} else {
			Set<Integer> tmp = B.keySet().stream().filter(key -> {
				return A.containsKey(key);
			}).collect(Collectors.toSet());
			tmp.forEach(key -> {
				double weight = e.getEdge().getWeight() + B.get(key) + A.get(key);
				double probability = e.getEdge().getProbability() * AP.get(key)
						* BP.get(key);
				Triangle tr = new Triangle(e.getEdge().getVertex1(), e.getEdge().getVertex2(), key,
						weight, probability);
				// if (tr.getWeight() > w)
					triangles.add(tr);
			});
		}
		return triangles;
	}
}