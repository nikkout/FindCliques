class Edge{
    int vertex1;
    int vertex2;
    double weight;
    
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

    public boolean equals(Object o){

        if (!(o instanceof Edge)) {
            return false;
        }
        if((this.vertex1 == ((Edge) o).vertex1 && this.vertex2 == ((Edge) o).vertex2) || (this.vertex1 == ((Edge) o).vertex2 && this.vertex2 == ((Edge) o).vertex1))return true;
        return false;
    }
}
