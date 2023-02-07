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
    PriorityBlockingQueue<Clique5> C5;
    HashMap<Edge, ArrayList<Clique4Value>> Clique_4;
    HashMap<Triangle, ArrayList<Clique5Value>> Clique_5;
    ArrayList<Triangle> triangles;
    static int counter = 0;
    PriorityBlockingQueue<Triangle> Tl;
    Triangle ht = null;
    Parameters parameters;

    public FindTriangles(Parameters parameters){
        this.parameters = parameters;
	this.Clique_4 = new HashMap<Edge, ArrayList<Clique4Value>>();
    this.Clique_5 = new HashMap<Triangle, ArrayList<Clique5Value>>();
	this.triangles = new ArrayList<Triangle>();
        FindTriangles.counter++;
        Tl = new PriorityBlockingQueue<Triangle>(this.parameters.Size, new Comparator<Triangle>() {
            @Override
            public int compare(Triangle lhs, Triangle rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? 1 : (lhs.weight < rhs.weight) ? -1 : 0;
            }
        });
        C4 = new PriorityBlockingQueue<Clique4>(this.parameters.Size, new Comparator<Clique4>() {
            @Override
            public int compare(Clique4 lhs, Clique4 rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? 1 : (lhs.weight < rhs.weight) ? -1 : 0;
            }
        });
        C5 = new PriorityBlockingQueue<Clique5>(this.parameters.Size, new Comparator<Clique5>() {
            @Override
            public int compare(Clique5 lhs, Clique5 rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? 1 : (lhs.weight < rhs.weight) ? -1 : 0;
            }
        });
    }

    private void Triangles(){
        for(int i=this.parameters.start;i<this.parameters.end;i++){
            //if(i%10000==0)System.out.println(i);
            for(int y=0;y<this.parameters.e[i].A.size();y++){
                E current = parameters.e[i];
		int vertex = current.A.get(y).vertex;
                if(current.B.contains(new Tupple(vertex, 0))){
                    double weight = current.edge.weight+current.A.get(y).weight+current.B.get(current.B.indexOf(new Tupple(current.A.get(y).vertex, 0))).weight;
                    Triangle tr = new Triangle(current.edge.vertex1, current.edge.vertex2, current.A.get(y).vertex, weight);
		    if (!this.Clique_4.containsKey(current.edge)) Clique_4.put(current.edge, new ArrayList<Clique4Value>());
	            Clique_4.get(current.edge).add(new Clique4Value(tr,vertex));
		    //Check if triangle is one of the k-heaviest, if it is added
		    //the k-heaviest that currently have been found
                    if(Tl.size()< this.parameters.Size && !Tl.contains(tr)){
                        Tl.put(tr);
                    }
                    else if(Tl.peek().weight < tr.weight && !Tl.contains(tr)){
                        Tl.poll();
                        Tl.put(tr);
                    }
		    if(this.parameters.debug && (this.ht == null || tr.weight > this.ht.weight))this.ht=tr;
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
		    if(this.parameters.L.containsKey(vertexA)) posL = this.parameters.L.get(vertexA).indexOf(new Tupple(vertexB, 0));
		    if(posL == -1 && this.parameters.HS.containsKey(vertexA)) posH = this.parameters.HS.get(vertexA).indexOf(new Tupple(vertexB, 0));
	    	    if(posL != -1) tmp = this.parameters.L.get(vertexA).get(posL);
	            else if(posH != -1) tmp = this.parameters.HS.get(vertexA).get(posH);
		    if(tmp != null) {
                var c4vy = arr.get(y);
			    Triangle t1 = arr.get(i).triangle;
			    Triangle t2 = c4vy.triangle;
			    Clique4 clique = new Clique4(t1, t2, new Edge(arr.get(y).vertex, tmp.vertex, tmp.weight), t1.weight+t2.weight+tmp.weight-key.weight);
                if (!this.Clique_5.containsKey(t1)) Clique_5.put(t1, new ArrayList<Clique5Value>());
	            Clique_5.get(t1).add(new Clique5Value(clique,c4vy.vertex));
			    if(C4.size()< this.parameters.Size && !C4.contains(clique)){
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

    private void clique5(){
        if(this.Clique_5 == null) return;
       this.Clique_5.forEach((triangle, clique5v) -> {
            clique5v.stream().forEach(c5v->{
                clique5v.stream().forEach(c5v2->{
                    if(c5v.equals(c5v2)) return;
                    int posL= -1;
                    int posH=-1;
                    Tupple tmp=null;
                    if(this.parameters.L.containsKey(c5v.vertex)) posL = this.parameters.L.get(c5v.vertex).indexOf(new Tupple(c5v2.vertex, 0));
                    if(posL == -1 && this.parameters.HS.containsKey(c5v.vertex)) posH = this.parameters.HS.get(c5v.vertex).indexOf(new Tupple(c5v2.vertex, 0));
	    	    if(posL != -1) tmp = this.parameters.L.get(c5v.vertex).get(posL);
	            else if(posH != -1) tmp = this.parameters.HS.get(c5v.vertex).get(posH);
                if(tmp != null) {
                    Clique4 c1 = c5v.clique4;
                    Clique4 c2 = c5v2.clique4;
                    Clique5 clique = new Clique5(c1, c2, new Edge(c5v2.vertex, tmp.vertex, tmp.weight), c1.weight+c2.weight+tmp.weight-triangle.weight);
                    if(C5.size()< this.parameters.Size && !C5.contains(clique)){
                            C5.put(clique);
                        }
                        else if(C4.peek().weight < clique.weight && !C4.contains(clique)){
                            C5.poll();
                            C5.put(clique);
                        }
                    this.C5.add(clique);
                }
                });
            });
       });
    }

    public void run() {
        this.Triangles();
	if(this.parameters.cliqueSize>3)this.clique4();
    if(this.parameters.cliqueSize>4)this.clique5();
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

class Clique5Value{
	Clique4 clique4;
	int vertex;

	public Clique5Value(Clique4 clique4, int vertex){
		this.clique4 = clique4;
		this.vertex = vertex;
	}
}