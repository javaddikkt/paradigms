package search;

public class BinarySearch {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        int i = 0;
        // ???
        while (i < args.length - 1) {
            // i < args.length - 1
            // i + 1 < args.length
            int b = Integer.parseInt(args[i + 1]);
            // b = args[i + 1]
            a[i] = b;
            //a[i] = args[i + 1]
            i++;
            // ???
        }
        // forall i: 0 .. args.length - 2   a[i] = args[i + 1]

        if (a.length > 0) {
            System.out.println(recursiveBinSearch(x, a, 0, a.length));
            //System.out.println(iterativeBinSearch(x, a));
        } else {
            System.out.println("0");
        }
    }

    // Контракт:
    // Pred: P: a is not empty && a is sorted in descending order
    // Post: R = min(i): a[i] <= x
    static int iterativeBinSearch(int x, int[] a) {
        // true
        int l = 0;
        // l' = 0
        int r = a.length;
        // r' = a.length
        int m;
        // I: P && 0 <= l' <= r' <= a.length - 1
        while (l < r) {
            // P && r' > l'
            m = (l + r) / 2;
            // m' = (r' + l') / 2
            // 2m' = r' + l'
            // 2l' <= 2m' <= 2r'
            // l' <= m' <= r'  =>  0 <= m' <= a.length - 1, m := m'
            // P && 0 <= l' <= m <= r' <= a.length - 1
            if (a[m] > x) {
                // P && 0 <= l' <= m <= r' <= a.length - 1
                // a[0] >= ... >= a[m] > a[x] >= ... >= a[r] >= a[a.length - 1]
                // a[m + 1] >= a[x] >= ... >= a[r] >= a[a.length - 1]
                l = m + 1;
                // l' = m + 1 => a[l'] >= a[x] >= a[r']
            } else {
                // P && 0 <= l' <= m <= r' <= a.length - 1
                // a[0] >= a[l] >= ... >= a[x] >= a[m] >= ... >= a[r] >= a[a.length - 1]
                // a[l] >= a[x] >= a[m] >= a[r]
                r = m;
                // r' = m => a[l'] >= a[x] >= a[r']
            }
            // P && 0 <= l' <= r' <= a.length - 1 && a[l'] >= a[x] >= a[r']
        }
        // P && 0 <= r' <= l' <= a.length - 1 && a[l'] >= a[x] >= a[r']
        return l;
        // l' = min(i): a[i] <= x

    }

    // Контракт:
    // Pred: P: a is not empty && a is sorted in descending order && 0 <= l <= r <= a.length
    // Post: R = min(i): a[i] <= x
    static int recursiveBinSearch(int x, int[] a, int l, int r) {
        if (r == l) {
            return l;
        }
        int m = (l + r) / 2;
        if (a[m] > x) {
            return recursiveBinSearch(x, a, m + 1, r);
        } else {
            return recursiveBinSearch(x, a, l, m);
        }
    }
}
