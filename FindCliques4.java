import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

class FindCliques4 implements Runnable {

    E[] e;
    PriorityBlockingQueue<Clique4> C4;
    ArrayList<Triangle> triangles;
    static int counter = 0;
    HashSet<Triangle> Tl;
    Triangle ht = null;
    Parameters parameters;
    Triangle[] triangle_array;

    public FindCliques4(Parameters parameters) {
        this.parameters = parameters;
        this.triangles = new ArrayList<Triangle>();
        FindTriangles.counter++;
        Tl = new HashSet<Triangle>();
        C4 = new PriorityBlockingQueue<Clique4>(this.parameters.Size, new Comparator<Clique4>() {
            @Override
            public int compare(Clique4 lhs, Clique4 rhs) {
                return lhs.weight > rhs.weight ? 1 : (lhs.weight < rhs.weight) ? -1 : 0;
            }
        });
    }

    private void Cliques4() {
        HashSet<Clique4> C4Set = new HashSet<Clique4>();
        for (int n = this.parameters.start; n < this.parameters.end; n++) {
            Edge current = parameters.e[n];
            if (!this.parameters.Clique_4.containsKey(current))
                continue;
            List<Clique4Value> arr = this.parameters.Clique_4.get(current);
            for (int i = 0; i < arr.size(); i++) {
                int vertexA = arr.get(i).vertex;
                for (int y = i; y < arr.size(); y++) {
                    int vertexB = arr.get(y).vertex;
                    int posL = -1, posH = -1;
                    Tupple tmp = null;
                    if (this.parameters.L.containsKey(vertexA))
                        posL = this.parameters.L.get(vertexA).indexOf(new Tupple(vertexB, 0));
                    if (posL == -1 && this.parameters.HS.containsKey(vertexA))
                        posH = this.parameters.HS.get(vertexA).indexOf(new Tupple(vertexB, 0));
                    if (posL != -1)
                        tmp = this.parameters.L.get(vertexA).get(posL);
                    else if (posH != -1)
                        tmp = this.parameters.HS.get(vertexA).get(posH);
                    if (tmp != null) {
                        Triangle t1 = arr.get(i).triangle;
                        Triangle t2 = arr.get(y).triangle;
                        Clique4 clique = new Clique4(t1, t2, new Edge(arr.get(y).vertex, tmp.vertex, tmp.weight),
                                t1.weight + t2.weight + tmp.weight - current.weight);
                        C4Set.add(clique);
                        if (this.parameters.cliqueSize > 4) {
                            if (!this.parameters.Clique_5.containsKey(t1))
                                this.parameters.Clique_5.put(t1,
                                        Collections.synchronizedList(new ArrayList<Clique5Value>()));
                            this.parameters.Clique_5.get(t1).add(new Clique5Value(clique, vertexB));
                            if (!this.parameters.Clique_5.containsKey(t2))
                                this.parameters.Clique_5.put(t2,
                                        Collections.synchronizedList(new ArrayList<Clique5Value>()));
                            this.parameters.Clique_5.get(t2).add(new Clique5Value(clique, vertexA));
                        }
                    }
                }
            }
        }
        Clique4[] clique_array = new Clique4[C4Set.size()];
        clique_array = C4Set.toArray(clique_array);
        Arrays.sort(clique_array, new Comparator<Clique4>() {
            @Override
            public int compare(Clique4 lhs, Clique4 rhs) {
                return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
            }
        });
        for (int i = 0; i < clique_array.length; i++) {
            if (this.parameters.C4.size() < this.parameters.Size && !this.parameters.C4Set.contains(clique_array[i])) {
                this.parameters.C4.put(clique_array[i]);
                this.parameters.C4Set.add(clique_array[i]);
            } else if (this.parameters.C4.peek().weight < clique_array[i].weight
                    && !this.parameters.C4Set.contains(clique_array[i])) {
                this.parameters.C4Set.remove(this.parameters.C4.poll());
                this.parameters.C4.put(clique_array[i]);
                this.parameters.C4Set.add(clique_array[i]);
            } else if (this.parameters.C4.peek().weight >= clique_array[i].weight
                    && this.parameters.C4.size() >= this.parameters.Size)
                break;
        }

    }

    public void run() {
        this.Cliques4();
    }
}
