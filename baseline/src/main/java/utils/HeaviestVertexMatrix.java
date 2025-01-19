package utils;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaviestVertexMatrix{
    private HashMap<Integer, Edge> matrix;

    public HeaviestVertexMatrix(Graph graph){
        this.matrix = new HashMap<>();
        graph.getL().forEach((key, value) ->{
            computeHeaviestEdge(key, value);
        });
    }

    public HashMap<Integer, Edge> getMatrix(){
        return this.matrix;
    }

    public Edge getEdge(int vertex){
        return this.matrix.get(vertex);
    }

    public void removeEdge(Edge e, HashMap<Integer, HashMap<Integer, Double>> L){
        Edge e1 = this.matrix.get(e.getVertex1());
        Edge e2 = this.matrix.get(e.getVertex2());
        if(e1.equals(e)){
            this.computeHeaviestEdge(e.getVertex1(), L.get(e.getVertex1()));
        }
        if(e2.equals(e)){
            this.computeHeaviestEdge(e.getVertex2(), L.get(e.getVertex2()));
        }
    }

    private void computeHeaviestEdge(Integer vertex, HashMap<Integer, Double> value) {
        AtomicReference<Double> minWeight = new AtomicReference<>(Double.MAX_VALUE);
        Edge e = new Edge(vertex, vertex+1, 0);
        if(value.size() == 0){
            matrix.remove(vertex);
            return;
        }
        value.forEach((vertex2, weight) -> {
            if(weight < minWeight.get()){
                e.setVertex2(vertex2);
                e.setWeight(weight);
                minWeight.set(weight);
            }
        });
        matrix.put(vertex, e);
    }

}