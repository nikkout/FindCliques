import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
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
    HashMap<Edge, ArrayList<Clique4Value>> Clique_4;
    ArrayList<Triangle> triangles;
    static int counter = 0;
    HashSet<Triangle> Tl;
    Triangle ht = null;
    Parameters parameters;
    Triangle[] triangle_array;

    public FindTriangles(Parameters parameters){
        this.parameters = parameters;
	this.Clique_4 = new HashMap<Edge, ArrayList<Clique4Value>>();
	this.triangles = new ArrayList<Triangle>();
        FindTriangles.counter++;
	Tl = new HashSet<Triangle>();
        /*Tl = new PriorityBlockingQueue<Triangle>(this.parameters.Size, new Comparator<Triangle>() {
            @Override
            public int compare(Triangle lhs, Triangle rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? 1 : (lhs.weight < rhs.weight) ? -1 : 0;
            }
        });*/
        C4 = new PriorityBlockingQueue<Clique4>(this.parameters.Size, new Comparator<Clique4>() {
            @Override
            public int compare(Clique4 lhs, Clique4 rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? 1 : (lhs.weight < rhs.weight) ? -1 : 0;
            }
        });
    }

    private void Triangles(){
	double min = 100000;
        for(int i=this.parameters.start;i<this.parameters.end;i++){
            Edge current = parameters.e[i];
	    /*if(!this.parameters.L.containsKey(current.vertex1))continue;
	    ArrayList<Tupple> v1 = this.parameters.L.get(current.vertex1);
	    for(int y=0;y<v1.size();y++){
	        Tupple c = v1.get(y);
		if(c.weight <= current.weight && current.vertex2 != c.vertex && this.parameters.L.containsKey(c.vertex)){
		    ArrayList<Tupple> v2 = this.parameters.L.get(c.vertex);
		    Tupple tmp = new Tupple(current.vertex2, 0);
		    if(v2.contains(tmp)){
		        //find Triangle
		        Triangle tr = new Triangle(current.vertex1, current.vertex2, c.vertex, current.weight+c.weight+v2.get(v2.indexOf(tmp)).weight);
			Tl.add(tr);
                    	/*if(Tl.size()< this.parameters.Size){
                             Tl.add(tr);
                             if(min > tr.weight) min = tr.weight;
                    	}
                        else if(min < tr.weight){
                             Tl.add(tr);
                        }
                    }
		}
	    }*/
	    if(this.parameters.mode ==0 && this.parameters.HS.containsKey(current.vertex1) && this.parameters.HS.containsKey(current.vertex2)){
	        ArrayList<Tupple> v1 = new ArrayList<Tupple>(this.parameters.HS.get(current.vertex1));
		ArrayList<Tupple> v2 = new ArrayList<Tupple>(this.parameters.HS.get(current.vertex2));
		v1.retainAll(this.parameters.HS.get(current.vertex2));
		for(int y=0;y<v1.size();y++){
		    Tupple a = v1.get(y);
		    Tupple b = v2.get(v2.indexOf(a));
		    Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                    Tl.add(tr);
		}
	    }
	    if(this.parameters.mode ==0 &&this.parameters.HS.containsKey(current.vertex1) && this.parameters.L.containsKey(current.vertex2)){
                ArrayList<Tupple> v1 = new ArrayList<Tupple>(this.parameters.HS.get(current.vertex1));
                ArrayList<Tupple> v2 = new ArrayList<Tupple>(this.parameters.L.get(current.vertex2));
                v1.retainAll(v2);
                v2.retainAll(v1);
                for(int y=0;y<v1.size();y++){
                    Tupple a = v1.get(y);
                    Tupple b = v2.get(v2.indexOf(a));
                    Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                    Tl.add(tr);
                }
            }
	    if(this.parameters.mode ==0 &&this.parameters.HS.containsKey(current.vertex2) && this.parameters.L.containsKey(current.vertex1)){
                ArrayList<Tupple> v1 = new ArrayList<Tupple>(this.parameters.HS.get(current.vertex2));
                ArrayList<Tupple> v2 = new ArrayList<Tupple>(this.parameters.L.get(current.vertex1));
                v1.retainAll(v2);
                for(int y=0;y<v1.size();y++){
                    Tupple a = v1.get(y);
                    Tupple b = v2.get(v2.indexOf(a));
                    Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                    Tl.add(tr);
                }
            }
	    if(this.parameters.mode ==1 &&this.parameters.L.containsKey(current.vertex1) && this.parameters.L.containsKey(current.vertex2)){
                ArrayList<Tupple> v1 = new ArrayList<Tupple>(this.parameters.L.get(current.vertex1));
                ArrayList<Tupple> v2 = new ArrayList<Tupple>(this.parameters.L.get(current.vertex2));
                v1.retainAll(v2);
                for(int y=0;y<v1.size();y++){
                    Tupple a = v1.get(y);
                    Tupple b = v2.get(v2.indexOf(a));
                    Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                    Tl.add(tr);
                }
            }
        }
        this.triangle_array = new Triangle[Tl.size()];
        this.triangle_array = Tl.toArray(this.triangle_array);
        Arrays.sort(this.triangle_array, new Comparator<Triangle>() {
            @Override
            public int compare(Triangle lhs, Triangle rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
                }
            });
        for(int i=0;i<this.triangle_array.length;i++){
            if(this.parameters.T.size() < this.parameters.Size && !this.parameters.TSet.contains(this.triangle_array[i])){
		this.parameters.T.put(this.triangle_array[i]);
		this.parameters.TSet.add(this.triangle_array[i]);
	    }
            else if(this.parameters.T.peek().weight < this.triangle_array[i].weight && !this.parameters.TSet.contains(this.triangle_array[i])){
	        this.parameters.TSet.remove(this.parameters.T.poll());
		this.parameters.T.put(this.triangle_array[i]);
		this.parameters.TSet.add(this.triangle_array[i]);
            }
            else if(this.parameters.T.peek().weight >= this.triangle_array[i].weight && this.parameters.T.size() >= this.parameters.Size) break;
	}
	//System.out.println(Arrays.toString(this.triangle_array));
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
			    Triangle t1 = arr.get(i).triangle;
			    Triangle t2 = arr.get(y).triangle;
			    Clique4 clique = new Clique4(t1, t2, new Edge(arr.get(y).vertex, tmp.vertex, tmp.weight), t1.weight+t2.weight+tmp.weight-key.weight);
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

    public void run() {
        this.Triangles();
	if(this.parameters.cliqueSize>3)this.clique4();
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
