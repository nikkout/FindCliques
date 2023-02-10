import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

class FindCliques5 implements Runnable {

    E[] e;
    PriorityBlockingQueue<Clique5> C5;
    ArrayList<Triangle> triangles;
    static int counter = 0;
    HashSet<Triangle> Tl;
    Triangle ht = null;
    Parameters parameters;
    Triangle[] triangle_array;

    public FindCliques5(Parameters parameters) {
        this.parameters = parameters;
        this.triangles = new ArrayList<Triangle>();
        Tl = new HashSet<Triangle>();
        C5 = new PriorityBlockingQueue<Clique5>(this.parameters.Size, new Comparator<Clique5>() {
            @Override
            public int compare(Clique5 lhs, Clique5 rhs) {
                return lhs.weight > rhs.weight ? 1 : (lhs.weight < rhs.weight) ? -1 : 0;
            }
        });
    }

    private void Cliques5() {
        HashSet<Clique5> C5Set = new HashSet<Clique5>();
        for (int n = this.parameters.start; n < this.parameters.end; n++) {
            Edge current = parameters.e[n];
            if (!this.parameters.Clique_4.containsKey(current))
                continue;
            List<Clique4Value> c4vs = this.parameters.Clique_4.get(current);
            c4vs.forEach(c4v -> {
                if (!this.parameters.Clique_5.containsKey(c4v.triangle))
                    return;
                List<Clique5Value> arr = this.parameters.Clique_5.get(c4v.triangle);
                for (int i = 0; i < arr.size(); i++) {
                    Clique5Value c5vi = arr.get(i);
                    int vertexA = c5vi.vertex;
                    for (int y = i; y < arr.size(); y++) {
                        Clique5Value c5vy = arr.get(y);
                        int vertexB = c5vy.vertex;
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
                            Clique4 c1 = c5vi.clique4;
                            Clique4 c2 = c5vy.clique4;
                            Clique5 clique = new Clique5(c1, c2, new Edge(c5vy.vertex, tmp.vertex, tmp.weight),
                                    c1.weight + c2.weight + tmp.weight - current.weight);
                            C5Set.add(clique);
                        }
                    }
                }
            });

        }
        Clique5[] clique_array = new Clique5[C5Set.size()];
        clique_array = C5Set.toArray(clique_array);
        Arrays.sort(clique_array, new Comparator<Clique5>() {
            @Override
            public int compare(Clique5 lhs, Clique5 rhs) {
                return lhs.weight > rhs.weight ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
            }
        });
        for (int i = 0; i < clique_array.length; i++) {
            if (this.parameters.C5.size() < this.parameters.Size && !this.parameters.C5Set.contains(clique_array[i])) {
                this.parameters.C5.put(clique_array[i]);
                this.parameters.C5Set.add(clique_array[i]);
            } else if (this.parameters.C5.peek().weight < clique_array[i].weight
                    && !this.parameters.C5Set.contains(clique_array[i])) {
                this.parameters.C5Set.remove(this.parameters.C5.poll());
                this.parameters.C5.put(clique_array[i]);
                this.parameters.C5Set.add(clique_array[i]);
            } else if (this.parameters.C5.peek().weight >= clique_array[i].weight
                    && this.parameters.C5.size() >= this.parameters.Size)
                break;
        }

    }

    public void run() {
        this.Cliques5();
    }
}
