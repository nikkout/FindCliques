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



public class Main{

    HashMap<Integer, ArrayList<Tupple>> L;
    HashMap<Integer, ArrayList<Tupple>> H;
    HashMap<Integer, ArrayList<Tupple>> HS;
    HashMap<Integer, ArrayList<Tupple>> S;
    ArrayList<Edge> array;
    //PriorityBlockingQueue<Triangle> T;
    Set<Triangle> T;
    Set<Clique4> C4;
    int tnum;
    int threads;
    int Tsize;
    
    public static void main(String Args[]){
        if(Args.length <4){
            System.out.println("Usage findCliques.jar <edges to read for each itteration> <threads> <find heaviest edges> <filename>");
            return;
        }
        Main m = new Main(Args);
        m.read(Args[3]);
    }
    
    Main(String Args[]){
        this.tnum = Integer.parseInt(Args[0]);
        this.threads = Integer.parseInt(Args[1]);
        this.Tsize = Integer.parseInt(Args[2]);
        L = new HashMap<Integer, ArrayList<Tupple>>();
        H = new HashMap<Integer, ArrayList<Tupple>>();
        HS = new HashMap<Integer, ArrayList<Tupple>>();
        S = new HashMap<Integer, ArrayList<Tupple>>();
        array = new ArrayList<Edge>();
        /*T = new PriorityBlockingQueue<Triangle>(10000, new Comparator<Triangle>() {
            @Override
            public int compare(Triangle lhs, Triangle rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
            }
        });*/
        T = Collections.synchronizedSet(new HashSet<Triangle>(100000));
	    C4 = Collections.synchronizedSet(new HashSet<Clique4>(10000));
    }

    void read(String fname){
        int tmp1=0, tmp2=0;
        double tmp3=0;
        try{
            Scanner scanner = new Scanner(new File(fname));
            while(scanner.hasNextInt()){
                //Read a line
                tmp1 = scanner.nextInt();
                tmp2 = scanner.nextInt();
                tmp3 = scanner.nextDouble();
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
        
        System.out.println(this.tnum+" "+this.threads+" "+this.Tsize+" ------------------------------------------------------------");
        this.findTriangles(this.tnum, this.threads, this.Tsize);
    }
    
    void findTriangles(int tnum, int threads, int Tsize){
        int limit =0;
        int l=-1;
        int h=-1;
        double ar = 1.5;
        while(l<array.size()-1){
            if(array.get(l+1).weight > Math.pow(array.get(h+1).weight, ar) || l==h){
                //find triangles with edge l+1 and two edges in S and H
                limit = tnum;
                if(array.size()-l <= tnum) limit = array.size()-l-1;
                ArrayList<E> e1 = new ArrayList<E>();
                ArrayList<E> e2 = new ArrayList<E>();
                for(int i=0;i<limit;i++){
                    if (HS.containsKey(array.get(l+1).vertex1) && HS.containsKey(array.get(l+1).vertex2))e1.add(new E(array.get(l+1), HS.get(array.get(l+1).vertex1), HS.get(array.get(l+1).vertex2)));
                    if (HS.containsKey(array.get(l+1).vertex1) && L.containsKey(array.get(l+1).vertex2))e2.add(new E(array.get(l+1), HS.get(array.get(l+1).vertex1), L.get(array.get(l+1).vertex2)));
                    move(L, H, array, l);
                    add(HS, array, l);
                    l+=1;
                }
                
                findTriangles(e1, threads, Tsize);
                findTriangles(e2, threads, Tsize);
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
                findTriangles(e, threads, Tsize);
            }
            //if(h!= -1)r = pow(float(array[h][2]), p)+2*pow(float(array[l][2]), p);
            //else r = pow(float(array[0][2]), p)+2*pow(float(array[l][2]), p);
        }
        System.out.println(T.size());
	System.out.println(C4.size());
    }
    
    void move(HashMap<Integer, ArrayList<Tupple>> rm, HashMap<Integer, ArrayList<Tupple>> add, ArrayList<Edge> array, int l){
        Edge tmp = array.get(l+1); 
        rm.remove(tmp.vertex1);
        rm.remove(tmp.vertex2);
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
    
    void findTriangles(ArrayList<E> e, int threads, int Tsize){
        int size = e.size();
        int rem = size%threads;
        size /=threads;
        Thread[] threadsArray = new Thread[threads];
        E[] arrayE = new E[e.size()];
        arrayE = e.toArray(arrayE);
        for(int i=0;i<threads;i++){
            FindTriangles ft;
            if(i==threads-1) ft = new FindTriangles(arrayE, T, C4, i*size, (i+1)*size+rem, Tsize, this.L, this.HS);
            else ft = new FindTriangles(arrayE, T, C4, i*size, (i+1)*size, Tsize, this.L, this.HS);
            Thread thread = new Thread(ft);
            thread.start();
            threadsArray[i] = thread;
        }
        try{
            for(int i=0;i<threads;i++)threadsArray[i].join();
        }
        catch(Exception exc){exc.printStackTrace();}
    }

}
