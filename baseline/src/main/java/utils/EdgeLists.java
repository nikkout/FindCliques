package utils;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EdgeLists {
	@Override
	public String toString() {
		return "EdgeLists [edge=" + edge + ", A=" + A + ", B=" + B + "]";
	}

	private Edge edge;
	private ArrayList<Vertex> A;
	private ArrayList<Vertex> B;

	public EdgeLists(Edge edge, ArrayList<Vertex> A, ArrayList<Vertex> B) {
		this.edge = edge;
		this.A = A;
		this.B = B;
	}
}
