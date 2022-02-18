import java.util.Set;
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
	Set<Triangle> TSet;
	int mode;

	public Parameters(Edge[] e, int start, int end, int Size, HashMap<Integer, ArrayList<Tupple>> L, HashMap<Integer, ArrayList<Tupple>> HS, int cliqueSize, boolean debug, PriorityBlockingQueue<Triangle> T, Set<Triangle> TSet, int mode){
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
	}
}
