package components.priorityqueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

/**
 * JUnit test cases for the secondary methods defined in
 * {@link PriorityQueueSecondary}, exercised through {@link PriorityQueue1L}.
 *
 * <p>
 * Tests cover all secondary methods: {@code front}, {@code back},
 * {@code combine}, {@code isEmpty}, {@code clear}, and {@code equals}. Each
 * test verifies both the expected return value and that the queue's state is
 * correct afterward using kernel methods ({@code length} and {@code dequeue}).
 * </p>
 */
public class PriorityQueueTest {

    /**
     * Natural integer ordering (ascending).
     */
    private static final Comparator<Integer> INT_ORDER = Integer::compareTo;

    /**
     * Creates a new empty {@code PriorityQueue} backed by {@code PriorityQueue1L}
     * using natural integer ordering.
     *
     * @return a new empty queue
     */
    private PriorityQueue<Integer> newQueue() {
        return new PriorityQueue1L<>(INT_ORDER);
    }

    /*
     * -----------------------------------------------------------------------
     * front tests
     * -----------------------------------------------------------------------
     */

    /**
     * {@code front} on a single-element queue should return that element and
     * leave the length unchanged.
     */
    @Test
    public void testFrontSingleElement() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(5);
        Integer result = q.front();
        assertEquals(Integer.valueOf(5), result);
        assertEquals(1, q.length());
    }

    /**
     * {@code front} should return the minimum element without removing it.
     */
    @Test
    public void testFrontReturnsMinimum() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(7);
        q.enqueue(2);
        q.enqueue(9);
        Integer result = q.front();
        assertEquals(Integer.valueOf(2), result);
        assertEquals(3, q.length());
    }

    /**
     * After calling {@code front}, the queue must be unchanged: subsequent
     * dequeues should still return elements in sorted order.
     */
    @Test
    public void testFrontDoesNotModifyQueue() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(4);
        q.enqueue(1);
        q.enqueue(3);
        q.front(); // discard return value; only check side-effects
        assertEquals(3, q.length());
        assertEquals(Integer.valueOf(1), q.dequeue());
        assertEquals(Integer.valueOf(3), q.dequeue());
        assertEquals(Integer.valueOf(4), q.dequeue());
    }

    /**
     * Calling {@code front} twice in a row should return the same value both
     * times.
     */
    @Test
    public void testFrontCalledTwiceReturnsSameValue() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(6);
        q.enqueue(2);
        assertEquals(q.front(), q.front());
        assertEquals(2, q.length());
    }

    /*
     * -----------------------------------------------------------------------
     * back tests
     * -----------------------------------------------------------------------
     */

    /**
     * {@code back} on a single-element queue should return that element and
     * leave the length unchanged.
     */
    @Test
    public void testBackSingleElement() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(5);
        Integer result = q.back();
        assertEquals(Integer.valueOf(5), result);
        assertEquals(1, q.length());
    }

    /**
     * {@code back} should return the maximum element without removing it.
     */
    @Test
    public void testBackReturnsMaximum() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(3);
        q.enqueue(1);
        q.enqueue(9);
        Integer result = q.back();
        assertEquals(Integer.valueOf(9), result);
        assertEquals(3, q.length());
    }

    /**
     * After calling {@code back}, the queue must be unchanged: subsequent
     * dequeues should still return elements in sorted order.
     */
    @Test
    public void testBackDoesNotModifyQueue() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(4);
        q.enqueue(1);
        q.enqueue(3);
        q.back(); // discard return value; only check side-effects
        assertEquals(3, q.length());
        assertEquals(Integer.valueOf(1), q.dequeue());
        assertEquals(Integer.valueOf(3), q.dequeue());
        assertEquals(Integer.valueOf(4), q.dequeue());
    }

    /*
     * -----------------------------------------------------------------------
     * combine tests
     * -----------------------------------------------------------------------
     */

    /**
     * Combining with an empty queue should not change the receiver and should
     * leave the argument empty.
     */
    @Test
    public void testCombineWithEmpty() {
        PriorityQueue<Integer> q1 = newQueue();
        PriorityQueue<Integer> q2 = newQueue();
        q1.enqueue(1);
        q1.enqueue(3);
        q1.combine(q2);
        assertEquals(2, q1.length());
        assertEquals(0, q2.length());
    }

    /**
     * Combining into an empty receiver should transfer all elements from the
     * argument; the argument should be cleared.
     */
    @Test
    public void testCombineIntoEmpty() {
        PriorityQueue<Integer> q1 = newQueue();
        PriorityQueue<Integer> q2 = newQueue();
        q2.enqueue(4);
        q2.enqueue(2);
        q1.combine(q2);
        assertEquals(2, q1.length());
        assertEquals(0, q2.length());
        assertEquals(Integer.valueOf(2), q1.dequeue());
        assertEquals(Integer.valueOf(4), q1.dequeue());
    }

    /**
     * After combining two non-empty queues, the result should contain all
     * elements from both in sorted order, and the argument queue should be
     * empty.
     */
    @Test
    public void testCombineResultSorted() {
        PriorityQueue<Integer> q1 = newQueue();
        PriorityQueue<Integer> q2 = newQueue();
        q1.enqueue(5);
        q1.enqueue(1);
        q2.enqueue(4);
        q2.enqueue(2);
        q2.enqueue(3);
        q1.combine(q2);
        assertEquals(5, q1.length());
        assertEquals(0, q2.length());
        assertEquals(Integer.valueOf(1), q1.dequeue());
        assertEquals(Integer.valueOf(2), q1.dequeue());
        assertEquals(Integer.valueOf(3), q1.dequeue());
        assertEquals(Integer.valueOf(4), q1.dequeue());
        assertEquals(Integer.valueOf(5), q1.dequeue());
    }

    /**
     * Combining two single-element queues should yield a queue with two
     * elements in sorted order.
     */
    @Test
    public void testCombineTwoSingleElement() {
        PriorityQueue<Integer> q1 = newQueue();
        PriorityQueue<Integer> q2 = newQueue();
        q1.enqueue(10);
        q2.enqueue(5);
        q1.combine(q2);
        assertEquals(2, q1.length());
        assertEquals(0, q2.length());
        assertEquals(Integer.valueOf(5), q1.dequeue());
        assertEquals(Integer.valueOf(10), q1.dequeue());
    }

    /*
     * -----------------------------------------------------------------------
     * isEmpty tests
     * -----------------------------------------------------------------------
     */

    /**
     * A newly created queue should be empty.
     */
    @Test
    public void testIsEmptyOnNewQueue() {
        PriorityQueue<Integer> q = newQueue();
        assertTrue(q.isEmpty());
        assertEquals(0, q.length());
    }

    /**
     * After enqueuing one element, the queue should not be empty.
     */
    @Test
    public void testIsEmptyAfterEnqueue() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(1);
        assertFalse(q.isEmpty());
        assertEquals(1, q.length());
    }

    /**
     * After dequeuing the last element, the queue should be empty again.
     */
    @Test
    public void testIsEmptyAfterDequeueAll() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(1);
        q.dequeue();
        assertTrue(q.isEmpty());
    }

    /**
     * After clearing a non-empty queue, it should be empty.
     */
    @Test
    public void testIsEmptyAfterClear() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(1);
        q.enqueue(2);
        q.clear();
        assertTrue(q.isEmpty());
        assertEquals(0, q.length());
    }

    /*
     * -----------------------------------------------------------------------
     * clear tests
     * -----------------------------------------------------------------------
     */

    /**
     * Clearing an already empty queue should leave it empty.
     */
    @Test
    public void testClearAlreadyEmpty() {
        PriorityQueue<Integer> q = newQueue();
        q.clear();
        assertTrue(q.isEmpty());
    }

    /**
     * Clearing a queue with multiple elements should yield an empty queue.
     */
    @Test
    public void testClearMultipleElements() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);
        q.clear();
        assertTrue(q.isEmpty());
        assertEquals(0, q.length());
    }

    /*
     * -----------------------------------------------------------------------
     * equals tests
     * -----------------------------------------------------------------------
     */

    /**
     * Two newly created empty queues should be equal.
     */
    @Test
    public void testEqualsBothEmpty() {
        PriorityQueue<Integer> q1 = newQueue();
        PriorityQueue<Integer> q2 = newQueue();
        assertTrue(q1.equals(q2));
    }

    /**
     * A queue should be equal to itself (reflexivity).
     */
    @Test
    public void testEqualsSameReference() {
        PriorityQueue<Integer> q = newQueue();
        q.enqueue(1);
        q.enqueue(2);
        assertTrue(q.equals(q));
    }

    /**
     * Two queues with the same elements in the same sorted order should be
     * equal.
     */
    @Test
    public void testEqualsSameContents() {
        PriorityQueue<Integer> q1 = newQueue();
        PriorityQueue<Integer> q2 = newQueue();
        q1.enqueue(1);
        q1.enqueue(2);
        q2.enqueue(1);
        q2.enqueue(2);
        assertTrue(q1.equals(q2));
    }

    /**
     * Queues with different lengths should not be equal.
     */
    @Test
    public void testEqualsDifferentLengths() {
        PriorityQueue<Integer> q1 = newQueue();
        PriorityQueue<Integer> q2 = newQueue();
        q1.enqueue(1);
        assertFalse(q1.equals(q2));
    }
}
