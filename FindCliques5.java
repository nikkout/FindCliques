import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

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
            List<Tupple> exam1 = this.parameters.HS.get(current.vertex1);
            List<Tupple> exam2 = this.parameters.HS.get(current.vertex2);
            final List<Tupple> exam;
            if(exam1.size() > exam2.size())exam = exam2;
            else exam = exam1;
            List<Clique4Value> c4vs = this.parameters.Clique_4.get(current);
            c4vs.forEach(c4v -> {
                if (!this.parameters.Clique_5.containsKey(c4v.triangle))
                    return;
                Map<Integer, Clique4> arr = this.parameters.Clique_5.get(c4v.triangle);
                /*this.parameters.HS.forEach((vertexA, list)->{
                    list.stream().forEach(vertexB ->{
                        if (arr.containsKey(vertexA) && arr.containsKey(vertexB.vertex)) {
                            Clique4 c1 = arr.get(vertexA);
                            Clique4 c2 = arr.get(vertexB.vertex);
                            Clique5 clique = new Clique5(c1, c2, new Edge(vertexA, vertexB.vertex, vertexB.weight),
                                    c1.weight + c2.weight + current.weight - c4v.triangle.weight);
                            C5Set.add(clique);
                        }
                    });
                });*/
                /*for (int i = this.parameters.start; i < this.parameters.end; i++) {
                    Edge currentI = parameters.e[i];
                    if (arr.containsKey(currentI.vertex1) && arr.containsKey(currentI.vertex2)) {
                        Clique4 c1 = arr.get(currentI.vertex1);
                        Clique4 c2 = arr.get(currentI.vertex2);
                        Clique5 clique = new Clique5(c1, c2, new Edge(currentI.vertex1, currentI.vertex2, currentI.weight),
                                c1.weight + c2.weight + current.weight - c4v.triangle.weight);
                        C5Set.add(clique);
                    }
                }*/
                /*this.parameters.array.forEach(edge ->{
                    if (arr.containsKey(edge.vertex1) && arr.containsKey(edge.vertex2)) {
                        Clique4 c1 = arr.get(edge.vertex1);
                        Clique4 c2 = arr.get(edge.vertex2);
                        Clique5 clique = new Clique5(c1, c2, new Edge(edge.vertex1, edge.vertex2, edge.weight),
                                c1.weight + c2.weight + current.weight - c4v.triangle.weight);
                        C5Set.add(clique);
                    }
                });*/
                
                exam.forEach(t->{
                    this.parameters.HS.get(t.vertex).forEach(t2->{
                        if(!arr.containsKey(t.vertex))return;
                        if (arr.containsKey(t2.vertex)) {
                            Clique4 c1 = arr.get(t.vertex);
                            Clique4 c2 = arr.get(t2.vertex);
                            Clique5 clique = new Clique5(c1, c2, new Edge(t.vertex, t2.vertex, t2.weight),
                                    c1.weight + c2.weight + current.weight - c4v.triangle.weight);
                            C5Set.add(clique);
                        }
                    });
                });
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
