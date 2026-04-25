package components.priorityqueue;

import java.util.Comparator;

/**
 * {@code PriorityQueue} represented as a heap.
 *
 * @param <T>
 *            the type of all the elements in {@code this}
 */
public class PriorityQueue2<T> extends PriorityQueueSecondary<T> {
    private T[] rep;

    private int length;

    private Comparator<T> comp;

    /**
     * Exchanges entries at indices {@code i} and {@code j} of {@code array}.
     *
     * @param <T>
     *            type of array entries
     * @param array
     *            the array whose entries are to be exchanged
     * @param i
     *            one index
     * @param j
     *            the other index
     * @updates array
     * @requires 0 <= i < |array| and 0 <= j < |array|
     * @ensures array = [#array with entries at indices i and j exchanged]
     */
    private static <T> void exchangeEntries(T[] array, int i, int j) {
        assert array != null : "Violation of: array is not null";
        assert 0 <= i : "Violation of: 0 <= i";
        assert i < array.length : "Violation of: i < |array|";
        assert 0 <= j : "Violation of: 0 <= j";
        assert j < array.length : "Violation of: j < |array|";

        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Given an array that represents a complete binary tree and an index
     * referring to the root of a subtree that would be a heap except for its
     * root, sifts the root down to turn that whole subtree into a heap.
     *
     * @param <T>
     *            type of array entries
     * @param array
     *            the complete binary tree
     * @param top
     *            the index of the root of the "subtree"
     * @param last
     *            the index of the last entry in the heap
     * @param order
     *            total preorder for sorting
     * @updates array
     * @requires <pre>
     * 0 <= top  and  last < |array|  and
     * for all i: integer
     *     where (0 <= i  and  i < |array|)
     *   ([entry at position i in array is not null])  and
     * [subtree rooted at {@code top} is a complete binary tree]  and
     * SUBTREE_IS_HEAP(array, 2 * top + 1, last,
     *     [relation computed by order.compare method])  and
     * SUBTREE_IS_HEAP(array, 2 * top + 2, last,
     *     [relation computed by order.compare method])  and
     * IS_TOTAL_PREORDER([relation computed by order.compare method])
     * </pre>
     * @ensures <pre>
     * SUBTREE_IS_HEAP(array, top, last,
     *     [relation computed by order.compare method])  and
     * perms(array, #array)  and
     * SUBTREE_ARRAY_ENTRIES(array, top, last) =
     *  SUBTREE_ARRAY_ENTRIES(#array, top, last)  and
     * [the other entries in array are the same as in #array]
     * </pre>
     */
    private static <T> void siftDown(T[] array, int top, int last,
            Comparator<T> order) {
        assert array != null : "Violation of: array is not null";
        assert order != null : "Violation of: order is not null";
        assert 0 <= top : "Violation of: 0 <= top";
        assert last < array.length : "Violation of: last < |array|";
        for (int i = 0; i < array.length; i++) {
            assert array[i] != null : ""
                    + "Violation of: all entries in array are not null";
        }
        assert isHeap(array, 2 * top + 1, last, order) : ""
                + "Violation of: SUBTREE_IS_HEAP(array, 2 * top + 1, last,"
                + " [relation computed by order.compare method])";
        assert isHeap(array, 2 * top + 2, last, order) : ""
                + "Violation of: SUBTREE_IS_HEAP(array, 2 * top + 2, last,"
                + " [relation computed by order.compare method])";
        /*
         * Impractical to check last requires clause; no need to check the other
         * requires clause, because it must be true when using the array
         * representation for a complete binary tree.
         */

        int left = 2 * top + 1;
        if (left <= last) {
            int right = left + 1;
            int smaller = left;
            if (right <= last && order.compare(array[right], array[left]) < 0) {
                smaller = right;
            }
            if (order.compare(array[top], array[smaller]) > 0) {
                exchangeEntries(array, top, smaller);
                siftDown(array, smaller, last, order);
            }
        }
    }

    private static <T> void siftUp(T[] array, int top, Comparator<T> order) {
        int parent = (top - 1) / 2;
        while (top > 0 && order.compare(array[top], array[parent]) < 0) {
            exchangeEntries(array, top, parent);
            top = parent;
            parent = (top - 1) / 2;
        }
    }

    /**
     * Checks if the subtree of the given {@code array} rooted at the given
     * {@code top} is a heap.
     *
     * @param <T>
     *            type of array entries
     * @param array
     *            the complete binary tree
     * @param top
     *            the index of the root of the "subtree"
     * @param last
     *            the index of the last entry in the heap
     * @param order
     *            total preorder for sorting
     * @return true if the subtree of the given {@code array} rooted at the
     *         given {@code top} is a heap; false otherwise
     * @requires <pre>
     * 0 <= top  and  last < |array|  and
     * for all i: integer
     *     where (0 <= i  and  i < |array|)
     *   ([entry at position i in array is not null])  and
     * [subtree rooted at {@code top} is a complete binary tree]
     * </pre>
     * @ensures <pre>
     * isHeap = SUBTREE_IS_HEAP(array, top, last,
     *     [relation computed by order.compare method])
     * </pre>
     */
    private static <T> boolean isHeap(T[] array, int top, int last,
            Comparator<T> order) {
        assert array != null : "Violation of: array is not null";
        assert 0 <= top : "Violation of: 0 <= top";
        assert last < array.length : "Violation of: last < |array|";
        for (int i = 0; i < array.length; i++) {
            assert array[i] != null : ""
                    + "Violation of: all entries in array are not null";
        }
        /*
         * No need to check the other requires clause, because it must be true
         * when using the Array representation for a complete binary tree.
         */
        int left = 2 * top + 1;
        boolean isHeap = true;
        if (left <= last) {
            isHeap = (order.compare(array[top], array[left]) <= 0)
                    && isHeap(array, left, last, order);
            int right = left + 1;
            if (isHeap && (right <= last)) {
                isHeap = (order.compare(array[top], array[right]) <= 0)
                        && isHeap(array, right, last, order);
            }
        }
        return isHeap;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] save = this.rep;
        this.rep = (T[]) new Object[this.rep.length * 2]; // cannot fail but java is dumb
        for (int i = 0; i < save.length; i++) {
            this.rep[i] = save[i];
        }
    }

    @SuppressWarnings("unchecked")
    private void createNewRep(int size) {
        this.rep = (T[]) new Object[size]; // same case here
        this.length = 0;
    }

    public PriorityQueue2(Comparator<T> comp) {
        this.createNewRep(1);
        this.comp = comp;
    }

    public PriorityQueue2(Comparator<T> comp, int size) {
        this.createNewRep(size);
        this.comp = comp;
    }

    @Override
    public void enqueue(T x) {
        if (this.length == this.rep.length) {
            this.resize();
        }
        this.rep[this.length] = x;
        this.length++;
        siftUp(this.rep, this.length - 1, this.comp);
    }

    @Override
    public T dequeue() {
        T result = this.rep[0];
        exchangeEntries(this.rep, this.length - 1, 0);
        this.rep[this.length - 1] = null;
        this.length--;
        if (this.length > 0) {
            siftDown(this.rep, 0, this.length - 1, this.comp);
        }
        return result;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public void setComparator(Comparator<T> comp) {
        this.comp = comp;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.rep.length; i++) {
            this.rep[i] = null;
        }
        this.length = 0;
    }
}
