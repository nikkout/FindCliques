package utils;

import java.util.ArrayList;

public class EdgeLists {
	@Override
	public String toString() {
		return "EdgeLists [edge=" + edge + ", A=" + A + ", B=" + B + "]";
	}

	private Edge edge;
	private ArrayList<Vertex> A;
	private ArrayList<Vertex> B;
    
    public EdgeLists(Edge edge, ArrayList<Vertex> A, ArrayList<Vertex> B){
        this.edge = edge;
        this.A = A;
        this.B = B;
    }

	public Edge getEdge() {
		return edge;
	}

	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	public ArrayList<Vertex> getA() {
		return A;
	}

	public void setA(ArrayList<Vertex> a) {
		A = a;
	}

	public ArrayList<Vertex> getB() {
		return B;
	}

	public void setB(ArrayList<Vertex> b) {
		B = b;
	}
}
