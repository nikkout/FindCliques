import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;

class FindTriangles implements Runnable{

    E[] e;
    PriorityBlockingQueue<Triangle> T;
    //Set<Triangle> T;
    Set<Clique4> C4;
    int start;
    int end;
    int Tsize;
    HashMap<Edge, ArrayList<Integer>> Clique_4;
    HashMap<Integer, ArrayList<Tupple>> L;
    HashMap<Integer, ArrayList<Tupple>> HS;
    ArrayList<Triangle> triangles;
    static int counter = 0;
    PriorityBlockingQueue<Triangle> Tl;

    public FindTriangles(E[] e, PriorityBlockingQueue<Triangle> T, Set<Clique4> C4, int start, int end, int Tsize, HashMap<Integer, ArrayList<Tupple>> L, HashMap<Integer, ArrayList<Tupple>> HS){
        this.e = e;
        this.T = T;
	this.C4 = C4;
        this.start = start;
        this.end = end;
        this.Tsize = Tsize;
	this.Clique_4 = new HashMap<Edge, ArrayList<Integer>>();
	this.triangles = new ArrayList<Triangle>();
	this.L = L;
	this.HS = HS;
        FindTriangles.counter++;
        Tl = new PriorityBlockingQueue<Triangle>(this.Tsize, new Comparator<Triangle>() {
            @Override
            public int compare(Triangle lhs, Triangle rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? 1 : (lhs.weight < rhs.weight) ? -1 : 0;
            }
        });
    }

    private void Triangles(){
        for(int i=this.start;i<this.end;i++){
            //if(i%10000==0)System.out.println(i);
            for(int y=0;y<e[i].A.size();y++){
                E current = e[i];
		int vertex = current.A.get(y).vertex;
                if(current.B.contains(new Tupple(vertex, 0))){
                    double weight = current.edge.weight+current.A.get(y).weight+current.B.get(current.B.indexOf(new Tupple(current.A.get(y).vertex, 0))).weight;
                    Triangle tr = new Triangle(current.edge.vertex1, current.edge.vertex2, current.A.get(y).vertex, weight);
		    //!if (!this.Clique_4.containsKey(current.edge)) Clique_4.put(current.edge, new ArrayList<Integer>());
	            //Clique_4.get(current.edge).add(vertex);
                    if(Tl.size()< this.Tsize && !Tl.contains(tr)){
                        Tl.put(tr);
                    }
                    else if(Tl.peek().weight < tr.weight && !Tl.contains(tr)){
                        Tl.poll();
                        Tl.put(tr);
                    }
                    /*while(true){
                        Triangle tmp = Tl.poll();
                        if(tmp == null || (this.T.size() >= this.Tsize && this.T.peek().weight >= tmp.weight)) break;
                        if(this.T.size() < this.Tsize) this.T.put(tmp);
                        else {
                            this.T.poll();
                            this.T.put(tmp);
                        }
		    }*/
                    //this.T.add(tr);
		    //!this.triangles.add(tr);
                }
            }
        }
    }

    private void clique4(){
	Set<Edge> keys = this.Clique_4.keySet();
        for(Edge key: keys){
            ArrayList<Integer> arr = this.Clique_4.get(key);
	    for(int i=0;i<arr.size();i++){
	        for(int y=i;y<arr.size();y++){
		    int temp = arr.get(i);
		    int pos = -1;
		    if(this.L.containsKey(temp)) pos = this.L.get(arr.get(i)).indexOf(new Tupple(arr.get(y), 0));
		    Tupple tmp = null;
		    if(pos == -1 && this.HS.containsKey(temp)) pos = this.HS.get(temp).indexOf(new Tupple(arr.get(y), 0));
		    else tmp =  this.L.get(temp).get(pos);
	    	    if(pos >= 0) {
			    if(tmp == null) tmp =  this.HS.get(temp).get(pos);
			    Triangle t1 = this.triangles.get(this.triangles.indexOf(new Triangle(key.vertex1, key.vertex2, arr.get(i), 0)));
			    Triangle t2 = this.triangles.get(this.triangles.indexOf(new Triangle(key.vertex1, key.vertex2, arr.get(y), 0)));
			    //this.C4.add(new Clique4(t1, t2, new Edge(arr.get(y), tmp.vertex, tmp.weight), t1.weight+t2.weight+tmp.weight-key.weight));
		    }
                }
	    }
	}
    }

    public void run() {
        this.Triangles();
	//this.clique4();
    }
}
