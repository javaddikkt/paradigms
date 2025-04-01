package queue;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;


// Код класса ArrayQueue для дз-3 в предыдущем коммите
public class ArrayQueue extends AbstractQueue{
    private Object[] elements;
    private int head, tail;

    // pre:  true
    // post: n' = 0
    public ArrayQueue() {
        super();
        elements = new Object[2];
        head = 0;
        tail = 0;
    }

    @Override
    protected void clearImpl() {
        // :NOTE: удаление не отвязывает неиспользуемые ссылки (+)
        while (!isEmpty()) {
            dequeue();
        }
        head = 0;
        tail = 0;
    }

    @Override
    protected Object elementImpl() {
        return elements[head];
    }

    @Override
    public void enqueueImpl(Object x) {
        tail = (tail + 1) % elements.length;
        if (tail == head) {
            increase();
        }
        if (tail == 0) {
            elements[elements.length - 1] = x;
        } else {
            elements[tail - 1] = x;
        }
    }

    @Override
    protected Object dequeueImpl() {
        Object ans = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return ans;
    }

    // pre:  cond != null
    // post: R = i: 0 <= i <= n-1 && i = min(j): cond.test(q[j+1]) || R = -1
    public int indexIf(Predicate<Object> cond) {
        int i = 0;
        while (i < size) {
            if (cond.test(elements[(head + i) % elements.length])) {
                return i;
            }
            i++;
        }
        return -1;
    }

    // pre:  cond != null
    // post: R = i: 0 <= i <= n-1 && i = max(j): cond.test(q[j+1]) || R = -1
    public int lastIndexIf(Predicate<Object> cond) {
        int i = 0, p;
        while (i < size) {
            p = tail - i - 1;
            if (p < 0) {
                p = elements.length + p;
            }
            if (cond.test(elements[p])) {
                return size - i - 1;
            }
            i++;
        }
        return -1;
    }

    // pre:  true
    // post: immutable(n) && n' = n
    private void increase() {
        if (head < tail) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        } else {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, tail + 1);
            System.arraycopy(elements, head, newElements, elements.length + head, elements.length - head);
            head += elements.length;
            elements = newElements;
        }
    }


}
