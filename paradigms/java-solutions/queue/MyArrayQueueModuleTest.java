package queue;

public class MyArrayQueueModuleTest {
    public static void main(String[] args) {
        System.out.println("Testing: ArrayQueueModule");
        fill();
        assert ArrayQueueModule.size() == 5;
        dump();
        assert ArrayQueueModule.isEmpty();
        fill();
        assert ArrayQueueModule.size() == 5;
        ArrayQueueModule.clear();
        assert ArrayQueueModule.isEmpty();
        System.out.println("ArrayQueueModule tests: SUCCEED");
    }

    public static void fill() {
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    ArrayQueueModule.element() + " " +
                    ArrayQueueModule.dequeue() + " " +
                    ArrayQueueModule.size()
            );
        }
    }
}
