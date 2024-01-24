package utils;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.testng.annotations.Test;

import baseline.FindTriangles;

public class TestReadGraph {

	@Test
	public void testVVLists() {
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("email-Eu-core.txt").getFile());
		rg.readVV();
		Graph graph = rg.getGraph();
		graph.getL().forEach((vertex, list)->{
			list.forEach(v ->{
				int f = list.indexOf(v);
				int l = list.lastIndexOf(v);
				assertTrue(v + " appears more than once ["+ list.get(f)+", "+list.get(l), f==l);
			});
		});
	}
}
