package utils;

import java.io.Serializable;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EdgeLists implements Serializable{
	@Override
	public String toString() {
		return "EdgeLists [edge=" + edge + ", A=" + A + ", B=" + B + "]";
	}

	private Edge edge;
	private HashMap<Integer, Double> A;
	private HashMap<Integer, Double> B;
	private HashMap<Integer, Double> AP;
	private HashMap<Integer, Double> BP;

	public EdgeLists(Edge edge, HashMap<Integer, Double> A, HashMap<Integer, Double> B) {
		this.edge = edge;
		this.A = A;
		this.B = B;
	}

	public EdgeLists(Edge edge, HashMap<Integer, Double> A, HashMap<Integer, Double> B, HashMap<Integer, Double> AP, HashMap<Integer, Double> BP) {
		this.edge = edge;
		this.A = A;
		this.B = B;
		this.AP = AP;
		this.BP = BP;
	}
}
