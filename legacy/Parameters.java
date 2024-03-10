package legacy;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

class Parameters {
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
	PriorityBlockingQueue<Clique5> C5;
	Set<Clique5> C5Set;
	Set<Triangle> TSet;
	int mode;
	Map<Edge, List<Clique4Value>> Clique_4;
	Map<Triangle, List<Clique5Value>> Clique_5;

	public Parameters(Edge[] e, int start, int end, int Size, HashMap<Integer, ArrayList<Tupple>> L,
			HashMap<Integer, ArrayList<Tupple>> HS, int cliqueSize, boolean debug, PriorityBlockingQueue<Triangle> T,
			Set<Triangle> TSet, int mode, PriorityBlockingQueue<Clique4> C4, Set<Clique4> C4Set,
			Map<Edge, List<Clique4Value>> Clique_4) {
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

	public Parameters(Edge[] e, int start, int end, int Size, HashMap<Integer, ArrayList<Tupple>> L,
			HashMap<Integer, ArrayList<Tupple>> HS, int cliqueSize, boolean debug, PriorityBlockingQueue<Triangle> T,
			Set<Triangle> TSet, int mode, PriorityBlockingQueue<Clique4> C4, Set<Clique4> C4Set,
			Map<Edge, List<Clique4Value>> Clique_4, PriorityBlockingQueue<Clique5> C5, Set<Clique5> C5Set,
			Map<Triangle, List<Clique5Value>> Clique_5) {
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
		this.C5 =C5;
		this.C5Set = C5Set;
		this.Clique_5 = Clique_5;
	}
}
