package components.priorityqueue;

import java.util.Comparator;

import components.queue.Queue;
import components.queue.Queue1L;

/**
 * {@code PriorityQueue} represented as {@link components.queue.Queue1L Queue}.
 *
 * @param <T>
 *            the type of all the elements in {@code this}
 */
public final class PriorityQueue1L<T> extends PriorityQueueSecondary<T> {
    /**
     * {@link components.queue.Queue1L Queue} representation of {@code this},
     * and comparator to use for organization.
     */
    private Queue<T> rep;

    /**
     * The comparator to use to organize the elements of {@code this}.
     */
    private Comparator<T> comp;

    /**
     * Private method for instantiating the representation.
     *
     * @param c
     *            the comparator to use for the new representation
     */
    private void createNewRep(Comparator<T> c) {
        this.rep = new Queue1L<>();
        this.comp = c;
    }

    /**
     * Constructor with initial comparator.
     *
     * @param c
     *            the comparator to use for {@code this}
     */
    public PriorityQueue1L(Comparator<T> c) {
        this.createNewRep(c);
    }

    @Override
    public void enqueue(T x) {
        boolean found = false;
        for (int i = 0; i < this.rep.length(); i++) {
            T check = this.rep.dequeue();
            if (!found && this.comp.compare(x, check) <= 0) {
                this.rep.enqueue(x);
                found = true;
            }
            this.rep.enqueue(check);
        }
        if (!found) {
            this.rep.enqueue(x);
        }
    }

    @Override
    public T dequeue() {
        return this.rep.dequeue();
    }

    @Override
    public int length() {
        return this.rep.length();
    }

    @Override
    public void setComparator(Comparator<T> comp) {
        this.comp = comp;
        this.rep.sort(this.comp);
    }
}
