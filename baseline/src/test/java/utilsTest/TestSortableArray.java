package utilsTest;

import static org.testng.AssertJUnit.assertTrue;

import java.util.UUID;

import org.testng.annotations.Test;
import utils.SortableArray;
import utils.Iterator;

// mvn -Dtest=TestSortableArray test
public class TestSortableArray {

    // mvn -Dtest=TestSortableArray#iterateStruct test
    @Test
    public void iterateStruct() {
        SortableArray<Integer> array = new SortableArray<Integer>();
        for (int i = 0; i < 100; i++) {
            array.add(i);
        }
        Iterator<Integer> iterator = array.getDefaultIterator();
        for (int i = 0; i < 100; i++) {
            Integer tmp = iterator.getNext();
            assertTrue(tmp + " is not equal to " + i, tmp.equals(i));
        }
    }

    // mvn -Dtest=TestSortableArray#iterateStructReset test
    @Test
    public void iterateStructReset() {
        SortableArray<Integer> array = new SortableArray<Integer>();
        for (int i = 0; i < 100; i++) {
            array.add(i);
        }
        Iterator<Integer> iterator = array.getDefaultIterator();
        for (int i = 0; i < 10; i++) {
            Integer tmp = iterator.getNext();
            assertTrue(tmp + " is not equal to " + i, tmp.equals(i));
        }
        iterator.reset();
        for (int i = 0; i < 100; i++) {
            Integer tmp = iterator.getNext();
            assertTrue(tmp + " is not equal to " + i, tmp.equals(i));
        }
    }

    // mvn -Dtest=TestSortableArray#structAdd test
    @Test
    public void structAdd() {
        SortableArray<Integer> array = new SortableArray<Integer>();
        for (int i = 0; i < 100; i++) {
            array.add(i);
        }
        Iterator<Integer> iterator = array.getDefaultIterator();
        for (int i = 0; i < 10; i++) {
            Integer tmp = iterator.getNext();
            assertTrue(tmp + " is not equal to " + i, tmp.equals(i));
        }
        array.add(100);
        for (int i = 10; i < 101; i++) {
            Integer tmp = iterator.getNext();
            assertTrue(tmp + " is not equal to " + i, tmp.equals(i));
        }
    }

    // mvn -Dtest=TestSortableArray#structSort test
    @Test
    public void structSort() {
        SortableArray<Integer> array = new SortableArray<Integer>();
        for (int i = 0; i < 100; i++) {
            array.add(i);
        }
        UUID uuid = array.sort((Integer x, Integer y) -> {
            return y - x;
        });

        Iterator<Integer> iterator = array.getIterator(uuid);
        for (int i = 0; i < 100; i++) {
            Integer tmp = iterator.getNext();
            assertTrue(tmp + " is not equal to " + (99 - i), tmp.equals(99 - i));
        }

        iterator = array.getDefaultIterator();
        for (int i = 0; i < 100; i++) {
            Integer tmp = iterator.getNext();
            assertTrue(tmp + " is not equal to " + i, tmp.equals(i));
        }
    }

    // mvn -Dtest=TestSortableArray#checkSize test
    @Test(enabled = false)
    public void checkSize() {
        SortableArray<Integer> array = new SortableArray<Integer>();
        for (int i = 0; i < 100; i++) {
            array.add(i);
        }
        array.sort((Integer x, Integer y) -> {
            return y - x;
        });
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // mvn -Dtest=TestSortableArray#checkSize3 test
    @Test(enabled = false)
    public void checkSize3() {
        SortableArray<Integer> array = new SortableArray<Integer>();
        for (int i = 0; i < 100; i++) {
            array.add(i);
        }
        array.sort((Integer x, Integer y) -> {
            return y - x;
        });

        array.sort((Integer x, Integer y) -> {
            return y - x;
        });

        array.sort((Integer x, Integer y) -> {
            return y - x;
        });

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
