package queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

// Model: q[1..n]
// I: n >= 0 && for all i=1..n: q[i] != null
// Let immutable(j): i=1..j: q'[i] = q[i]
public class ArrayQueueModule {
    private static Object[] elements = new Object[2];
    private static int head = 0, tail = 0;

    // :NOTE: immutable?
    // pred: true
    // post: R = (n == 0)
    public static boolean isEmpty() {
        return size() == 0;
    }

    // :NOTE: immutable?
    // pred: true
    // post: R = n
    public static int size() {
        if (head <= tail) {
            return tail - head;
        } else {
            return elements.length - head + tail;
        }
    }

    // pred: true
    // post: n' = 0
    public static void clear() {
        // :NOTE: зачищение ссылок?
        head = 0;
        tail = 0;
    }

    // pred: n > 0
    // post: R = q[1] && n' = n && immutable(n)
    public static Object element() {
        assert size() > 0;
        return elements[head];
    }

    // pred: x != null
    // post: n' = n + 1 && q'[n'] = x && immutable(n)
    public static void enqueue(Object x) {
        Objects.requireNonNull(x);
        tail = (tail + 1) % elements.length;
        if (tail == head) {
            increase();
        }
        // :NOTE: звучит как костыль
        if (tail == 0) {
            elements[elements.length - 1] = x;
        } else {
            elements[tail - 1] = x;
        }
    }

    // pred: n > 0
    // post: R = q[1] && n' = n - 1 && i=2..n: q'[i - 1] = q[i]
    public static Object dequeue() {
        assert size() > 0;
        Object ans = elements[head];
        head = (head + 1) % elements.length;
        return ans;
    }

    // pred: cond != null
    // post: R = i: 0 <= i <= n-1 && i = min(j): cond.test(q[j+1]) || R = -1
    public static int indexIf(Predicate<Object> cond) {
        int i = 0;
        while (i < size()) {
            if (cond.test(elements[(head + i) % elements.length])) {
                return i;
            }
            i++;
        }
        return -1;
    }

    // pred: cond != null
    // post: R = i: 0 <= i <= n-1 && i = max(j): cond.test(q[j+1]) || R = -1
    public static int lastIndexIf(Predicate<Object> cond) {
        int i = 0, p;
        while (i < size()) {
            p = tail - i - 1;
            if (p < 0) {
                p = elements.length + p;
            }
            if (cond.test(elements[p])) {
                return size() - i - 1;
            }
            i++;
        }
        return -1;
    }

    // pred: true
    // post: immutable(n) && n' = n
    private static void increase() {
        if (head < tail) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        } else {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, tail + 1);
            System.arraycopy(elements, head, newElements, elements.length + head,
                    elements.length - head);

            head += elements.length;
            // :NOTE: зачем копировать?
            elements = Arrays.copyOf(newElements, newElements.length);
        }
    }
}
