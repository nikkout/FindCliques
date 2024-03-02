package utils;

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
	private double[] A;
	private double[] B;
	private double[] AP;
	private double[] BP;
    
    public EdgeLists(Edge edge, double[] A, double[] B){
        this.edge = edge;
        this.A = A;
        this.B = B;
    }
    
    public EdgeLists(Edge edge, double[] A, double[] B, double[] AP, double[] BP){
        this.edge = edge;
        this.A = A;
        this.B = B;
        this.AP = AP;
        this.BP = BP;
    }
}
