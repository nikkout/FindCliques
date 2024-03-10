package legacy;

class Clique5 implements Weighted{
    
    int vertex1;
    int vertex2;
    int vertex3;
    int vertex4;
    int vertex5;
    double weight;

    @Override
    public double get_weight(){
    	return this.weight;
    }

    public Clique5(Clique4 c1, Clique4 c2, Edge edge, double weight){
	int[] a = {c1.vertex1, c1.vertex2, c1.vertex3, c1.vertex4};
	int[] b = {c2.vertex1, c2.vertex2, c2.vertex3, c2.vertex4};
	int[] c = {-1, -1, -1, -1, -1};
	int count =0;
	int ac = 0;
	int bc = 0;

	while(count <=4){
	    if(bc>=4 || (ac < 4 && a[ac] < b[bc])) {
		if(c[count] == -1){c[count] = a[ac];count++;}
		ac++;
	    }
	    else if(ac >=4 || a[ac] > b[bc]){
	        if(c[count] == -1 ){c[count] = b[bc];count++;}
                bc++;
	    }
	    else if(ac < 4){
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
    this.vertex5 = c[4];
	this.weight = weight;
    }

    public int hashCode() {
       return this.vertex1*10000+vertex2*1000+vertex3*100+10*vertex4+vertex5; 
    }

    public boolean equals(Object o){

        if (!(o instanceof Clique5)) {
            return false;
        }
	if(this.vertex1 == ((Clique5)o).vertex1 && this.vertex2 == ((Clique5)o).vertex2 && this.vertex3 == ((Clique5)o).vertex3 
    && this.vertex4 == ((Clique5)o).vertex4 && this.vertex5 == ((Clique5)o).vertex5) return true;
        return false;
    }

    public String toString(){
    	return this.vertex1+" "+this.vertex2+" "+this.vertex3+" "+this.vertex4+"  | size:"+this.weight;
    }

}
