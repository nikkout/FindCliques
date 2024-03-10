package legacy;

class Tupple{
    int vertex;
    double weight;
    
    public Tupple(int vertex, double weight){
        this.vertex = vertex;
        this.weight = weight;
    }
    
    public boolean equals(Object o){
	    
        //System.out.println("My Equals");
    
        if (!(o instanceof Tupple)) {
            return false;
        }
        return this.vertex == ((Tupple) o).vertex; 
    }
}
