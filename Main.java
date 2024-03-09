import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.lang.Math;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    HashMap<Integer, ArrayList<Tupple>> L;
    HashMap<Integer, ArrayList<Tupple>> H;
    HashMap<Integer, ArrayList<Tupple>> HS;
    HashMap<Integer, ArrayList<Tupple>> S;
    ArrayList<Edge> array;
    PriorityBlockingQueue<Triangle> T;
    Set<Triangle> TSet;
    PriorityBlockingQueue<Clique4> C4;
    Set<Clique4> C4Set;
    Map<Edge, List<Clique4Value>> Clique_4;
    PriorityBlockingQueue<Clique5> C5;
    Set<Clique5> C5Set;
    Map<Triangle, List<Clique5Value>> Clique_5;
    int tnum;
    int tnumexpensive;
    int threads;
    int Size;
    int cliqueSize;
    Triangle ht = new Triangle(0, 0, 0, 0);
    boolean debug = true;
    double ar = 1;

    public static void main(String Args[]) {
        long t1 = System.currentTimeMillis();
        if (Args.length < 6) {
            System.out.println(
                    "Usage findCliques.jar <edges to read for each itteration> <threads> <find heaviest edges> <filename> <Clique size 3|4|5> <ar value> <edges to read expensive>");
            return;
        }
        Main m = new Main(Args);
        m.read(Args[3]);
        System.out.println(FindTriangles.counter);
        if (m.cliqueSize == 3) {
            Triangle[] triangles = new Triangle[m.T.size()];
            triangles = m.T.toArray(triangles);
            Arrays.sort(triangles, new Comparator<Triangle>() {
                @Override
                public int compare(Triangle lhs, Triangle rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
                }
            });
            // System.out.println(Arrays.toString(triangles));
            for (int i = 0; i < 20; i++) {
                System.out.println(triangles[i]);
            }
        } else if (m.cliqueSize == 4) {
            Clique4[] cliques = new Clique4[m.C4.size()];
            cliques = m.C4.toArray(cliques);
            Arrays.sort(cliques, new Comparator<Clique4>() {
                @Override
                public int compare(Clique4 lhs, Clique4 rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
                }
            });
            // System.out.println(Arrays.toString(triangles));
            for (int i = 0; i < 10; i++) {
                System.out.println(cliques[i]);
            }
            System.out.println("!!!! " + cliques.length);
        }
        System.out.println("Total time in millis: " + (System.currentTimeMillis() - t1));
    }

    Main(String Args[]) {
        this.tnum = Integer.parseInt(Args[0]);
        this.threads = Integer.parseInt(Args[1]);
        this.Size = Integer.parseInt(Args[2]) + 1;
        this.cliqueSize = Integer.parseInt(Args[4]);
        this.ar = Double.parseDouble(Args[5]);
        this.tnumexpensive = Integer.parseInt(Args[6]);
        if (this.cliqueSize < 3 || this.cliqueSize > 5) {
            System.out.println("Unsupported clique size: " + this.cliqueSize);
            return;
        }
        L = new HashMap<Integer, ArrayList<Tupple>>();
        H = new HashMap<Integer, ArrayList<Tupple>>();
        HS = new HashMap<Integer, ArrayList<Tupple>>();
        S = new HashMap<Integer, ArrayList<Tupple>>();
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
        C5 = new PriorityBlockingQueue<Clique5>(this.Size, new Comparator<Clique5>() {
            @Override
            public int compare(Clique5 lhs, Clique5 rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? 1 : (lhs.weight < rhs.weight) ? -1 : 0;
            }
        });
        this.TSet = Collections.synchronizedSet(new HashSet<Triangle>(this.Size));
        this.C4Set = Collections.synchronizedSet(new HashSet<Clique4>(this.Size));
        this.Clique_4 = Collections.synchronizedMap(new HashMap<Edge, List<Clique4Value>>());
        this.C5Set = Collections.synchronizedSet(new HashSet<Clique5>(this.Size));
        this.Clique_5 = Collections.synchronizedMap(new HashMap<Triangle, List<Clique5Value>>());
    }

    void read(String fname) {
        int tmp1 = 0, tmp2 = 0;
        double tmp3 = 0;
        int counter = 0;
        try {
            // Scanner scanner = new Scanner(new File(fname));
            BufferedReader br = new BufferedReader(new FileReader(new File(fname)));
            String st;
            st = br.readLine();
            String[] tmp_arr = st.split(" ");
            tmp1 = Integer.parseInt(tmp_arr[2]);
            this.array = new ArrayList<Edge>(tmp1);
            // while(scanner.hasNextInt()){
            while ((st = br.readLine()) != null) {
                counter++;
                // if(counter % 1000000 == 0)System.out.println(counter);
                // Read a line
                tmp_arr = st.split("\t");
                tmp1 = Integer.parseInt(tmp_arr[0]);
                tmp2 = Integer.parseInt(tmp_arr[1]);
                tmp3 = (int) Double.parseDouble(tmp_arr[2]);
                if (!this.L.containsKey(tmp1)) {
                    this.L.put(tmp1, new ArrayList<Tupple>());
                }
                this.L.get(tmp1).add(new Tupple(tmp2, tmp3));
                if (!this.L.containsKey(tmp2)) {
                    this.L.put(tmp2, new ArrayList<Tupple>());
                }
                this.L.get(tmp2).add(new Tupple(tmp1, tmp3));
                Edge tmp = new Edge(tmp1, tmp2, tmp3);
                this.array.add(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(array, new Comparator<Edge>() {
            @Override
            public int compare(Edge lhs, Edge rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
            }
        });
        System.out.println(this.tnum + " " + this.threads + " " + this.Size + " ----------    -----------");
        long start = System.currentTimeMillis();
        this.findTriangles(this.tnum, this.threads, this.Size);
        System.out.println("Millis: " + (System.currentTimeMillis() - start));
    }

    void findTriangles(int tnum, int threads, int Size) {
        int limit = 0;
        int l = -1;
        int h = -1;
        double r3 = 0;
        double r4 = 0;
        double r5 = 0;
        int p = 1;
        double threshold = 0;
        boolean pick = false;
        Weighted currentPeek = null;
        int currentSize = 0;
        while (currentPeek == null || currentPeek.get_weight() < threshold || currentSize < this.Size) {// l<array.size()-1){
            if (Math.pow(array.get(l + 1).weight, ar) > array.get(h + 1).weight || l <= h) {
                limit = tnum;
                if (array.size() - l <= tnum)
                    limit = array.size() - l - 1;
                ArrayList<Edge> e1 = new ArrayList<Edge>();
                ArrayList<Edge> e2 = new ArrayList<Edge>();
                for (int i = 0; i < limit; i++) {
                    e1.add(array.get(l + 1));
                    move(L, HS, array, l);
                    l += 1;
                }
                findTriangles(e1, threads, Size, 0);
            } else {
                pick = true;
                limit = l - h;
                System.out.println("H " + limit);
                if (limit > threads * tnumexpensive)
                    limit = threads * tnumexpensive;
                ArrayList<Edge> e = new ArrayList<Edge>();
                for (int i = 0; i < limit; i++) {
                    e.add(array.get(h + 1));
                    h += 1;
                }
                findTriangles(e, threads, Size, 1);

            }
            if (h != -1)
                r3 = Math.pow((double) (array.get(h).weight), p) + 2 * Math.pow((float) (array.get(l).weight), p);
            else
                r3 = Math.pow((double) (array.get(0).weight), p) + 2 * Math.pow((float) (array.get(l).weight), p);
            r4 = 3 * (array.get(h + 1).weight) + 3 * array.get(l).weight;
            r5 = 6 * (array.get(h + 1).weight) + 4 * array.get(l).weight;
            // if(!this.Clique_4.containsKey(array.get(l-limit+1)))d = 2*r;
            // else {
            // List<Clique4Value> c4vs = (List)this.Clique_4.get(array.get(h+1));
            // double max = 0;
            // for(int i=0;i<c4vs.size();i++){
            // Clique4Value c4v = c4vs.get(i);
            // if(c4v.triangle.weight > max) max = c4v.triangle.weight;
            // }
            // d = (r+ht.weight+array.get(h+1).weight-array.get(l).weight);
            // }
            if (this.cliqueSize == 5) {
                threshold = r4;
                currentSize = this.C4.size();
                currentPeek = this.C4.peek();
            } else if (this.cliqueSize == 4) {
                threshold = r4;
                currentSize = this.C4.size();
                currentPeek = this.C4.peek();
            } else {
                threshold = r3;
                currentSize = this.T.size();
                currentPeek = this.T.peek();
            }
            // System.out.println(T.size());
            // System.out.println("R: "+r);
            // if(T.peek() != null)System.out.println(T.peek().weight);
            // System.out.println(C4.size());
            // System.out.println("D: "+d);
            // if(C4.peek() != null)System.out.println(C4.peek().weight);
            // if(currentPeek != null)System.out.println(currentPeek.get_weight());
        }
        System.out.println("Triangles: " + T.size());
        System.out.println("C4: " + C4.size());
        System.out.println("C5: " + C5.size());
        /*
         * Clique4[] ca = new Clique4[C4.size()];
         * ca = C4.toArray(ca);
         * Triangle[] ta = new Triangle[T.size()];
         * ta = T.toArray(ta);
         * //List al = Arrays.asList(ta);
         * List al = Arrays.asList(ca);
         * Collections.sort(al, new Comparator<Clique4>() {
         * 
         * @Override
         * public int compare(Clique4 lhs, Clique4 rhs) {
         * // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
         * return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
         * }
         * });
         * //for(int i=0;i<=20;i++)System.out.println(al.get(i));
         * for(int i=0;i<20;i++)System.out.println(al.get(i));
         */
    }

    void move(HashMap<Integer, ArrayList<Tupple>> rm, HashMap<Integer, ArrayList<Tupple>> add, ArrayList<Edge> array,
            int l) {
        Edge tmp = array.get(l + 1);
        rm.get(tmp.vertex1).remove(new Tupple(tmp.vertex2, 0));
        rm.get(tmp.vertex2).remove(new Tupple(tmp.vertex1, 0));
        if (rm.get(tmp.vertex1).size() == 0)
            rm.remove(tmp.vertex1);
        if (rm.get(tmp.vertex2).size() == 0)
            rm.remove(tmp.vertex2);
        if (!add.containsKey(tmp.vertex1))
            add.put(tmp.vertex1, new ArrayList<Tupple>());
        add.get(tmp.vertex1).add(new Tupple(tmp.vertex2, tmp.weight));
        if (!add.containsKey(tmp.vertex2))
            add.put(tmp.vertex2, new ArrayList<Tupple>());
        add.get(tmp.vertex2).add(new Tupple(tmp.vertex1, tmp.weight));
    }

    void add(HashMap<Integer, ArrayList<Tupple>> add, ArrayList<Edge> array, int l) {
        Edge tmp = array.get(l + 1);
        if (!add.containsKey(tmp.vertex1))
            add.put(tmp.vertex1, new ArrayList<Tupple>());
        add.get(tmp.vertex1).add(new Tupple(tmp.vertex2, tmp.weight));
        if (!add.containsKey(tmp.vertex2))
            add.put(tmp.vertex2, new ArrayList<Tupple>());
        add.get(tmp.vertex2).add(new Tupple(tmp.vertex1, tmp.weight));
    }

    void remove(HashMap<Integer, ArrayList<Tupple>> rm, ArrayList<Edge> array, int l, int size) {
        for (int i = 1; i <= size; i++) {
            Edge tmp = array.get(l + i);
            rm.get(tmp.vertex1).remove(new Tupple(tmp.vertex2, 0));
            if (rm.get(tmp.vertex1).size() == 0)
                rm.remove(tmp.vertex1);
        }
    }

    void findTriangles(ArrayList<Edge> e, int threads, int Size, int mode) {
        int size = e.size();
        int rem = size % threads;
        size /= threads;
        Thread[] threadsArray = new Thread[threads];
        FindTriangles[] ft = new FindTriangles[threads];
        Edge[] arrayEdges = new Edge[e.size()];
        arrayEdges = e.toArray(arrayEdges);
        for (int i = 0; i < threads; i++) {
            if (i == threads - 1)
                ft[i] = new FindTriangles(new Parameters(arrayEdges, i * size, (i + 1) * size + rem, Size, this.L,
                        this.HS, this.cliqueSize, this.debug, this.T, this.TSet, mode, this.C4, this.C4Set,
                        this.Clique_4));
            else
                ft[i] = new FindTriangles(new Parameters(arrayEdges, i * size, (i + 1) * size, Size, this.L, this.HS,
                        this.cliqueSize, this.debug, this.T, this.TSet, mode, this.C4, this.C4Set, this.Clique_4));
            Thread thread = new Thread(ft[i]);
            thread.start();
            threadsArray[i] = thread;
        }
        try {
            for (int i = 0; i < threads; i++) {
                threadsArray[i].join();
                if (ft[i].triangle_array.length == 0)
                    continue;
                if (this.ht.weight < ft[i].triangle_array[0].weight)
                    this.ht = ft[i].triangle_array[0];
                /*
                 * while(this.cliqueSize>3){
                 * Clique4 tmp = ft[i].C4.poll();
                 * if(tmp == null) break;
                 * if(this.C4.size() < this.Size && !C4.contains(tmp)) this.C4.put(tmp);
                 * else if(this.C4.peek().weight < tmp.weight && !C4.contains(tmp)){
                 * this.C4.poll();
                 * this.C4.put(tmp);
                 * }
                 * }
                 */
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        if (this.cliqueSize > 3) {
            FindCliques4[] fq = new FindCliques4[threads];
            for (int i = 0; i < threads; i++) {
                if (i == threads - 1)
                    fq[i] = new FindCliques4(new Parameters(arrayEdges, i * size, (i + 1) * size + rem, Size, this.L,
                            this.HS, this.cliqueSize, this.debug, this.T, this.TSet, mode, this.C4, this.C4Set,
                            this.Clique_4, this.C5, this.C5Set, this.Clique_5));
                else
                    fq[i] = new FindCliques4(new Parameters(arrayEdges, i * size, (i + 1) * size, Size, this.L, this.HS,
                            this.cliqueSize, this.debug, this.T, this.TSet, mode, this.C4, this.C4Set, this.Clique_4,
                            this.C5, this.C5Set, this.Clique_5));
                Thread thread = new Thread(fq[i]);
                thread.start();
                threadsArray[i] = thread;
            }
            try {
                for (int i = 0; i < threads; i++) {
                    threadsArray[i].join();
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        if (this.cliqueSize > 4) {
            FindCliques5[] fq = new FindCliques5[threads];
            for (int i = 0; i < threads; i++) {
                if (i == threads - 1)
                    fq[i] = new FindCliques5(new Parameters(arrayEdges, i * size, (i + 1) * size + rem, Size, this.L,
                            this.HS, this.cliqueSize, this.debug, this.T, this.TSet, mode, this.C4, this.C4Set,
                            this.Clique_4, this.C5, this.C5Set, this.Clique_5));
                else
                    fq[i] = new FindCliques5(new Parameters(arrayEdges, i * size, (i + 1) * size, Size, this.L, this.HS,
                            this.cliqueSize, this.debug, this.T, this.TSet, mode, this.C4, this.C4Set, this.Clique_4,
                            this.C5, this.C5Set, this.Clique_5));
                Thread thread = new Thread(fq[i]);
                thread.start();
                threadsArray[i] = thread;
            }
            try {
                for (int i = 0; i < threads; i++) {
                    threadsArray[i].join();
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
}

interface Weighted {
    double get_weight();
}
