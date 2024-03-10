package legacy;

import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
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
    ArrayList<Triangle> triangles;
    static int counter = 0;
    HashSet<Triangle> Tl;
    Triangle ht = null;
    Parameters parameters;
    Triangle[] triangle_array;

    public FindTriangles(Parameters parameters){
        this.parameters = parameters;
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
	    if(this.parameters.mode ==0 && this.parameters.HS.containsKey(current.vertex1) && this.parameters.HS.containsKey(current.vertex2)){
	        ArrayList<Tupple> v1 = new ArrayList<Tupple>(this.parameters.HS.get(current.vertex1));
		ArrayList<Tupple> v2 = new ArrayList<Tupple>(this.parameters.HS.get(current.vertex2));
		if(v1.size() < v2.size()){
                    //HashSet<Tupple> h = new HashSet<Tupple>(v2);
                    v1.retainAll(v2);
                    for(int y=0;y<v1.size();y++){
                        Tupple a = v1.get(y);
                        Tupple b = v2.get(v2.indexOf(a));
                        Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                        Tl.add(tr);
			if(this.parameters.cliqueSize > 3){
			    if (!this.parameters.Clique_4.containsKey(current)) this.parameters.Clique_4.put(current, Collections.synchronizedList(new ArrayList<Clique4Value>()));
	            	    this.parameters.Clique_4.get(current).add(new Clique4Value(tr,a.vertex));
		    	}
                    }
                }
                else{
                    //HashSet<Tupple> h = new HashSet<Tupple>(v1);
                    v2.retainAll(v1);
                    for(int y=0;y<v2.size();y++){
                        Tupple a = v2.get(y);
                        Tupple b = v1.get(v1.indexOf(a));
                        Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                        Tl.add(tr);
		 	if(this.parameters.cliqueSize > 3){
			    if (!this.parameters.Clique_4.containsKey(current)) this.parameters.Clique_4.put(current, Collections.synchronizedList(new ArrayList<Clique4Value>()));
                            this.parameters.Clique_4.get(current).add(new Clique4Value(tr,a.vertex));
                        }
                    }
                }
	    }
	    if(this.parameters.mode ==0 &&this.parameters.HS.containsKey(current.vertex1) && this.parameters.L.containsKey(current.vertex2)){
                ArrayList<Tupple> v1 = new ArrayList<Tupple>(this.parameters.HS.get(current.vertex1));
                ArrayList<Tupple> v2 = new ArrayList<Tupple>(this.parameters.L.get(current.vertex2));
		if(v1.size() < v2.size()){
                    //HashSet<Tupple> h = new HashSet<Tupple>(v2);
                    v1.retainAll(v2);
                    for(int y=0;y<v1.size();y++){
                        Tupple a = v1.get(y);
                        Tupple b = v2.get(v2.indexOf(a));
                        Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                        Tl.add(tr);
			if(this.parameters.cliqueSize > 3){
                            if (!this.parameters.Clique_4.containsKey(current)) this.parameters.Clique_4.put(current, Collections.synchronizedList(new ArrayList<Clique4Value>()));
                            this.parameters.Clique_4.get(current).add(new Clique4Value(tr,a.vertex));
                        }
                    }
                }
                else{
                    //HashSet<Tupple> h = new HashSet<Tupple>(v1);
                    v2.retainAll(v1);
                    for(int y=0;y<v2.size();y++){
                        Tupple a = v2.get(y);
                        Tupple b = v1.get(v1.indexOf(a));
                        Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                        Tl.add(tr);
			if(this.parameters.cliqueSize > 3){
                            if (!this.parameters.Clique_4.containsKey(current)) this.parameters.Clique_4.put(current, Collections.synchronizedList(new ArrayList<Clique4Value>()));
                            this.parameters.Clique_4.get(current).add(new Clique4Value(tr,a.vertex));
                        }
                    }
                }
            }
	    if(this.parameters.mode ==0 &&this.parameters.HS.containsKey(current.vertex2) && this.parameters.L.containsKey(current.vertex1)){
                ArrayList<Tupple> v1 = new ArrayList<Tupple>(this.parameters.HS.get(current.vertex2));
                ArrayList<Tupple> v2 = new ArrayList<Tupple>(this.parameters.L.get(current.vertex1));
		if(v1.size() < v2.size()){
                    //HashSet<Tupple> h = new HashSet<Tupple>(v2);
                    v1.retainAll(v2);
                    for(int y=0;y<v1.size();y++){
                        Tupple a = v1.get(y);
                        Tupple b = v2.get(v2.indexOf(a));
                        Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                        Tl.add(tr);
			if(this.parameters.cliqueSize > 3){
                            if (!this.parameters.Clique_4.containsKey(current)) this.parameters.Clique_4.put(current, Collections.synchronizedList(new ArrayList<Clique4Value>()));
                            this.parameters.Clique_4.get(current).add(new Clique4Value(tr,a.vertex));
                        }
                    }
                }
                else{
                    //HashSet<Tupple> h = new HashSet<Tupple>(v1);
                    v2.retainAll(v1);
                    for(int y=0;y<v2.size();y++){
                        Tupple a = v2.get(y);
                        Tupple b = v1.get(v1.indexOf(a));
                        Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                        Tl.add(tr);
			if(this.parameters.cliqueSize > 3){
                            if (!this.parameters.Clique_4.containsKey(current)) this.parameters.Clique_4.put(current, Collections.synchronizedList(new ArrayList<Clique4Value>()));
                            this.parameters.Clique_4.get(current).add(new Clique4Value(tr,a.vertex));
                        }
                    }
                }
            }
	    if(this.parameters.mode ==1 &&this.parameters.L.containsKey(current.vertex1) && this.parameters.L.containsKey(current.vertex2)){
                ArrayList<Tupple> v1 = new ArrayList<Tupple>(this.parameters.L.get(current.vertex1));
                ArrayList<Tupple> v2 = new ArrayList<Tupple>(this.parameters.L.get(current.vertex2));
		if(v1.size() < v2.size()){
                    //HashSet<Tupple> h = new HashSet<Tupple>(v2);
                    v1.retainAll(v2);
                    for(int y=0;y<v1.size();y++){
                        Tupple a = v1.get(y);
                        Tupple b = v2.get(v2.indexOf(a));
                        Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                        Tl.add(tr);
			if(this.parameters.cliqueSize > 3){
                            if (!this.parameters.Clique_4.containsKey(current)) this.parameters.Clique_4.put(current, Collections.synchronizedList(new ArrayList<Clique4Value>()));
                            this.parameters.Clique_4.get(current).add(new Clique4Value(tr,a.vertex));
                        }
                    }
                }
                else{
                    //HashSet<Tupple> h = new HashSet<Tupple>(v1);
                    v2.retainAll(v1);
                    for(int y=0;y<v2.size();y++){
                        Tupple a = v2.get(y);
                        Tupple b = v1.get(v1.indexOf(a));
                        Triangle tr = new Triangle(current.vertex1, current.vertex2, a.vertex, current.weight+a.weight+b.weight);
                        Tl.add(tr);
			if(this.parameters.cliqueSize > 3){
                            if (!this.parameters.Clique_4.containsKey(current)) this.parameters.Clique_4.put(current, Collections.synchronizedList(new ArrayList<Clique4Value>()));
                            this.parameters.Clique_4.get(current).add(new Clique4Value(tr,a.vertex));
                        }
                    }
                }
            }
        }
        this.triangle_array = new Triangle[Tl.size()];
        this.triangle_array = Tl.toArray(this.triangle_array);
        Arrays.sort(this.triangle_array, new Comparator<Triangle>() {
            @Override
            public int compare(Triangle lhs, Triangle rhs) {
                return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
                }
            });
        for(int i=0;i<this.triangle_array.length;i++){
            if(this.parameters.T.size() < this.parameters.Size && this.parameters.TSet.add(this.triangle_array[i])){
		this.parameters.T.put(this.triangle_array[i]);
	    }
            else if(this.parameters.T.peek().weight < this.triangle_array[i].weight && this.parameters.TSet.add(this.triangle_array[i])){
            this.parameters.TSet.remove(this.parameters.T.poll());
		this.parameters.T.put(this.triangle_array[i]);
            }
            else if(this.parameters.T.peek().weight >= this.triangle_array[i].weight && this.parameters.T.size() >= this.parameters.Size) break;
	}
	//System.out.println(Arrays.toString(this.triangle_array));
    }

    public void run() {
        this.Triangles();
    }
}
