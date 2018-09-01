import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MergeIterator<T extends Comparable<? super T>> implements Iterator {
    private final Iterator<T> one;
    private final Iterator<T> two;
    private T element1;
    private T element2;

    public MergeIterator(Iterator<T> one, Iterator<T> two) {
        this.one = one;
        this.two = two;

        if (one.hasNext()) {
            element1 = one.next();
        }

        if (two.hasNext()) {
            element2 = two.next();
        }
    }

    @Override
    public boolean hasNext() {
        return element1 != null || element2 != null;
    }

    @Override
    public Object next() {
        if (element1 != null && element2 != null) {
            if (element1.compareTo(element2) < 0) {
                T tmp = element1;
                element1 = one.hasNext() ? one.next() : null;
                return tmp;
            } else {
                T tmp = element2;
                element2 = two.hasNext() ? two.next() : null;
                return tmp;
            }
        } else if (element1 != null) {
            T tmp = element1;
            element1 = one.hasNext() ? one.next() : null;
            return tmp;
        } else if (element2 != null) {
            T tmp = element2;
            element2 = two.hasNext() ? two.next() : null;
            return tmp;
        } else {
            throw new NoSuchElementException();
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> oneList = new ArrayList<>(Arrays.asList(9, 5, 8));
        Collections.sort(oneList);

        ArrayList<Integer> twoList = new ArrayList<>(Arrays.asList(1, 7, 4, 9, 3));
        Collections.sort(twoList);

        Iterator<Integer> oneIterator = oneList.iterator();
        Iterator twoIterator = twoList.iterator();

        MergeIterator<Integer> mergeIterator = new MergeIterator<>(oneIterator, twoIterator);
        StringBuilder result = new StringBuilder();
        while (mergeIterator.hasNext()) {
            result.append(mergeIterator.next() + " ");
        }
        System.out.println(result);
    }
}

/*class MergeIteratorTest {

    @Test
    public void testMerge() {
        ArrayList<Integer> oneList = new ArrayList<>(Arrays.asList(9, 5, 8));
        ArrayList<Integer> twoList = new ArrayList<>(Arrays.asList(1, 7, 4, 9, 3));
        Iterator<Integer> oneIterator = oneList.iterator();
        Iterator twoIterator = twoList.iterator();

        MergeIterator <Integer> mergeIterator = new MergeIterator<>(oneIterator, twoIterator);
        StringBuilder result = new StringBuilder();
        if(mergeIterator.hasNext()){
            result.append(mergeIterator.next()).append(" ");
        }
        String actual = result.substring(0, result.length() - 1).toString();

        String expected = "1 3 4 5 7 8 9 9";

        assertEquals(expected, actual);
    }
}*/
