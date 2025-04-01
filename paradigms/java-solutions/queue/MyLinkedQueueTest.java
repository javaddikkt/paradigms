package queue;

public class MyLinkedQueueTest {
    public static void main(String[] args) {
        LinkedQueue q = new LinkedQueue();
        for (int i = 0; i < 5; i++) {
            q.enqueue(i);
        }
        for (int i = 0; i < 5; i++) {
            q.enqueue(i);
        }
        for (int i = 0; i < 5; i++) {
            q.enqueue(i);
        }
        q.distinct();
        assert q.size() == 5;
    }
}
