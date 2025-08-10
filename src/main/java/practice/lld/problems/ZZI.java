package practice.lld.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class RangeIterator implements Iterator<Integer> {
    private int current;
    private final int end;
    private final int step;

    public RangeIterator(int start, int end, int step) {
        if (step == 0) throw new IllegalArgumentException("Step cannot be zero");
        this.current = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public boolean hasNext() {
        return step > 0 ? current <= end : current >= end;
    }

    @Override
    public Integer next() {
        int val = current;
        current += step;
        return val;
    }
}

class ZigzagIteratorFromIterators {
    private final Queue<Iterator<Integer>> queue;

    public ZigzagIteratorFromIterators(List<Iterator<Integer>> iterators) {
        queue = new LinkedList<>();
        for (Iterator<Integer> itr : iterators) {
            if (itr.hasNext()) {
                queue.offer(itr);
            }
        }
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }

    public int next() {
        Iterator<Integer> itr = queue.poll();
        int value = itr.next();
        if (itr.hasNext()) {
            queue.offer(itr);
        }
        return value;
    }
}

class SimpleListIterator implements Iterator<Integer> {
    private final List<Integer> list;
    private int index = 0;

    public SimpleListIterator(List<Integer> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public Integer next() {
        return list.get(index++);
    }
}

public class ZZI {

    public static void main(String[] args) {
        // Level 4 Example
        List<Iterator<Integer>> iterators = new ArrayList<>();
        iterators.add(new SimpleListIterator(Arrays.asList(0, 1, 2)));
        iterators.add(new RangeIterator(3, 4, 1));  // 3, 4
        iterators.add(new SimpleListIterator(Arrays.asList(5))); // 5

        ZigzagIteratorFromIterators zigzag = new ZigzagIteratorFromIterators(iterators);
        while (zigzag.hasNext()) {
            System.out.print(zigzag.next() + " ");
        }
        // Output: 0 3 5 1 4 2
    }

}
