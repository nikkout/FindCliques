package utils;

public class Triangle{
	private int vertex1;
	private int vertex2;
	private int vertex3;
	private double weight;
	private double probability;

	public Triangle(int vertex1, int vertex2, int vertex3, double weight) {
		this(vertex1, vertex2, vertex3, weight, 0);
	}

	public Triangle(int vertex1, int vertex2, int vertex3, double weight, double probability) {
		if (vertex1 < vertex2 && vertex1 < vertex3) {
			this.vertex1 = vertex1;
			if (vertex2 < vertex3) {
				this.vertex2 = vertex2;
				this.vertex3 = vertex3;
			} else {
				this.vertex2 = vertex3;
				this.vertex3 = vertex2;
			}
		} else if (vertex2 < vertex3) {
			this.vertex1 = vertex2;
			if (vertex1 < vertex3) {
				this.vertex2 = vertex1;
				this.vertex3 = vertex3;
			} else {
				this.vertex2 = vertex3;
				this.vertex3 = vertex1;
			}
		} else {
			this.vertex1 = vertex3;
			if (vertex1 < vertex2) {
				this.vertex2 = vertex1;
				this.vertex3 = vertex2;
			} else {
				this.vertex2 = vertex2;
				this.vertex3 = vertex1;
			}
		}
		this.weight = weight;
		this.probability = probability;
	}

	public boolean equals(Object o) {

		if (!(o instanceof Triangle)) {
			return false;
		}
		if (this.vertex1 == ((Triangle) o).vertex1 && this.vertex2 == ((Triangle) o).vertex2
				&& this.vertex3 == ((Triangle) o).vertex3)
			return true;
		return false;
	}

	public int hashCode() {
		return this.vertex1 * 100 + vertex2 * 10 + vertex3;
	}

	public String toString() {
		return this.vertex1 + " " + this.vertex2 + " " + this.vertex3 + "|W: " + this.weight + " |P: "
				+ this.probability;
	}

	public int getVertex1() {
		return vertex1;
	}

	public void setVertex1(int vertex1) {
		this.vertex1 = vertex1;
	}

	public int getVertex2() {
		return vertex2;
	}

	public void setVertex2(int vertex2) {
		this.vertex2 = vertex2;
	}

	public int getVertex3() {
		return vertex3;
	}

	public void setVertex3(int vertex3) {
		this.vertex3 = vertex3;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}
}
