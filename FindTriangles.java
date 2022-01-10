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
    //PriorityBlockingQueue<Triangle> T;
    PriorityBlockingQueue<Clique4> C4;
    int start;
    int end;
    int Size;
    HashMap<Edge, ArrayList<Clique4Value>> Clique_4;
    HashMap<Integer, ArrayList<Tupple>> L;
    HashMap<Integer, ArrayList<Tupple>> HS;
    ArrayList<Triangle> triangles;
    static int counter = 0;
    PriorityBlockingQueue<Triangle> Tl;

    public FindTriangles(E[] e, int start, int end, int Size, HashMap<Integer, ArrayList<Tupple>> L, HashMap<Integer, ArrayList<Tupple>> HS){
        this.e = e;
        this.start = start;
        this.end = end;
        this.Size = Size;
	this.Clique_4 = new HashMap<Edge, ArrayList<Clique4Value>>();
	this.triangles = new ArrayList<Triangle>();
	this.L = L;
	this.HS = HS;
        FindTriangles.counter++;
        Tl = new PriorityBlockingQueue<Triangle>(this.Size, new Comparator<Triangle>() {
            @Override
            public int compare(Triangle lhs, Triangle rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? 1 : (lhs.weight < rhs.weight) ? -1 : 0;
            }
        });
        C4 = new PriorityBlockingQueue<Clique4>(this.Size, new Comparator<Clique4>() {
            @Override
            public int compare(Clique4 lhs, Clique4 rhs) {
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
		    if (!this.Clique_4.containsKey(current.edge)) Clique_4.put(current.edge, new ArrayList<Clique4Value>());
	            Clique_4.get(current.edge).add(new Clique4Value(tr,vertex));
		    //Check if triangle is one of the k-heaviest, if it is added
		    //the k-heaviest that currently have been found
                    if(Tl.size()< this.Size && !Tl.contains(tr)){
                        Tl.put(tr);
                    }
                    else if(Tl.peek().weight < tr.weight && !Tl.contains(tr)){
                        Tl.poll();
                        Tl.put(tr);
                    }
                    /*while(true){
                        Triangle tmp = Tl.poll();
                        if(tmp == null || (this.T.size() >= this.Size && this.T.peek().weight >= tmp.weight)) break;
                        if(this.T.size() < this.Size) this.T.put(tmp);
                        else {
                            this.T.poll();
                            this.T.put(tmp);
                        }
		    }*/
                    //this.T.add(tr);
		    //this.triangles.add(tr);
                }
            }
        }
    }

    private void clique4(){
	Set<Edge> keys = this.Clique_4.keySet();
        for(Edge key: keys){
            ArrayList<Clique4Value> arr = this.Clique_4.get(key);
	    for(int i=0;i<arr.size();i++){
	        for(int y=i;y<arr.size();y++){
		    int vertexA = arr.get(i).vertex;
		    int vertexB = arr.get(y).vertex;
		    int posL = -1, posH = -1;
		    Tupple tmp = null;
		    if(this.L.containsKey(vertexA)) posL = this.L.get(vertexA).indexOf(new Tupple(vertexB, 0));
		    if(posL == -1 && this.HS.containsKey(vertexA)) posH = this.HS.get(vertexA).indexOf(new Tupple(vertexB, 0));
	    	    if(posL != -1) tmp = this.L.get(vertexA).get(posL);
	            else if(posH != -1) tmp = this.HS.get(vertexA).get(posH);
		    if(tmp != null) {
			    Triangle t1 = arr.get(i).triangle;
			    Triangle t2 = arr.get(y).triangle;
			    Clique4 clique = new Clique4(t1, t2, new Edge(arr.get(y).vertex, tmp.vertex, tmp.weight), t1.weight+t2.weight+tmp.weight-key.weight);
			    if(C4.size()< this.Size && !C4.contains(clique)){
                        C4.put(clique);
                    }
                    else if(C4.peek().weight < clique.weight && !C4.contains(clique)){
                        C4.poll();
                        C4.put(clique);
                    }
			    this.C4.add(new Clique4(t1, t2, new Edge(arr.get(y).vertex, tmp.vertex, tmp.weight), t1.weight+t2.weight+tmp.weight-key.weight));
		    }
                }
	    }
	}
    }

    public void run() {
        this.Triangles();
	    this.clique4();
    }
}

class Clique4Value{
	Triangle triangle;
	int vertex;

	public Clique4Value(Triangle triangle, int vertex){
		this.triangle = triangle;
		this.vertex = vertex;
	}
}
