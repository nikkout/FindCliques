package baseline;

import lombok.extern.slf4j.Slf4j;
import utils.Edge;
import utils.Graph;
import utils.HeaviestVertexMatrix;

@Slf4j
public class BaselineWithMatrix extends Baseline{

    private HeaviestVertexMatrix heaviestVertexMatrix;

    public BaselineWithMatrix(Graph graph, int size, double ar){
		super(graph, size, ar);
        this.heaviestVertexMatrix = new HeaviestVertexMatrix(graph);
	}

    @Override
    protected int hsSearch(int l) {
        Edge edge = array.get(l + 1);
        this.heaviestVertexMatrix.removeEdge(edge, L);
        double w = edge.getWeight();
        double w1 = this.heaviestVertexMatrix.getEdge(edge.getVertex1()).getWeight();
        double w2 = this.heaviestVertexMatrix.getEdge(edge.getVertex2()).getWeight();
        //case i
        if(currentSize >= size && w > w1 && w > w2 && (w + w1 + w2 < currentPeek.getWeight())){
            log.info("Case i hs");
            this.move(L, HS, array, l);
            return l + 1;
        }
        return super.hsSearch(l);
    }

    @Override
    protected int lSearch(int h) {
        Edge edge = array.get(h + 1);
        double w = edge.getWeight();
        double w1 = this.heaviestVertexMatrix.getEdge(edge.getVertex1()).getWeight();
        double w2 = this.heaviestVertexMatrix.getEdge(edge.getVertex2()).getWeight();
        //case ii
        if(currentSize >= size && (w < w1 || w < w2) && (w + w1 + w2 < currentPeek.getWeight())){
            log.info("Case ii ls");
            return h + 1;
        }
        //case i
        // case iii
		return super.lSearch(h);
    }
}
