package queue;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public abstract class AbstractQueue implements Queue{
    protected int size;

    protected AbstractQueue() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        clearImpl();
        size = 0;
    }

    protected abstract void clearImpl();

    public Object element() {
        assert !isEmpty();
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public void enqueue(Object x) {
        Objects.requireNonNull(x);
        enqueueImpl(x);
        size++;
    }

    protected abstract void enqueueImpl(Object x);

    public Object dequeue() {
        assert size > 0;
        size--;
        return dequeueImpl();
    }

    protected abstract Object dequeueImpl();

    public void distinct() {
        Set<Object> set = new HashSet<>();
        Object el;
        int cnt = 0;
        int prevSize = size;
        while (cnt < prevSize) {
            el = dequeue();
            if (!set.contains(el)) {
                set.add(el);
                enqueue(el);
            }
            cnt++;
        }
        size = set.size();
    }

}
