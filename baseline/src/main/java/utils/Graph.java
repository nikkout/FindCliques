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

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Graph {
	private HashMap<Integer, ArrayList<Vertex>> L;
	private HashMap<Integer, ArrayList<Vertex>> LP;
	private HashMap<Integer, ArrayList<Vertex>> HS;
	private HashMap<Integer, ArrayList<Vertex>> HSP;
	private ArrayList<Edge> array;
	private ArrayList<Edge> arrayP;

	public Graph() {
		this.array = new ArrayList<>();
		this.L = new HashMap<>();
		this.LP = new HashMap<>();
		this.HS = new HashMap<>();
		this.HSP = new HashMap<>();
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
	
	public void sortArrayProbability() {
		this.arrayP = new ArrayList<Edge>();
		this.array.forEach(edge ->{
			this.arrayP.add(edge);
		});
		Collections.sort(this.arrayP, new Comparator<Edge>() {
			@Override
			public int compare(Edge lhs, Edge rhs) {
				// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
				return lhs.getProbability() > rhs.getProbability() ? -1 : (lhs.getProbability() < rhs.getProbability()) ? 1 : 0;
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
		this.array.forEach((i) -> {
			if (max.get() < i.getVertex2()) {
				max.set(i.getVertex2());
			}
		});
		return max.get();
	}

	public double[][] getAdjacencyMatrix() {
		int V = findMaxVertex() + 1;
		double[][] matrix = new double[V][V];
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
