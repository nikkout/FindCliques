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
import java.io.BufferedReader;
import java.io.FileReader;


public class Main{

    HashMap<Integer, ArrayList<Tupple>> L;
    HashMap<Integer, ArrayList<Tupple>> H;
    HashMap<Integer, ArrayList<Tupple>> HS;
    HashMap<Integer, ArrayList<Tupple>> S;
    ArrayList<Edge> array;
    PriorityBlockingQueue<Triangle> T;
    Set<Triangle> TSet;
    PriorityBlockingQueue<Clique4> C4;
    int tnum;
    int threads;
    int Size;
    int cliqueSize;
    Triangle ht = null;
    boolean debug = true;
    
    public static void main(String Args[]){
        if(Args.length <5){
            System.out.println("Usage findCliques.jar <edges to read for each itteration> <threads> <find heaviest edges> <filename> <Clique size 3|4>");
            return;
        }
        Main m = new Main(Args);
        m.read(Args[3]);
        System.out.println(FindTriangles.counter);
	Triangle[] triangles = new Triangle[m.T.size()];
	triangles = m.T.toArray(triangles);
	Arrays.sort(triangles, new Comparator<Triangle>() {
            @Override
            public int compare(Triangle lhs, Triangle rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
            }
        });
	//System.out.println(Arrays.toString(triangles));
	for(int i=0;i<15;i++){
	    System.out.println(triangles[i]);
	}
    }
    
    Main(String Args[]){
        this.tnum = Integer.parseInt(Args[0]);
        this.threads = Integer.parseInt(Args[1]);
        this.Size = Integer.parseInt(Args[2])+1;
	this.cliqueSize = Integer.parseInt(Args[4]);
	if(this.cliqueSize <3 || this.cliqueSize > 4){
		System.out.println("Unsupported clique size: "+this.cliqueSize);
		return;
	}
        L = new HashMap<Integer, ArrayList<Tupple>>();
        H = new HashMap<Integer, ArrayList<Tupple>>();
        HS = new HashMap<Integer, ArrayList<Tupple>>();
        S = new HashMap<Integer, ArrayList<Tupple>>();
        array = new ArrayList<Edge>();
        T = new PriorityBlockingQueue<Triangle>(this.Size, new Comparator<Triangle>() {
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
        this.TSet = Collections.synchronizedSet(new HashSet<Triangle>(this.Size));
	    //C4 = Collections.synchronizedSet(new HashSet<Clique4>(10000));
    }

    void read(String fname){
        int tmp1=0, tmp2=0;
        double tmp3=0;
        int counter = 0;
        try{
            //Scanner scanner = new Scanner(new File(fname));
	    BufferedReader br = new BufferedReader(new FileReader(new File(fname)));
            String st;
	    br.readLine();
	    //while(scanner.hasNextInt()){
            while ((st = br.readLine()) != null){
                counter++;
                //if(counter % 100000 == 0)System.out.println(counter);
                //Read a line
                String[] tmp_arr = st.split(" ");
                tmp1 = Integer.parseInt(tmp_arr[0]);
                tmp2 = Integer.parseInt(tmp_arr[1]);
                tmp3 = Integer.parseInt(tmp_arr[2]);
                if(!this.L.containsKey(tmp1)){
                    this.L.put(tmp1, new ArrayList<Tupple>());
                }
                this.L.get(tmp1).add(new Tupple(tmp2, tmp3));
                if(!this.L.containsKey(tmp2)){
                    this.L.put(tmp2, new ArrayList<Tupple>());
                }
                this.L.get(tmp2).add(new Tupple(tmp1, tmp3));
                Edge tmp = new Edge(tmp1, tmp2, tmp3);
                this.array.add(tmp);
            }
        }
        catch(Exception e){e.printStackTrace();}       
        Collections.sort(array, new Comparator<Edge>() {
            @Override
            public int compare(Edge lhs, Edge rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
            }
        });
        System.out.println(this.tnum+" "+this.threads+" "+this.Size+" ----------    -----------");
	long start = System.currentTimeMillis();
        this.findTriangles(this.tnum, this.threads, this.Size);
	System.out.println("Millis: "+(System.currentTimeMillis()-start));
    }
    
    void findTriangles(int tnum, int threads, int Size){
        int limit =0;
        int l=-1;
        int h=-1;
        double ar = 1.5;
        double r = 0;
        double d = 0;
        int p =1;
	double threshold = 0;
	Weighted currentPeek = null;
	int currentSize = 0;
        while(currentPeek == null || currentPeek.get_weight() < threshold || currentSize<this.Size){//l<array.size()-1){
            if(array.get(l+1).weight > Math.pow(array.get(h+1).weight, ar) || l==h){
                //find triangles with edge l+1 and two edges in S and H
                limit = tnum;
                if(array.size()-l <= tnum) limit = array.size()-l-1;
                ArrayList<E> e1 = new ArrayList<E>();
                ArrayList<E> e2 = new ArrayList<E>();
                for(int i=0;i<limit;i++){
                    if (HS.containsKey(array.get(l+1).vertex1) && HS.containsKey(array.get(l+1).vertex2))e1.add(new E(array.get(l+1), HS.get(array.get(l+1).vertex1), HS.get(array.get(l+1).vertex2)));
                    if (HS.containsKey(array.get(l+1).vertex1) && L.containsKey(array.get(l+1).vertex2))e2.add(new E(array.get(l+1), HS.get(array.get(l+1).vertex1), L.get(array.get(l+1).vertex2)));
		    if (HS.containsKey(array.get(l+1).vertex2) && L.containsKey(array.get(l+1).vertex1))e2.add(new E(array.get(l+1), HS.get(array.get(l+1).vertex2), L.get(array.get(l+1).vertex1)));
 
		    move(L, H, array, l);
                    add(HS, array, l);
                    l+=1;
                }
                
                findTriangles(e1, threads, Size);
                findTriangles(e2, threads, Size);
            }
            else{
                limit = l-h;
                if(limit > tnum)limit = tnum;
                ArrayList<E> e = new ArrayList<E>();
                for(int i=0;i<limit;i++){
                    if (L.containsKey(array.get(h+1).vertex1) && L.containsKey(array.get(h+1).vertex2))e.add(new E(array.get(h+1), L.get(array.get(h+1).vertex1), L.get(array.get(h+1).vertex2)));
                    move(H, S, array, h);
                    h+=1;
                }
                findTriangles(e, threads, Size);
            }
            if(h!= -1)r = Math.pow((double)(array.get(h).weight), p)+2*Math.pow((float)(array.get(l).weight), p);
            else r = Math.pow((double)(array.get(0).weight), p)+2*Math.pow((float)(array.get(l).weight), p);
            d = 2*r+array.get(l).weight;
	    if(this.cliqueSize == 3){
                threshold = r;
                currentSize = this.T.size();
                currentPeek = this.T.peek();
            }
            else {
                threshold = d;
                currentSize = this.C4.size();
                currentPeek = this.C4.peek();
       	    }
            //System.out.println(T.size());
            //System.out.println("R: "+r);
            //if(T.peek() != null)System.out.println(T.peek().weight);
            //System.out.println(C4.size());
            //System.out.println("D: "+d);
            //if(C4.peek() != null)System.out.println(C4.peek().weight);
            //if(currentPeek != null)System.out.println(currentPeek.get_weight());
        }
        System.out.println(T.size());
	System.out.println(C4.size());
	    /*Clique4[] ca = new Clique4[C4.size()];
	    ca = C4.toArray(ca);
	    Triangle[] ta = new Triangle[T.size()];
	    ta = T.toArray(ta);
	    //List al = Arrays.asList(ta);
	    List al = Arrays.asList(ca);
	    Collections.sort(al, new Comparator<Clique4>() {
            @Override
            public int compare(Clique4 lhs, Clique4 rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
            }
        });
	    //for(int i=0;i<=20;i++)System.out.println(al.get(i));
	    for(int i=0;i<20;i++)System.out.println(al.get(i));*/
    }
    
    void move(HashMap<Integer, ArrayList<Tupple>> rm, HashMap<Integer, ArrayList<Tupple>> add, ArrayList<Edge> array, int l){
        Edge tmp = array.get(l+1);
	rm.get(tmp.vertex1).remove(new Tupple(tmp.vertex2, 0));
        rm.get(tmp.vertex2).remove(new Tupple(tmp.vertex1, 0));
        if(rm.get(tmp.vertex1).size()==0)rm.remove(tmp.vertex1);
        if(rm.get(tmp.vertex2).size()==0)rm.remove(tmp.vertex2);
        if(!add.containsKey(tmp.vertex1))add.put(tmp.vertex1, new ArrayList<Tupple>());
        add.get(tmp.vertex1).add(new Tupple(tmp.vertex2, tmp.weight));
        if(!add.containsKey(tmp.vertex2))add.put(tmp.vertex2, new ArrayList<Tupple>());
        add.get(tmp.vertex2).add(new Tupple(tmp.vertex1, tmp.weight));
    }

    void add(HashMap<Integer, ArrayList<Tupple>> add, ArrayList<Edge> array, int l){
        Edge tmp = array.get(l+1); 
        if(!add.containsKey(tmp.vertex1))add.put(tmp.vertex1, new ArrayList<Tupple>());
        add.get(tmp.vertex1).add(new Tupple(tmp.vertex2, tmp.weight));
        if(!add.containsKey(tmp.vertex2))add.put(tmp.vertex2, new ArrayList<Tupple>());
        add.get(tmp.vertex2).add(new Tupple(tmp.vertex1, tmp.weight));
    }
    
    void findTriangles(ArrayList<E> e, int threads, int Size){
        int size = e.size();
        int rem = size%threads;
        size /=threads;
        Thread[] threadsArray = new Thread[threads];
        FindTriangles[] ft = new FindTriangles[threads];
        E[] arrayE = new E[e.size()];
        arrayE = e.toArray(arrayE);
        for(int i=0;i<threads;i++){
            if(i==threads-1) ft[i] = new FindTriangles(new Parameters(arrayE, i*size, (i+1)*size+rem, Size, this.L, this.HS, this.cliqueSize, this.debug, this.T, this.TSet));
            else ft[i] = new FindTriangles(new Parameters(arrayE, i*size, (i+1)*size, Size, this.L, this.HS,this.cliqueSize, this.debug, this.T, this.TSet));
            Thread thread = new Thread(ft[i]);
            thread.start();
            threadsArray[i] = thread;
        }
        try{
            for(int i=0;i<threads;i++){
                threadsArray[i].join();
                //Triangle[] trs = new Triangle[ft[i].T.size()];
                //trs = ft[i].T.toArray(trs);
                //for(int y=0;y<trs.length;y++){
		//    this.T.add(trs[y]);
		//}
		int y = 0;
		/*while(ft[i].triangle_array.length>y){
                        Triangle tmp = ft[i].triangle_array[y];
                        if(this.T.size() >= this.Size && this.T.peek().weight > tmp.weight) break;
                        if(this.T.size() < this.Size && !T.contains(tmp)) this.T.put(tmp);
                        else if(!T.contains(tmp)){
                            this.T.poll();
                            this.T.put(tmp);
                        }
			if(this.debug && ( this.ht == null || tmp.weight > this.ht.weight)) this.ht = tmp;
			y++;
                    }*/
            	while(this.cliqueSize>3){
                        Clique4 tmp = ft[i].C4.poll();
                        if(tmp == null) break;
                        if(this.C4.size() < this.Size && !C4.contains(tmp)) this.C4.put(tmp);
                        else if(this.C4.peek().weight < tmp.weight && !C4.contains(tmp)){
                            this.C4.poll();
                            this.C4.put(tmp);
                        }
                    }
            }
            //System.out.println("Threads finished.");
        }
        catch(Exception exc){exc.printStackTrace();}
    }

}

interface Weighted{
	double get_weight();
}
