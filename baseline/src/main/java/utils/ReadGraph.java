package utils;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class ReadGraph {

	String fname;
	Graph graph;

	public ReadGraph(String fname) {
		this.fname = fname;
		graph = new Graph();

	}

	/**
	 * Read a graph from a file that has the format VertxA(int) VertxB(int) Generate
	 * random weights for each edge, following Gaussian distribution Store the
	 * vertices and edges in an object Graph. In the end sort the array of the edges
	 * based on their weight.
	 */
	public void readVV() {
		int tmp1 = 0;
		int tmp2 = 0;
		Random rand = new Random();

		try {
			Scanner scanner = new Scanner(new File(this.fname));
			while (scanner.hasNextInt()) {
				// Read a line
				tmp1 = scanner.nextInt();
				tmp2 = scanner.nextInt();
				if (tmp1 == tmp2)
					continue;
				double tmp3 = Math.abs(rand.nextGaussian() * 7);
				Vertex v1 = new Vertex(tmp1, tmp3);
				Vertex v2 = new Vertex(tmp2, tmp3);
				if (!graph.getL().containsKey(tmp1)) {
					graph.getL().put(tmp1, new ArrayList<Vertex>());
				}
				if (!graph.getL().get(tmp1).contains(v2)) {
					graph.getL().get(tmp1).add(v2);
				}

				if (!graph.getL().containsKey(tmp2)) {
					graph.getL().put(tmp2, new ArrayList<Vertex>());
				}
				if (!graph.getL().get(tmp2).contains(v1)) {
					graph.getL().get(tmp2).add(v1);
				}
				Edge tmp = new Edge(tmp1, tmp2, tmp3);
				if (!graph.getArray().contains(tmp))
					graph.getArray().add(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		graph.sortArrayWeight();
	}

	/**
	 * Read a graph from a file that has the format VertxA(int) VertxB(int) Generate
	 * random weights for each edge, following Gaussian distribution Store the
	 * vertices and edges in an object Graph. In the end sort the array of the edges
	 * based on their weight.
	 */
	public void readVV_WP() {
		int tmp1 = 0;
		int tmp2 = 0;
		Random rand = new Random();

		try {
			Scanner scanner = new Scanner(new File(this.fname));
			while (scanner.hasNextInt()) {
				// Read a line
				tmp1 = scanner.nextInt();
				tmp2 = scanner.nextInt();
				if (tmp1 == tmp2)
					continue;
				double tmp3 = Math.abs(rand.nextGaussian() * 7);
				double tmp4 = Math.abs(rand.nextGaussian()) % 1;
				Vertex v1 = new Vertex(tmp1, tmp3, tmp4);
				Vertex v2 = new Vertex(tmp2, tmp3, tmp4);
				if (!graph.getL().containsKey(tmp1)) {
					graph.getL().put(tmp1, new ArrayList<Vertex>());
				}
				if (!graph.getL().get(tmp1).contains(v2)) {
					graph.getL().get(tmp1).add(v2);
				}

				if (!graph.getL().containsKey(tmp2)) {
					graph.getL().put(tmp2, new ArrayList<Vertex>());
				}
				if (!graph.getL().get(tmp2).contains(v1)) {
					graph.getL().get(tmp2).add(v1);
				}
				Edge tmp = new Edge(tmp1, tmp2, tmp3, tmp4);
				if (!graph.getArray().contains(tmp))
					graph.getArray().add(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		graph.sortArrayWeight();
		graph.sortArrayProbability();
	}

	/**
	 * Read a graph from a file that has the format VertxA(int) VertxB(int)
	 * Weight(double) Store the vertices and edges in an object Graph. In the end
	 * sort the array of the edges based on their weight.
	 */
	public void readVVW() {
		int tmp1 = 0;
		int tmp2 = 0;
		double tmp3 = 0;
		try {
			Scanner scanner = new Scanner(new File(this.fname));
			while (scanner.hasNextInt()) {
				// Read a line
				tmp1 = scanner.nextInt();
				tmp2 = scanner.nextInt();
				tmp3 = scanner.nextDouble();
				if (!graph.getL().containsKey(tmp1)) {
					graph.getL().put(tmp1, new ArrayList<Vertex>());
				}
				if (graph.getL().get(tmp1).contains(new Vertex(tmp2, tmp3)))
					graph.getL().get(tmp1).add(new Vertex(tmp2, tmp3));
				if (!graph.getL().containsKey(tmp2)) {
					graph.getL().put(tmp2, new ArrayList<Vertex>());
				}
				if (graph.getL().get(tmp1).contains(new Vertex(tmp1, tmp3)))
					graph.getL().get(tmp2).add(new Vertex(tmp1, tmp3));
				Edge tmp = new Edge(tmp1, tmp2, tmp3);
				if (!graph.getArray().contains(tmp))
					graph.getArray().add(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		graph.sortArrayWeight();
	}

	public void readVVWP() {
		int tmp1 = 0;
		int tmp2 = 0;
		double tmp3 = 0;
		double tmp4 = 0;
		try {
			Scanner scanner = new Scanner(new File(this.fname));
			while (scanner.hasNextInt()) {
				// Read a line
				tmp1 = scanner.nextInt();
				tmp2 = scanner.nextInt();
				tmp3 = scanner.nextDouble();
				tmp4 = scanner.nextDouble();
				if (!graph.getL().containsKey(tmp1)) {
					graph.getL().put(tmp1, new ArrayList<Vertex>());
				}
				graph.getL().get(tmp1).add(new Vertex(tmp2, tmp3, tmp4));
				if (!graph.getL().containsKey(tmp2)) {
					graph.getL().put(tmp2, new ArrayList<Vertex>());
				}
				graph.getL().get(tmp2).add(new Vertex(tmp1, tmp3, tmp4));
				Edge tmp = new Edge(tmp1, tmp2, tmp3, tmp4);
				graph.getArray().add(tmp);
			}
			graph.getAdjacencyMatrixP();
			graph.getAdjacencyMatrixW();
		} catch (Exception e) {
			e.printStackTrace();
		}
		graph.sortArrayWeight();
		graph.sortArrayProbability();
	}

	public Graph getGraph() {
		return graph;
	}
}
