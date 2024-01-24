package generator;

import utils.GenerateGraph;
import utils.ReadGraph;

public class Generator {

	public static void main(String args[]) {
		if (args.length != 3) {
			printHelp();
			return;
		} else if (!args[0].equals("generate")) {
			printHelp();
			return;
		}

		if (args[1].equals("w")) {

		} else if (args[1].equals("p")) {
			
		} else if (args[1].equals("wp")) {
			ReadGraph rg = new ReadGraph(args[2]);
			rg.readVVW();
			GenerateGraph gg = new GenerateGraph();
			gg.generateGraphWP(rg.getGraph(), args[2]+"_gen");
		} else
			printHelp();
	}

	static void printHelp() {
		System.out.println(Generator.class.getName());
		System.out.println("Three arguments required: <command> <type> <filename>");
		System.out.println("command: generate");
		System.out.println("type: w | p | wp");
		System.out.println("filename: the name of a file containig a graph in the following form |v1 v2 w|");
	}
}
