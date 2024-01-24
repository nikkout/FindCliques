package utils;

import java.util.Objects;

public class Vertex {
	@Override
	public String toString() {
		return "Vertex [vertex=" + vertex + ", weight=" + weight + ", probability=" + probability + "]";
	}


	private int vertex;
	private double weight;
	private double probability;

	public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
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

	public Vertex(int vertex, double weight, double probability){
        this.vertex = vertex;
        this.weight = weight;
        this.probability = probability;
    }
	
	public Vertex(int vertex, double weight){
        this.vertex = vertex;
        this.weight = weight;
    }
	

	@Override
	public int hashCode() {
		return Objects.hash(probability, vertex, weight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		return vertex == other.vertex;
	}
}
