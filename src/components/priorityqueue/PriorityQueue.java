package components.priorityqueue;

/**
 * Priority queue secondary methods.
 *
 * @param <T>
 *            the type of all the elements in {@code this}
 */
public interface PriorityQueue<T> extends PriorityQueueKernel<T> {
    /**
     * Returns the first item in {@code this}.
     *
     * @return the first item in {@code this}.
     * @requires this != empty_string
     * @ensures for all x in this, order.compare(front, x) <= 0
     */
    T front();

    /**
     * Returns the last item in {@code this}.
     *
     * @return the last item in {@code this}.
     * @requires this != empty_string
     * @ensures for all x in this, order.compare(front, x) >= 0
     */
    T back();

    /**
     * Appends {@code q} to {@code this} and reorganizes the elements according
     * to {@code this.comp}.
     *
     * @param pq
     * @updates this
     * @clears pq
     * @ensures this = #this * <pq> and perms(this, #this)
     */
    void combine(PriorityQueue<T> pq);

    /**
     * Reports whether {@code this} is empty.
     *
     * @return true if this is empty
     * @ensures isEmpty = (|this| = 0)
     */
    boolean isEmpty();

    /**
     * Removes all elements from {@code this}.
     *
     * @clears this
     * @ensures this = empty_string
     */
    void clear();
}
