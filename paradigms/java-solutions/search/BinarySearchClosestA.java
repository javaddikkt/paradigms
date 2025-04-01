package search;

public class BinarySearchClosestA {
    // Контракт:
    // Pred: args.length >= 1 && for i: 2..args.length-1  args[i - 1] <= args[i]
    // Post: R = args[i] : abs(args[i] - args[0]) <= abs(args[j] - args[0])  for j: 1..args.length
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        int sum = 0;
        int i = 0;
        // I: sum' == sum(args[1:i]) && i <= args.length
        while (i < args.length - 1) {
            // i < args.length - 1
            // i + 1 < args.length
            a[i] = Integer.parseInt(args[i + 1]);
            // a[i] = args[i + 1]
            sum += a[i];
            // sum' = args[1] + .. + a[i] = sum(args[1:i])
            // i + 1 <= args.length - 1
            i++;
            // i = i + 1 && i <= args.length - 1
            // sum' == sum(args[1:i]) && i <= args.length
        }
        // i >= args.length - 1
        // for i: 0..args.length - 2   a[i] = args[i + 1] && sum = sum(args[1:])
        // true
        if (a.length > 0) {
            // a.length > 0
            if (sum % 2 == 0) {
                // a.length > 0 && sum % 2 == 0
                System.out.println(a[recursiveBinSearch(x, a, 0, a.length - 1)]);
                // out = R
            } else {
                // a.length > 0 && sum % 2 != 0
                System.out.println(iterativeBinSearch(x, a));
                // out = R
            }
        } else {
            // a.length == 0
            System.out.println("0");
            // out = 0
        }
        //
    }


    // Контракт
    // Pred: P := (a is not empty && a is sorted in ascending order)
    // Post: R = a[i] : abs(a[i] - x) <= abs(a[j] - x) for all j: 0 ... a.length - 1
    static int iterativeBinSearch(int x, int[] a) {
        int m;
        // true
        int l = 0;
        // l = 0
        // true
        int r = a.length - 1;
        // r = a.length - 1
        // P
        if (a[0] >= x) {
            // x <= a[0] && a[0] <= a[j] for all j: 0 ... a.length - 1
            return a[0];
            // a[0] - x <= a[j] - x for all j: 0 ... a.length - 1 #
        }
        // P
        if (a[a.length - 1] <= x) {
            // x >= a[a.length - 1] && a[a.length - 1] >= a[j] for all j: 0 ... a.length - 1
            return a[a.length - 1];
            // x - a[a.length - 1] <= x - a[j] for all j: 0 ... a.length - 1 #
        }
        // P1 := (a[0] < x < a[a.length - 1])
        // l' := l, r' := r
        // I = P && P1 && r' - l' >= -1 && a[l'] <= x <= a[r']
        while (l <= r) {
            // P && P1 && 0 <= l' <= r' <= a.length - 1
            m = (l + r) / 2;
            // m' = (r' + l') / 2
            // 2m' = r' + l'
            // 2l' <= 2m' <= 2r'
            // l' <= m' <= r'  =>  0 <= m' <= a.length - 1, m := m'
            // P && P1 && 0 <= m <= a.length - 1
            if (a[m] >= x && (m > 0 && a[m - 1] <= x)) {
                // P && P1 && 0 <= m <= a.length - 1 && a[m] >= x && m > 0 && a[m - 1] <= x
                // P && P1 && a[m - 1] <= x <= a[m] && a < m <= a.length - 1
                if (Math.abs(a[m] - x) < Math.abs(x - a[m - 1])) {
                    // P && P1 && a[m - 1] <= x <= a[m] && a < m <= a.length - 1 && abs(a[m] - x) < abs(a[m - 1] - x)
                    return a[m];
                    // a[0] < a[1] ... <= a[m - 1] <= x <= a[m] <= a[m + 1] ... < a[a.length - 1]
                    // abs(a[0] - x) >= abs(a[1] - x) ... >= abs(a[m - 1] - x) >= abs(a[m] - x) ... <= abs(a[a.length - 1] - x)
                    // abs(a[m] - x) <= abs(a[j] - x) for all j : 0 ... a.length - 1 #
                } else {
                    // P && P1 && Math.abs(a[m] - x) >= Math.abs(x - a[m - 1])
                    return a[m - 1];
                    // a[0] < a[1] ... <= a[m - 1] <= x <= a[m] <= a[m + 1] ... < a[a.length - 1]
                    // abs(a[0] - x) >= abs(a[1] - x) ... >= abs(a[m - 1] - x) <= abs(a[m] - x) ... <= abs(a[a.length - 1] - x)
                    // abs(a[m - 1] - x) <= abs(a[j] - x) for all j : 0 ... a.length - 1 #
                }
            } else if (a[m] < x) {
                // P && P1 && a[m] < x && (a[m] < x || m <= 0 || a[m - 1] > x)
                // P && P1 && a[m] < x
                // a[m] < x < a[a.length - 1]
                // a[m + 1] <= x < a[a.length - 1] && m + 1 < a.length - 1
                l = m + 1;
                // l' = m + 1
                // l' < a.length - 1
                // a[m + 1] <= x < a[a.length - 1]
                // a[l'] <= x < a[a.length - 1], l := l'
            } else {
                // P && P1 && a[m] >= x && (a[m] < x || m <= 0 || a[m - 1] > x)
                // P && P1 && a[m] >= x && x < a[m - 1]
                // P && a[0] < x < a[m - 1] <= a[m] < a[a.length - 1]
                // P && a[0] < x < a[m - 1] <= a[m] < a[a.length - 1] && 0 < m - 1
                r = m - 1;
                // r' = m - 1
                // 0 < r'
                // a[0] < x < a[r'], r := r'
            }
            // P && a[0] < a[l] <= x < a[r] < a[a.length - 1] && r - l >= -1
        }
        // P && a[0] < a[l] <= x < a[r] < a[a.length - 1] && l == r + 1
        return a[l - 1];
        // abs(a[l - 1] - x) = abs(a[r] - x) <= abs(a[i] - x) for all i: 0 ... a.length - 1
    }

    // Контракт:
    // Pred: P := (a.length > 0 && for all i: 1 .. a.length - 1 a[i - 1] <= a[i] && 0 <= l <= r <= a.length -1 &&
    // a[l] <= x <= a[r])
    // Post: R = i: abs(a[i] - x) < abs(a[j] - x) for all j: 0 ... a.length - 1)
    static int recursiveBinSearch(int x, int[] a, int l, int r) {
        // P
        if (a[0] >= x) {
            // P && a[0] >= x
            return 0;
            // x <= a[0] <= a[1] ...
            // a[0] - x <= a[1] - x ... #
        }
        // P
        if (a[a.length - 1] <= x) {
            // P && a[a.length - 1] <= x
            return a.length - 1;
            // a[0] <= ... <= a[a.length - 1] <= x
            // x - a[a.length - 1] <= x - a[j] for all j: 0 ... a.length - 1 #
        }
        // P1 := (a[0] < x < a[a.length - 1])
        // l' := l, r' := r
        int m = (l + r) / 2;
        // m = (l + r) / 2
        int ans;
        // m' = (r' + l') / 2
        // 2m' = r' + l'
        // 2l' <= 2m' <= 2r'
        // l' <= m' <= r'  =>  0 <= m' <= a.length - 1, m := m'
        // P && P1 && 0 <= m <= a.length - 1
        if (a[m] >= x && (m > 0 && a[m - 1] <= x) || l == r) {
            // P && P1 && 0 <= m <= a.length - 1 && a[m] >= x && m > 0 && a[m - 1] <= x
            // P && P1 && a[m - 1] <= x <= a[m] && 0 < m <= a.length - 1
                ans = m;
            // P && P1 && a[ans - 1] <= x <= a[ans] && 0 < ans <= a.length - 1 && ans' = m
        } else if (a[m] < x) {
            // P && P1 && a[m] < x && (a[m] < x || m <= 0 || a[m - 1] > x)
            // P && P1 && a[m] < x
            // P && a[m] < x < a[a.length - 1]
            // P && a[m + 1] <= x < a[a.length - 1] && m + 1 < a.length - 1
            ans = recursiveBinSearch(x, a, m + 1, r);
            // P && a[m + 1] <= x < a[a.length - 1] && m + 1 < a.length - 1 &&
            // abs(a[ans] - x) < abs(a[j] - x) for all j: 0 ... a.length - 1
        } else {
            // P && P1 && a[m] >= x && (a[m] < x || m <= 0 || a[m - 1] > x)
            // P && P1 && a[m] >= x && x < a[m - 1]
            // P && a[0] < x < a[m - 1] <= a[m] < a[a.length - 1]
            // P && a[0] < x < a[m - 1] < a[a.length - 1] && 0 < m - 1
            ans = recursiveBinSearch(x, a, l, m);
            // P && a[0] < x <= a[m - 1] < a[a.length - 1] && 0 < m - 1 &&
            // abs(a[ans] - x) < abs(a[j] - x) for all j: 0 ... a.length - 1
        }
        // P && P1 && (abs(a[ans] - x) < abs(a[j] - x) for all j: 0 ... a.length - 1 || a[ans - 1] <= x <= a[ans])
        if (ans == 0 || a[ans] == x || Math.abs(a[ans] - x) < Math.abs(a[ans - 1] - x)) {
            // P && P1 && (abs(a[ans] - x) < abs(a[j] - x) for all j: 0 ... a.length - 1 || a[ans - 1] <= x <= a[ans]) &&
            // (ans == 0 || a[ans] == x || abs(a[ans] - x) < abs(a[ans - 1] - x))
            return ans;
            // 1. P && P1 && ans = 0 => a[ans] < x < a[ans + 1] => abs(a[ans] - x) < abs(a[j] - x) for all j: 0 ... a.length - 1
            // 2. a[ans] = x => abs(a[ans] - x) < abs(a[j] - x) for all j: 0 ... a.length - 1
            // 3. P && a[ans - 1] < x < a[ans] && abs(a[ans] - x) < abs(a[ans - 1] - x) =>
            // abs(a[ans] - x) < abs(a[j] - x) for all j: 0 ... a.length - 1
        } else {
            // P && P1 && (abs(a[ans] - x) < abs(a[j] - x) for all j: 0 ... a.length - 1 || a[ans - 1] <= x <= a[ans]) &&
            // ans > 0 && a[ans] != x && abs(a[ans] - x) >= abs(a[ans - 1] - x)
            return ans - 1;
            // a[ans - 1] <= x <= a[ans] && abs(a[ans] - x) >= abs(a[ans - 1] - x)) =>
            // abs(a[ans - 1] - x) < abs(a[j] - x) for all j: 0 ... a.length - 1
        }
    }
}
