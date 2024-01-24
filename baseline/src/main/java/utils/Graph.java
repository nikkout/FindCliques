package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Graph {
	private HashMap<Integer, ArrayList<Vertex>> L;
	private HashMap<Integer, ArrayList<Vertex>> H;
	private HashMap<Integer, ArrayList<Vertex>> HS;

	public HashMap<Integer, ArrayList<Vertex>> getL() {
		return L;
	}

	public void setL(HashMap<Integer, ArrayList<Vertex>> l) {
		L = l;
	}

	public HashMap<Integer, ArrayList<Vertex>> getH() {
		return H;
	}

	public void setH(HashMap<Integer, ArrayList<Vertex>> h) {
		H = h;
	}

	public HashMap<Integer, ArrayList<Vertex>> getHS() {
		return HS;
	}

	public void setHS(HashMap<Integer, ArrayList<Vertex>> hS) {
		HS = hS;
	}

	public HashMap<Integer, ArrayList<Vertex>> getS() {
		return S;
	}

	public void setS(HashMap<Integer, ArrayList<Vertex>> s) {
		S = s;
	}

	public ArrayList<Edge> getArray() {
		return array;
	}

	public void setArray(ArrayList<Edge> array) {
		this.array = array;
	}

	private HashMap<Integer, ArrayList<Vertex>> S;
	private ArrayList<Edge> array;

	public Graph() {
		this.L = new HashMap<>();
		this.H = new HashMap<>();
		this.HS = new HashMap<>();
		this.S = new HashMap<>();
		this.array = new ArrayList<>();
	}

	public void sortArrayWeight() {
		Collections.sort(this.array, new Comparator<Edge>() {
			@Override
			public int compare(Edge lhs, Edge rhs) {
				// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
				return lhs.getWeight() > rhs.getWeight() ? -1 : (lhs.getWeight() < rhs.getWeight()) ? 1 : 0;
			}
		});
	}

	public void toFileWP(File f) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			array.forEach(e -> {
				try {
					bw.write(e.getVertex1() + " " + e.getVertex2() + " " + e.getWeight() + " " + e.getProbability());
					bw.newLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void toFileW(File f) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			array.forEach(e -> {
				try {
					bw.write(e.getVertex1() + " " + e.getVertex2() + " " + e.getWeight());
					bw.newLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int findMaxVertex() {
		AtomicInteger max = new AtomicInteger(0);
		L.forEach((i, v) -> {
			if (max.get() < i) {
				max.set(i);
			}
		});
		return max.get();
	}

	public int[][] getAdjacencyMatrix() {
		int V = findMaxVertex() + 1;
		int[][] matrix = new int[V][V];
		this.array.forEach(e -> {
			matrix[e.getVertex1()][e.getVertex2()] = 1;
			matrix[e.getVertex2()][e.getVertex1()] = 1;
		});
		return matrix;
	}

	public double[][] getAdjacencyMatrixW() {
		int V = findMaxVertex() + 1;
		double[][] matrix = new double[V][V];
		this.array.forEach(e -> {
			matrix[e.getVertex1()][e.getVertex2()] = e.getWeight();
			matrix[e.getVertex2()][e.getVertex1()] = e.getWeight();
		});
		return matrix;
	}
	
	public double[][] getAdjacencyMatrixP() {
		int V = findMaxVertex() + 1;
		double[][] matrix = new double[V][V];
		this.array.forEach(e -> {
			matrix[e.getVertex1()][e.getVertex2()] = e.getProbability();
			matrix[e.getVertex2()][e.getVertex1()] = e.getProbability();
		});
		return matrix;
	}
}
