import components.priorityqueue.PriorityQueue;
import components.priorityqueue.PriorityQueue1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class PriorityQueueDemo {

    /**
     * Represents a schedulable task with a name and urgency level.
     */
    private static final class Task {

        /**
         * Human-readable task name.
         */
        private final String name;

        /**
         * Urgency level.
         */
        private final int urgency;

        /**
         * Creates a new {@code Task}.
         *
         * @param name
         *            the task name
         * @param urgency
         *            the urgency level (lower = more urgent)
         */
        Task(String name, int urgency) {
            this.name = name;
            this.urgency = urgency;
        }

        @Override
        public String toString() {
            return "[P" + this.urgency + "] " + this.name;
        }
    }

    /**
     * Demonstrates basic integer ordering with a {@code PriorityQueue}.
     *
     * @param out
     *            the output stream
     */
    private static void integerDemo(SimpleWriter out) {
        PriorityQueue<Integer> pq = new PriorityQueue1L<>(Integer::compare);
        pq.enqueue(1);
        pq.enqueue(2);
        out.println("Current state of PriorityQueue: " + pq.toString());

        pq.enqueue(10); // shut up checkstyle idgaf about "magic numbers"
        pq.enqueue(5);
        pq.enqueue(4);
        out.println("Current state of PriorityQueue: " + pq.toString());

        StringBuilder sb = new StringBuilder();
        int len = pq.length();

        for (int i = 0; i < len; i++) {
            sb.append(pq.dequeue());
            if (i < len - 1) {
                sb.append(", ");
            }
        }

        out.println("All dequeued items: " + sb.toString());
    }

    /**
     * Demonstrates using a {@code PriorityQueue} as a task scheduler. Tasks
     * from two separate queues are merged with {@code combine} and then
     * processed in urgency order.
     *
     * @param out
     *            the output stream
     */
    private static void taskSchedulerDemo(SimpleWriter out) {
        final int urgencyHigh = 5;
        final int urgencyMediumHigh = 4;
        final int urgencyMedium = 3;
        final int urgencyMediumLow = 2;
        final int urgencyLow = 1;

        PriorityQueue<Task> queueA = new PriorityQueue1L<>(
                (a, b) -> Integer.compare(b.urgency, a.urgency));
        PriorityQueue<Task> queueB = new PriorityQueue1L<>(
                (a, b) -> Integer.compare(b.urgency, a.urgency));

        queueA.enqueue(new Task("Send weekly report", urgencyMedium));
        queueA.enqueue(new Task("Fix critical production bug", urgencyHigh));
        queueA.enqueue(new Task("Update documentation", urgencyLow));

        queueB.enqueue(new Task("Deploy hotfix", urgencyHigh));
        queueB.enqueue(new Task("Review pull request", urgencyMediumLow));
        queueB.enqueue(
                new Task("Respond to support ticket", urgencyMediumHigh));

        out.println("Queue A: " + queueA);
        out.println("Queue B: " + queueB);

        queueA.combine(queueB);
        out.println("Merged queue: " + queueA);
        out.println("Queue B after combine: " + queueB);

        out.println("Processing tasks in urgency order:");
        while (!queueA.isEmpty()) {
            out.println("  " + queueA.dequeue());
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            command-line arguments (not used)
     */
    public static void main(String[] args) {
        SimpleWriter stdout = new SimpleWriter1L();

        stdout.println("=== Integer Demo ===");
        integerDemo(stdout);

        stdout.println();
        stdout.println("=== Task Scheduler Demo ===");
        taskSchedulerDemo(stdout);

        stdout.close();
    }
}
