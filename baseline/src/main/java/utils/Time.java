package utils;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Time {
	
	
	
	private static long millisStart;

	public static void start() {
		millisStart = System.currentTimeMillis();
	}
	
	public static long end() {
		return System.currentTimeMillis() - millisStart;
	}

	public PriorityQueue<Triangle> executeAlgorithm(Callable<PriorityQueue<Triangle>> func, String funcName) {
		double start = System.currentTimeMillis();
		log.info("Starting execution");
		PriorityQueue<Triangle> res = null;
		try {
			res = func.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double end = System.currentTimeMillis();
		log.info("The {} required {} miliseconds to execute", funcName, end-start);
		return res;
	}
	
	public PriorityBlockingQueue<Triangle> executeAlgorithmMultiThread(Callable<PriorityBlockingQueue<Triangle>> func, String funcName) {
		double start = System.currentTimeMillis();
		PriorityBlockingQueue<Triangle> res = null;
		try {
			res = func.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
