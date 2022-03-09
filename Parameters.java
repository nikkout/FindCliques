import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

class Parameters{
	Edge[] e;
	int start;
	int end;
	int Size;
	HashMap<Integer, ArrayList<Tupple>> L;
	HashMap<Integer, ArrayList<Tupple>> HS;
	int cliqueSize;
	boolean debug;
	PriorityBlockingQueue<Triangle> T;
	PriorityBlockingQueue<Clique4> C4;
	Set<Clique4> C4Set;
	Set<Triangle> TSet;
	int mode;
	Map<Edge, List<Clique4Value>> Clique_4;

	public Parameters(Edge[] e, int start, int end, int Size, HashMap<Integer, ArrayList<Tupple>> L, HashMap<Integer, ArrayList<Tupple>> HS, int cliqueSize, boolean debug, PriorityBlockingQueue<Triangle> T, Set<Triangle> TSet, int mode, PriorityBlockingQueue<Clique4> C4, Set<Clique4> C4Set, Map<Edge, List<Clique4Value>> Clique_4){
		this.e = e;
		this.start = start;
		this.end = end;
		this.Size = Size;
		this.L = L;
		this.HS = HS;
		this.cliqueSize = cliqueSize;
		this.debug = debug;
		this.T = T;
		this.TSet = TSet;
		this.mode = mode;
		this.C4 = C4;
		this.C4Set = C4Set;
		this.Clique_4 = Clique_4;
	}
}
