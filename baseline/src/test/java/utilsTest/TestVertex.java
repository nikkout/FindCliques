package utilsTest;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.testng.annotations.Test;

import utils.Edge;
import utils.EdgeLists;
import utils.Graph;
import utils.ReadGraph;
import utils.Triangle;
import utils.Vertex;

public class TestVertex {

	@Test
	public void testEquality() {
		Vertex v1 = new Vertex(10, 1.52);
		Vertex v2 = new Vertex(10, 7.54);
		Vertex v3 = new Vertex(10, 9.54, 0.15);
		Vertex v4 = new Vertex(10, 54, 0.5);
		assertTrue(v1 + "is not equal to " + v2, v1.equals(v2));
		assertTrue(v1 + "is not equal to " + v3, v1.equals(v3));
		assertTrue(v1 + "is not equal to " + v4, v1.equals(v4));
		assertTrue(v2 + "is not equal to " + v3, v2.equals(v3));
		assertTrue(v2 + "is not equal to " + v4, v2.equals(v4));
		assertTrue(v3 + "is not equal to " + v4, v3.equals(v4));
	}

	@Test
	public void testArrayListContains() {
		Vertex v1 = new Vertex(10, 1.52);
		Vertex v2 = new Vertex(10, 7.54);
		Vertex v3 = new Vertex(10, 9.54, 0.15);
		Vertex v4 = new Vertex(10, 54, 0.5);
		ArrayList<Vertex> array = new ArrayList<>();
		array.add(v1);
		assertTrue("Array does not contains " + v2, array.contains(v2));
		assertTrue("Array does not contains " + v3, array.contains(v3));
		assertTrue("Array does not contains " + v4, array.contains(v4));
	}
}
