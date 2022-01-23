class Triangle implements Weighted{
    int vertex1;
    int vertex2;
    int vertex3;
    double weight;

    @Override
    public double get_weight(){
    	return this.weight;
    }
    
    public Triangle(int vertex1, int vertex2, int vertex3, double weight){
        if(vertex1 < vertex2 && vertex1 < vertex3){
            this.vertex1 = vertex1;
            if(vertex2 < vertex3){
                this.vertex2 = vertex2;
                this.vertex3 = vertex3;
            }
            else{
                this.vertex2 = vertex3;
                this.vertex3 = vertex2;
            }
        }
        else if(vertex2 < vertex3){
            this.vertex1 = vertex2;
            if(vertex1 < vertex3){
                this.vertex2 = vertex1;
                this.vertex3 = vertex3;
            }
            else{
                this.vertex2 = vertex3;
                this.vertex3 = vertex1;
            }
        }
        else{
            this.vertex1 = vertex3;
            if(vertex1 < vertex2){
                this.vertex2 = vertex1;
                this.vertex3 = vertex2;
            }
            else{
                this.vertex2 = vertex2;
                this.vertex3 = vertex1;
            }
        }
        this.weight = weight;
    }
    
    public boolean equals(Object o){
    
        if (!(o instanceof Triangle)) {
            return false;
        }
        if(this.vertex1 == ((Triangle) o).vertex1 && this.vertex2 == ((Triangle) o).vertex2 && this.vertex3 == ((Triangle) o).vertex3)return true;
        return false; 
    }
    
    public int hashCode() {
        return this.vertex1*100+vertex2*10+vertex3;
    }

    public String toString(){
    	return this.vertex1+" "+this.vertex2+" "+this.vertex3 + "  | size: "+this.weight;
    }
}
