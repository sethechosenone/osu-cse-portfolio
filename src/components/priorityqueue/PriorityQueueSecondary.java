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
    public final boolean equals(PriorityQueue<T> pq) {
        if (this == pq) {
            return true;
        }
        if (this.length() == pq.length()) {
            for (int i = 0; i < this.length(); i++) {
                T thisItem = this.dequeue();
                T pqItem = pq.dequeue();
                if (!thisItem.equals(pqItem)) {
                    break;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final int hashCode() {
        return this.length();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{");
        for (int i = 0; i < this.length(); i++) {
            if (i == this.length() - 1) {
                result.append(this.dequeue());
            } else {
                result.append(this.dequeue() + ", ");
            }
        }
        result.append("}");
        return result.toString();
    }
}
// comment so this file shows up in the PR