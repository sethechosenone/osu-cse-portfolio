package components.priorityqueue;

import java.util.Comparator;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

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
        Sequence<T> hold = new Sequence1L<>();
        for (int i = 0; i < len; i++) {
            hold.add(hold.length(), this.dequeue());
        }
        T result = hold.entry(len - 1);
        for (int i = 0; i < len; i++) {
            this.enqueue(hold.entry(i));
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
                this.enqueue(thisItem);
                pq.enqueue(pqItem);
                if (!thisItem.equals(pqItem)) {
                    return false;
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
        int len = this.length();
        Sequence<T> hold = new Sequence1L<>();
        for (int i = 0; i < len; i++) {
            hold.add(hold.length(), this.dequeue());
        }
        for (int i = 0; i < len; i++) {
            result.append(hold.entry(i));
            if (i < len - 1) {
                result.append(", ");
            }
            this.enqueue(hold.entry(i));
        }
        result.append("}");
        return result.toString();
    }
}
// comment so this file shows up in the PR