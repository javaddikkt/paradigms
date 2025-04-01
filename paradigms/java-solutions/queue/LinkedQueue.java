package queue;

import java.util.Objects;

public class LinkedQueue extends AbstractQueue{
    private static class Node {
        private Node next;
        private final Object key;

        public Node(Object key, Node next) {
            Objects.requireNonNull(key);
            this.next = next;
            this.key = key;
        }
    }

    private Node head, tail;

    // pre:  true
    // post: n' = 0
    public LinkedQueue() {
        // :NOTE: инициализация в AbstractQueue (+)
        super();
        head = null;
        tail = null;
    }

    @Override
    protected void clearImpl() {
        head = null;
        tail = null;
    }

    @Override
    protected Object elementImpl() {
        return head.key;
    }

    @Override
    protected void enqueueImpl(Object x) {
        Node node = new Node(x, null);
        if (head == null) {
            head = node;
        }
        if (tail != null) {
            tail.next = node;
        }
        tail = node;
    }

    @Override
    protected Object dequeueImpl() {
        Object ans = head.key;
        head = head.next;
        return ans;
    }
}
