package queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

// Model: q[1..n]
// I: n >= 0 && for all i=1..n: q[i] != null
// Let immutable(j): i=1..j: q'[i] = q[i]
public class ArrayQueueADT {
    private Object[] elements = new Object[2];
    private int head = 0, tail = 0;

    // pred: true
    // post: R.n = 0
    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }

    // pred: queue != null
    // post: R = (n == 0)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return size(queue) == 0;
    }

    // pred: queue != null
    // post: R = n
    public static int size(ArrayQueueADT queue) {
        if (queue.head <= queue.tail) {
            return queue.tail - queue.head;
        } else {
            return queue.elements.length - queue.head + queue.tail;
        }
    }

    // pred: queue != null
    // post: n' = 0
    public static void clear(ArrayQueueADT queue) {
        queue.head = 0;
        queue.tail = 0;
    }

    // pred: queue != null && n > 0
    // post: R = q[0] && n' = n && immutable(n)
    public static Object element(ArrayQueueADT queue) {
        assert size(queue) > 0;
        return queue.elements[queue.head];
    }

    // pred: queue != null && x != null
    // post: n' = n + 1 && q'[n'] = x && immutable(n)
    public static void enqueue(ArrayQueueADT queue, Object x) {
        Objects.requireNonNull(x);
        queue.tail = (queue.tail + 1) % queue.elements.length;
        if (queue.tail == queue.head) {
            increase(queue);
        }
        if (queue.tail == 0) {
            queue.elements[queue.elements.length - 1] = x;
        } else {
            queue.elements[queue.tail - 1] = x;
        }
    }

    // pred: queue != null && n > 0
    // post: R = q[1] && n' = n - 1 && i=2..n: q'[i - 1] = q[i]
    public static Object dequeue(ArrayQueueADT queue) {
        assert size(queue) > 0;
        Object ans = queue.elements[queue.head];
        queue.head = (queue.head + 1) % queue.elements.length;
        return ans;
    }

    // pred: queue != null && cond != null
    // post: R = i: 0 <= i <= n-1 && i = min(j): cond.test(q[j+1]) || R = -1
    public static int indexIf(ArrayQueueADT queue, Predicate<Object> cond) {
        int i = 0;
        while (i < size(queue)) {
            if (cond.test(queue.elements[(queue.head + i) % queue.elements.length])) {
                return i;
            }
            i++;
        }
        return -1;
    }

    // pred: queue != null && cond != null
    // post: R = i: 0 <= i <= n-1 && i = max(j): cond.test(q[j+1]) || R = -1
    public static int lastIndexIf(ArrayQueueADT queue, Predicate<Object> cond) {
        int i = 0, p;
        while (i < size(queue)) {
            p = queue.tail - i - 1;
            if (p < 0) {
                p = queue.elements.length + p;
            }
            if (cond.test(queue.elements[p])) {
                return size(queue) - i - 1;
            }
            i++;
        }
        return -1;
    }
    
    // pred: queue != null
    // post: immutable(n) && n' = n
    private static void increase(ArrayQueueADT queue) {
        if (queue.head < queue.tail) {
            queue.elements = Arrays.copyOf(queue.elements, queue.elements.length * 2);
        } else {
            Object[] newElements = new Object[queue.elements.length * 2];
            System.arraycopy(queue.elements, 0, newElements, 0, queue.tail + 1);
            System.arraycopy(queue.elements, queue.head, newElements, queue.elements.length + queue.head,
                    queue.elements.length - queue.head);
            queue.head += queue.elements.length;
            queue.elements = Arrays.copyOf(newElements, newElements.length);
        }
    }
}
