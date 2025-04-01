package queue;

public class MyArrayQueueTest {
    public static void main(String[] args) {
        System.out.println("Testing: ArrayQueue");
        ArrayQueue queue = new ArrayQueue();
        fill(queue);
        assert queue.size() == 5;
        dump(queue);
        assert queue.isEmpty();
        fill(queue);
        assert queue.size() == 5;
        queue.clear();
        assert queue.isEmpty();
        System.out.println("ArrayQueue tests: SUCCEED");
    }

    public static void fill(ArrayQueue queue) {
        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(
                    queue.element() + " " +
                    queue.dequeue() + " " +
                    queue.size()
            );
        }
    }
}
