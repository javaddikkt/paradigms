package queue;

public class MyArrayQueueADTTest {
    public static void main(String[] args) {
        System.out.println("Testing: ArrayQueueADT");
        ArrayQueueADT queue = new ArrayQueueADT();
        fill(queue);
        assert ArrayQueueADT.size(queue) == 5;
        dump(queue);
        assert ArrayQueueADT.isEmpty(queue);
        fill(queue);
        assert ArrayQueueADT.size(queue) == 5;
        ArrayQueueADT.clear(queue);
        assert ArrayQueueADT.isEmpty(queue);
        System.out.println("ArrayQueueADT tests: SUCCEED");
    }

    public static void fill(ArrayQueueADT queue) {
        for (int i = 0; i < 5; i++) {
            ArrayQueueADT.enqueue(queue, i);
        }
    }

    public static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                    ArrayQueueADT.element(queue) + " " +
                    ArrayQueueADT.dequeue(queue) + " " +
                    ArrayQueueADT.size(queue)
            );
        }
    }
}
