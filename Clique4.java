class Clique4{
    
    int vertex1;
    int vertex2;
    int vertex3;
    int vertex4;
    double weight;

    public Clique4(Triangle triangleA, Triangle triangleB, Edge edge, double weight){
	int[] a = {triangleA.vertex1, triangleA.vertex2, triangleA.vertex3};
	int[] b = {triangleB.vertex1, triangleB.vertex2, triangleB.vertex3};
	int[] c = {-1, -1, -1, -1};
	int count =0;
	int ac = 0;
	int bc = 0;

	while(count <=3){
	    if(bc>=3 || (ac < 3 && a[ac] < b[bc])) {
		if(c[count] == -1){c[count] = a[ac];count++;}
		ac++;
	    }
	    else if(ac >=3 || a[ac] > b[bc]){
	        if(c[count] == -1 ){c[count] = b[bc];count++;}
                bc++;
	    }
	    else if(ac < 3){
	        if(c[count] == -1 ){c[count] = a[ac];count++;}
                ac++;
		bc++;
	    }
	    else {
	        if(c[count] == -1 ){c[count] = b[bc];count++;}
                ac++;
                bc++;
	    }
	}
	this.vertex1 = c[0];
	this.vertex2 = c[1];
	this.vertex3 = c[2];
	this.vertex4 = c[3];
	this.weight = weight;
    }

    public int hashCode() {
       return this.vertex1*1000+vertex2*100+vertex3*10+vertex4; 
    }

    public boolean equals(Object o){

        if (!(o instanceof Clique4)) {
            return false;
        }
	if(this.vertex1 == ((Clique4)o).vertex1 && this.vertex2 == ((Clique4)o).vertex2 && this.vertex3 == ((Clique4)o).vertex3 && this.vertex4 == ((Clique4)o).vertex4) return true;
        return false;
    }

    public String toString(){
    	return this.vertex1+" "+this.vertex2+" "+this.vertex3+" "+this.vertex4+"  | size:"+this.weight;
    }

}
