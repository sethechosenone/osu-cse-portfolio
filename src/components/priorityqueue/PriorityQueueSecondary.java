package components.priorityqueue;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Priority queue implementations for secondary methods.
 *
 * @param <T>
 *            the type of all the elements in {@code this}
 */
public abstract class PriorityQueueSecondary<T> implements PriorityQueue<T> {
    @Override
    public abstract void enqueue(T x);

    @Override
    public abstract T dequeue();

    @Override
    public abstract int length();

    @Override
    public abstract void setComparator(Comparator<T> comp);

    @Override
    public final T front() {
        T result = this.dequeue();
        this.enqueue(result);
        return result;
    }

    @Override
    public final T back() {
        int len = this.length();
        ArrayList<T> hold = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            hold.add(this.dequeue());
        }
        T result = hold.get(len - 1);
        for (T item : hold) {
            this.enqueue(item);
        }
        return result;
    }

    @Override
    public final void combine(PriorityQueue<T> pq) {
        while (pq.length() > 0) {
            this.enqueue(pq.dequeue());
        }
    }

    @Override
    public final boolean isEmpty() {
        return this.length() == 0;
    }

    @Override
    public final void clear() {
        while (!this.isEmpty()) {
            this.dequeue();
        }
    }
}
