package utils;

import java.io.File;
import java.util.Random;

public class GenerateGraph {

	public void generateGraphWP(Graph graph, String fname){
		Random rand = new Random();
		graph.getArray().forEach(e ->{
			e.setProbability((Math.abs(rand.nextGaussian()))%1);
		});
		graph.toFileWP(new File(fname));
	}
}
