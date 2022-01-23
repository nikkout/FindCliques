import java.util.HashMap;
import java.util.ArrayList;

class Parameters{
	E[] e;
	int start;
	int end;
	int Size;
	HashMap<Integer, ArrayList<Tupple>> L;
	HashMap<Integer, ArrayList<Tupple>> HS;
	int cliqueSize;
	boolean debug;

	public Parameters(E[] e, int start, int end, int Size, HashMap<Integer, ArrayList<Tupple>> L, HashMap<Integer, ArrayList<Tupple>> HS, int cliqueSize, boolean debug){
		this.e = e;
		this.start = start;
		this.end = end;
		this.Size = Size;
		this.L = L;
		this.HS = HS;
		this.cliqueSize = cliqueSize;
		this.debug = debug;
	}
}
