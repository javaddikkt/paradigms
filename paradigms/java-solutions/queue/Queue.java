package queue;

// Model: q[1]..q[n]
// I: n >= 0 && for all i=1..n: q[i] != null
// Let immutable(j): i=1..j: q'[i] = q[i]
public interface Queue {

    // pre:  true
    // :NOTE: immutable (+)
    // :NOTE: queue = [1,3,4]; isEmpty(); queue = [1,3,4,5]; (+)
    // post: R = (n == 0) && immutable(n) && n' = n
    boolean isEmpty();

    // pre:  true
    // :NOTE: immutable size? (+)
    // post: R = n && immutable(n) && n' = n
    int size();

    // pre:  true
    // post: n' = 0
    void clear();

    // pre:  n > 0
    // post: R = q[0] && n' = n && immutable(n)
    Object element();

    // pre:  x != null
    // post: n' = n + 1 && q'[n'] = x && immutable(n)
    void enqueue(Object x);

    // pre:  n > 0
    // post: R = q[1] && n' = n - 1 && i=2..n: q'[i - 1] = q[i]
    Object dequeue();

    // pre:  true
    // post: for all i:1..n' for all j:1..n', j != i  q'[i] != q'[j] &&
    //       for all i:1..n q' contains q[i] &&
    //       for all i:1..n' q contains q'[i] &&
    //       for all i:1..n' for all j:i+1..n' q.indexOf(q'[i]) < q.indexOf(q'[j])
    void distinct();

}
