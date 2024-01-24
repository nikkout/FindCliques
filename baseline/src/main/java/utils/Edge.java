package utils;

public class Edge{
    @Override
	public String toString() {
		return "Edge [vertex1=" + vertex1 + ", vertex2=" + vertex2 + ", weight=" + weight + ", probability="
				+ probability + "]";
	}

	private int vertex1;
    private int vertex2;
    private double weight;
    private double probability;
    
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

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getProbability() {
		return probability;
	}

	public Edge(int vertex1, int vertex2, double weight){
    	if(vertex1 < vertex2){
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }
        else{
            this.vertex1 = vertex2;
            this.vertex2 = vertex1;
        }
        
        this.weight = weight;
    }
    
    public Edge(int vertex1, int vertex2, double weight, double probability){
        if(vertex1 < vertex2){
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }
        else{
            this.vertex1 = vertex2;
            this.vertex2 = vertex1;
        }
        
        this.weight = weight;
        this.probability = probability;
    }
    
    public void setProbability(double probability){
    	this.probability = probability;
    }

    public boolean equals(Object o){

        if (!(o instanceof Edge)) {
            return false;
        }
        if((this.vertex1 == ((Edge) o).vertex1 && this.vertex2 == ((Edge) o).vertex2) || (this.vertex1 == ((Edge) o).vertex2 && this.vertex2 == ((Edge) o).vertex1))return true;
        return false;
    }
}