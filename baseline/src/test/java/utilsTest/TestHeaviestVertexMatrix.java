package utilsTest;

import static org.testng.AssertJUnit.assertEquals;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.testng.annotations.Test;

import utils.Edge;
import utils.Graph;
import utils.HeaviestVertexMatrix;
import utils.Iterator;
import utils.ReadGraph;

public class TestHeaviestVertexMatrix {
    
    // mvn -Dtest=TestHeaviestVertexMatrix#testCreation test
    @Test
    public void testCreation(){
        ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
        HeaviestVertexMatrix matrix = new HeaviestVertexMatrix(graph);

        assertEquals(matrix.getMatrix().keySet().size(), graph.getL().size());
        this.assertMatrix(graph.getL(), matrix);
    }

    // mvn -Dtest=TestHeaviestVertexMatrix#testRemoveEdge test
    @Test
    public void testRemoveEdge(){
        ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
        HeaviestVertexMatrix matrix = new HeaviestVertexMatrix(graph);

        assertEquals(matrix.getMatrix().keySet().size(), graph.getL().size());

        HashMap<Integer, HashMap<Integer, Double>> L = graph.getL();

        Iterator<Edge> iterator = graph.getArray().getDefaultIterator();
        for(int i=0;i<100;i++){
            Edge tmp = iterator.getNext();
            L.get(tmp.getVertex1()).remove(tmp.getVertex2());
            L.get(tmp.getVertex2()).remove(tmp.getVertex1());
            matrix.removeEdge(tmp, L);
        }

        this.assertMatrix(L , matrix);
    }

    private void assertMatrix(HashMap<Integer, HashMap<Integer, Double>> L, HeaviestVertexMatrix matrix){
        L.forEach((key, map)->{
            AtomicReference<Double> min = new AtomicReference<>(Double.MAX_VALUE);
            Edge e = new Edge(key, key+1, min.get());
            map.forEach((vertex, weight)->{
                if(min.get() > weight){
                    e.setVertex2(vertex);
                    e.setWeight(weight);
                    min.set(weight);
                }
            });
            assertEquals(e, matrix.getEdge(key));
        });
    }
}
