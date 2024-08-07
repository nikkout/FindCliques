package utils;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Slf4j
public class ReadGraph {

	String fname;
	Graph graph;

	public ReadGraph(String fname) {
		this(fname, 0);

	}

	public ReadGraph(String fname, int arraySize) {
		this.fname = fname;
		graph = new Graph(arraySize);

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
					graph.getL().put(tmp1, new HashMap<Integer, Double>());
				}
				if (!graph.getL().get(tmp1).containsKey(v2.getVertex())) {
					graph.getL().get(tmp1).put(v2.getVertex(), v2.getWeight());
				}

				if (!graph.getL().containsKey(tmp2)) {
					graph.getL().put(tmp2, new HashMap<Integer, Double>());
				}
				if (!graph.getL().get(tmp2).containsKey(v1.getVertex())) {
					graph.getL().get(tmp2).put(v1.getVertex(), v1.getWeight());
				}
				Edge tmp = new Edge(tmp1, tmp2, tmp3);
				if (!graph.getArray().contains(tmp))
					graph.getArray().add(tmp);

			}
			scanner.close();
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
				if (!graph.getL().containsKey(tmp1)) {
					graph.getL().put(tmp1, new HashMap<Integer, Double>());
				}
				if (!graph.getLP().containsKey(tmp1)) {
					graph.getLP().put(tmp1, new HashMap<Integer, Double>());
				}
				if (!graph.getL().get(tmp1).containsKey(tmp2)) {
					graph.getL().get(tmp1).put(tmp2, tmp3);
					graph.getLP().get(tmp1).put(tmp2, tmp4);
				}
				if (!graph.getL().containsKey(tmp2)) {
					graph.getL().put(tmp2, new HashMap<Integer, Double>());
				}
				if (!graph.getLP().containsKey(tmp2)) {
					graph.getLP().put(tmp2, new HashMap<Integer, Double>());
				}
				if (!graph.getL().get(tmp2).containsKey(tmp1)) {
					graph.getL().get(tmp2).put(tmp1, tmp3);
					graph.getLP().get(tmp2).put(tmp1, tmp4);
				}
				Edge tmp = new Edge(tmp1, tmp2, tmp3, tmp4);
				if (!graph.getArray().contains(tmp))
					graph.getArray().add(tmp);
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		graph.sortArrayWeight();
		graph.sortArrayProbability();

	}

	/**
	 * Read a graph from a file that has the format VertxA(int) VertxB(int) Generate
	 * random weights for each edge, following Gaussian distribution Store the
	 * vertices and edges in an object Graph. In the end sort the array of the edges
	 * based on their weight.
	 */
	public void readVVW_P() {
		int tmp1 = 0;
		int tmp2 = 0;
		double tmp3 = 0;
		Random rand = new Random();

		try {
			Scanner scanner = new Scanner(new File(this.fname));
			while (scanner.hasNextInt()) {
				// Read a line
				tmp1 = scanner.nextInt();
				tmp2 = scanner.nextInt();
				if (tmp1 == tmp2)
					continue;
				tmp3 = scanner.nextDouble();
				double tmp4 = Math.abs(rand.nextGaussian()) % 1;
				if (!graph.getL().containsKey(tmp1)) {
					graph.getL().put(tmp1, new HashMap<Integer, Double>());
				}
				if (!graph.getLP().containsKey(tmp1)) {
					graph.getLP().put(tmp1, new HashMap<Integer, Double>());
				}
					graph.getL().get(tmp1).put(tmp2, tmp3);
					graph.getLP().get(tmp1).put(tmp2, tmp4);
				if (!graph.getL().containsKey(tmp2)) {
					graph.getL().put(tmp2, new HashMap<Integer, Double>());
				}
				if (!graph.getLP().containsKey(tmp2)) {
					graph.getLP().put(tmp2, new HashMap<Integer, Double>());
				}
					graph.getL().get(tmp2).put(tmp1, tmp3);
					graph.getLP().get(tmp2).put(tmp1, tmp4);
				Edge tmp = new Edge(tmp1, tmp2, tmp3, tmp4);
				graph.getArray().add(tmp);
			}
			scanner.close();
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
		log.debug("start reading graph");
		int tmp1 = 0;
		int tmp2 = 0;
		double tmp3 = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fname)));
			String st;
			while ((st = br.readLine()) != null) {
				String[] tmp_arr = st.split(" ");
				tmp1 = Integer.parseInt(tmp_arr[0]);
				tmp2 = Integer.parseInt(tmp_arr[1]);
				tmp3 = Double.parseDouble(tmp_arr[2]);
				Vertex v1 = new Vertex(tmp1, tmp3);
				Vertex v2 = new Vertex(tmp2, tmp3);
				if (!graph.getL().containsKey(tmp1)) {
					graph.getL().put(tmp1, new HashMap<Integer, Double>());
				}
				// if (!graph.getL().get(tmp1).contains(v2)) {
				graph.getL().get(tmp1).put(v2.getVertex(), v2.getWeight());
				// }

				if (!graph.getL().containsKey(tmp2)) {
					graph.getL().put(tmp2, new HashMap<Integer, Double>());
				}
				// if (!graph.getL().get(tmp2).contains(v1)) {
				graph.getL().get(tmp2).put(v1.getVertex(), v1.getWeight());
				// }
				Edge tmp = new Edge(tmp1, tmp2, tmp3);
				// if (!graph.getArrayMap().contains(tmp)) {
				graph.getArray().add(tmp);
				// graph.getArrayMap().add(tmp);
				// }
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		graph.sortArrayWeight();
		log.debug("finished reading graph");
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
					graph.getL().put(tmp1, new HashMap<Integer, Double>());
				}
				if (!graph.getLP().containsKey(tmp1)) {
					graph.getLP().put(tmp1, new HashMap<Integer, Double>());
				}
				graph.getL().get(tmp1).put(tmp2, tmp3);
				graph.getLP().get(tmp1).put(tmp2, tmp4);
				if (!graph.getL().containsKey(tmp2)) {
					graph.getL().put(tmp2, new HashMap<Integer, Double>());
				}
				if (!graph.getLP().containsKey(tmp2)) {
					graph.getLP().put(tmp2, new HashMap<Integer, Double>());
				}
				graph.getL().get(tmp2).put(tmp1, tmp3);
				graph.getLP().get(tmp2).put(tmp1, tmp4);
				Edge tmp = new Edge(tmp1, tmp2, tmp3, tmp4);
				graph.getArray().add(tmp);
			}
			graph.getAdjacencyMatrixP();
			graph.getAdjacencyMatrixW();
			scanner.close();
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
