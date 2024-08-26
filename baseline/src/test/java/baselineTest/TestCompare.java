package baselineTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import baseline.Baseline;
import baseline.Prune;
import lombok.extern.slf4j.Slf4j;
import utils.Graph;
import utils.ReadGraph;
import utils.Time;

//mvn -Dtest=TestCompare test
@Slf4j
public class TestCompare extends Common {
	private Graph graph;

	@BeforeMethod
	public void readGraph(){
		ReadGraph rg = new ReadGraph(this.getClass().getClassLoader().getResource("test_2.dat_gen").getFile());
		rg.readVVWP();
		this.graph = rg.getGraph();
	}

	// mvn -Dtest=TestCompare#testBaseline100000_1_c test
	@Test(enabled = false)
	public void testBaseline100000_1_c() {
		log.info("Starting test");
		Time time = new Time();
		Prune pn = new Prune(graph, 100000, 1.5, 0.1);
		Baseline baseline = new Baseline(graph, 100000, 1.5);
		time.executeAlgorithm(pn::findTriangles, "Algorithm PruneP");
		time.executeAlgorithm(baseline::findTriangles, "Algorithm Baseline");
		log.info("Ending test");
		log.info("====================================");
	}

	// mvn -Dtest=TestCompare#testBaseline100000_001_c test
	@Test(enabled = false)
	public void testBaseline100000_001_c() {
		log.info("Starting test");
		Time time = new Time();
		Prune pn = new Prune(graph, 100000, 1.5, 0.001);
		Baseline baseline = new Baseline(graph, 100000, 1.5);
		time.executeAlgorithm(pn::findTriangles, "Algorithm PruneP");
		time.executeAlgorithm(baseline::findTriangles, "Algorithm Baseline");
		log.info("Ending test");
		log.info("====================================");
	}

	// mvn -Dtest=TestCompare#testBaseline100000_0001_c test
	@Test(enabled = false)
	public void testBaseline100000_0001_c() {
		log.info("Starting test");
		Time time = new Time();
		Prune pn = new Prune(graph, 100000, 1.5, 0.0001);
		Baseline baseline = new Baseline(graph, 100000, 1.5);
		time.executeAlgorithm(pn::findTriangles, "Algorithm PruneP");
		time.executeAlgorithm(baseline::findTriangles, "Algorithm Baseline");
		log.info("Ending test");
		log.info("====================================");
	}

	// mvn -Dtest=TestCompare#testBaseline1000_001_c test
	@Test(enabled = false)
	public void testBaseline1000_001_c() {
		log.info("Starting test");
		Time time = new Time();
		Prune pn = new Prune(graph, 1000, 1.5, 0.001);
		Baseline baseline = new Baseline(graph, 1000, 1.5);
		time.executeAlgorithm(pn::findTriangles, "Algorithm PruneP");
		time.executeAlgorithm(baseline::findTriangles, "Algorithm Baseline");
		log.info("Ending test");
		log.info("====================================");
	}
}
