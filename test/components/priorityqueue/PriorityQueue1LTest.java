package components.priorityqueue;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

/**
 * JUnit test cases for {@link PriorityQueue1L} kernel methods.
 *
 * <p>
 * Tests cover all kernel methods: {@code enqueue}, {@code dequeue},
 * {@code length}, {@code setComparator}, and {@code clear}. Each test verifies
 * both the expected return value and that state is properly restored or updated
 * by using kernel methods to inspect the queue afterward.
 * </p>
 */
public class PriorityQueue1LTest {

    /**
     * Natural integer ordering (ascending).
     */
    private static final Comparator<Integer> INT_ORDER = Integer::compareTo;

    /**
     * Creates a new empty {@code PriorityQueue1L} with integer natural order.
     *
     * @return a new empty queue
     */
    private PriorityQueue1L<Integer> newQueue() {
        return new PriorityQueue1L<>(INT_ORDER);
    }

    /*
     * -----------------------------------------------------------------------
     * enqueue tests
     * -----------------------------------------------------------------------
     */

    /**
     * Enqueue one item into an empty queue; length should be 1 and dequeue
     * should return that item.
     */
    @Test
    public void testEnqueueIntoEmpty() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(5); // SHUT UP ABOUT MAGIC NUMBERS IDC
        assertEquals(1, q.length());
        assertEquals(Integer.valueOf(5), q.dequeue());
    }

    /**
     * Enqueue items already in ascending order; dequeue should return them in
     * the same order and length should reach 0 afterward.
     */
    @Test
    public void testEnqueueAscendingOrder() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        assertEquals(3, q.length());
        assertEquals(Integer.valueOf(1), q.dequeue());
        assertEquals(Integer.valueOf(2), q.dequeue());
        assertEquals(Integer.valueOf(3), q.dequeue());
        assertEquals(0, q.length());
    }

    /**
     * Enqueue items in descending order; dequeue should still return them in
     * ascending order due to the comparator.
     */
    @Test
    public void testEnqueueDescendingOrder() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(3);
        q.enqueue(2);
        q.enqueue(1);
        assertEquals(3, q.length());
        assertEquals(Integer.valueOf(1), q.dequeue());
        assertEquals(Integer.valueOf(2), q.dequeue());
        assertEquals(Integer.valueOf(3), q.dequeue());
    }

    /**
     * Enqueue items in arbitrary order; dequeue should return them sorted.
     */
    @Test
    public void testEnqueueArbitraryOrder() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(5);
        q.enqueue(1);
        q.enqueue(3);
        q.enqueue(2);
        q.enqueue(4);
        assertEquals(5, q.length());
        assertEquals(Integer.valueOf(1), q.dequeue());
        assertEquals(Integer.valueOf(2), q.dequeue());
        assertEquals(Integer.valueOf(3), q.dequeue());
        assertEquals(Integer.valueOf(4), q.dequeue());
        assertEquals(Integer.valueOf(5), q.dequeue());
    }

    /**
     * Enqueue duplicate values; both should be retained and returned in order.
     */
    @Test
    public void testEnqueueDuplicates() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(2);
        q.enqueue(2);
        q.enqueue(1);
        assertEquals(3, q.length());
        assertEquals(Integer.valueOf(1), q.dequeue());
        assertEquals(Integer.valueOf(2), q.dequeue());
        assertEquals(Integer.valueOf(2), q.dequeue());
    }

    /*
     * -----------------------------------------------------------------------
     * dequeue tests
     * -----------------------------------------------------------------------
     */

    /**
     * Dequeue the only element; the queue should be empty afterward.
     */
    @Test
    public void testDequeueSingleElement() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(7);
        Integer result = q.dequeue();
        assertEquals(Integer.valueOf(7), result);
        assertEquals(0, q.length());
    }

    /**
     * Dequeue from a queue of three elements; each call returns the current
     * minimum and length decreases by one.
     */
    @Test
    public void testDequeueReturnsMinimumFirst() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(8);
        q.enqueue(3);
        q.enqueue(5);
        assertEquals(Integer.valueOf(3), q.dequeue());
        assertEquals(2, q.length());
        assertEquals(Integer.valueOf(5), q.dequeue());
        assertEquals(1, q.length());
        assertEquals(Integer.valueOf(8), q.dequeue());
        assertEquals(0, q.length());
    }

    /**
     * Dequeue with negative values; smallest (most negative) should come first.
     */
    @Test
    public void testDequeueNegativeValues() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(-1);
        q.enqueue(-10);
        q.enqueue(0);
        assertEquals(Integer.valueOf(-10), q.dequeue());
        assertEquals(Integer.valueOf(-1), q.dequeue());
        assertEquals(Integer.valueOf(0), q.dequeue());
    }

    /*
     * -----------------------------------------------------------------------
     * length tests
     * -----------------------------------------------------------------------
     */

    /**
     * A newly created queue should have length 0.
     */
    @Test
    public void testLengthEmpty() {
        PriorityQueue1L<Integer> q = this.newQueue();
        assertEquals(0, q.length());
    }

    /**
     * After enqueuing two items, length should be 2.
     */
    @Test
    public void testLengthAfterEnqueue() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(10);
        q.enqueue(20);
        assertEquals(2, q.length());
    }

    /**
     * Calling {@code length} should not modify the queue.
     */
    @Test
    public void testLengthDoesNotModifyState() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(10);
        int firstCall = q.length();
        int secondCall = q.length();
        assertEquals(firstCall, secondCall);
        assertEquals(Integer.valueOf(10), q.dequeue());
    }

    /**
     * Length after a dequeue should decrease by exactly one.
     */
    @Test
    public void testLengthAfterDequeue() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.dequeue();
        assertEquals(2, q.length());
    }

    /*
     * -----------------------------------------------------------------------
     * setComparator tests
     * -----------------------------------------------------------------------
     */

    /**
     * Setting a reverse comparator on a populated queue should re-order
     * elements so the largest comes out first.
     */
    @Test
    public void testSetComparatorReverseOrder() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.setComparator(Comparator.reverseOrder());
        assertEquals(Integer.valueOf(3), q.dequeue());
        assertEquals(Integer.valueOf(2), q.dequeue());
        assertEquals(Integer.valueOf(1), q.dequeue());
    }

    /**
     * {@code setComparator} should preserve the number of elements.
     */
    @Test
    public void testSetComparatorPreservesLength() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(4);
        q.enqueue(9);
        q.enqueue(1);
        q.setComparator(Comparator.reverseOrder());
        assertEquals(3, q.length());
    }

    /**
     * Setting the same comparator again should leave the queue unchanged.
     */
    @Test
    public void testSetComparatorSameComparator() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(3);
        q.enqueue(1);
        q.enqueue(2);
        q.setComparator(INT_ORDER);
        assertEquals(3, q.length());
        assertEquals(Integer.valueOf(1), q.dequeue());
        assertEquals(Integer.valueOf(2), q.dequeue());
        assertEquals(Integer.valueOf(3), q.dequeue());
    }

    /*
     * -----------------------------------------------------------------------
     * clear tests
     * -----------------------------------------------------------------------
     */

    /**
     * Clearing an already empty queue should leave it empty (length 0).
     */
    @Test
    public void testClearEmpty() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.clear();
        assertEquals(0, q.length());
    }

    /**
     * Clearing a non-empty queue should set length to 0.
     */
    @Test
    public void testClearNonEmpty() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.clear();
        assertEquals(0, q.length());
    }

    /**
     * After clearing, new elements can be enqueued normally.
     */
    @Test
    public void testEnqueueAfterClear() {
        PriorityQueue1L<Integer> q = this.newQueue();
        q.enqueue(5);
        q.clear();
        q.enqueue(10);
        assertEquals(1, q.length());
        assertEquals(Integer.valueOf(10), q.dequeue());
    }
}
