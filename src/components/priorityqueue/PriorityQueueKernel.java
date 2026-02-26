package components.priorityqueue;

import java.util.Comparator;

/**
 * Priority queue kernel methods.
 *
 * @param <T>
 *            the type of all the elements in {@code this}
 */
public interface PriorityQueueKernel<T> {
    /**
     * Adds {@code x} to the end of {@code this}.
     *
     * @param x
     *            the item to be added
     * @updates this
     * @ensures this = #this * <x>
     */
    void enqueue(T x);

    /**
     * Removes the front-most element of {@code this}.
     *
     * @return the removed item
     * @updates this
     * @requires this != empty_string
     * @ensures this = <dequeue> * this and for all y in this,
     *          order.compare(dequeue, y) <= 0
     */
    T dequeue();

    /**
     * Returns the number of items in {@code this}.
     *
     * @return the number of items in {@code this}
     * @ensures length = |this|
     */
    int length();

    /**
     * Sets the comparator used to organize the items of {@code this}, and
     * reoranizes the elements accordingly.
     *
     * @param comp
     *            the new comparator to use
     * @updates this
     * @ensures this.order = comp and perms(this, #this)
     */
    void setComparator(Comparator<T> comp);
}
