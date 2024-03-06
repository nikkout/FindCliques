package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Time {

	public PriorityQueue<Triangle> executeAlgorithm(BiFunction<Graph, Integer, PriorityQueue<Triangle>> func, Graph a, int b, String funcName) {
		double start = System.currentTimeMillis();
		PriorityQueue<Triangle> res = func.apply(a, b);
		double end = System.currentTimeMillis();
		log.info("The {} required {} miliseconds to execute", funcName, end-start);
		return res;
	}
	
	public ArrayList<Triangle> executeBruteForce(TriFunction<double[][], double[][], Double, ArrayList<Triangle>> func, double[][] a, double[][] b, double c, String funcName) {
		double start = System.currentTimeMillis();
		ArrayList<Triangle> res = func.apply(a, b, c);
		double end = System.currentTimeMillis();
		log.info("The {} required {} miliseconds to execute", funcName, end-start);
		return res;
	}
	
	public ArrayList<Triangle> executeBruteForce(Function<double[][], ArrayList<Triangle>> func, double[][] hashMap, String funcName) {
		double start = System.currentTimeMillis();
		ArrayList<Triangle> res = func.apply(hashMap);
		double end = System.currentTimeMillis();
		log.info("The {} required {} miliseconds to execute", funcName, end-start);
		return res;
	}
}
