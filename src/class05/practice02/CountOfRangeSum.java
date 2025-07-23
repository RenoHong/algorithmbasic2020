package class05.practice02;

public class CountOfRangeSum {

    public static int CountOfRangeSum(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        long[] sum = new long[arr.length];
        sum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    public static int process(long[] sum, int l, int r, int lower, int upper) {
        if (l == r) {
            return sum[l] >= lower && sum[l] <= upper ? 1 : 0;
        }

        int m = l + ((r - l) >> 1);
        return process(sum, l, m, lower, upper) +
                process(sum, m + 1, r, lower, upper) +
                merge(sum, l, m, r, lower, upper);

    }

    private static int merge(long[] sum, int l, int m, int r, int lower, int upper) {
        int ans = 0;
        int windowL = l;
        int windowR = l;

        for (int i = m + 1; i <= r; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;

            while (windowR <= m && sum[windowR] <= max) {
                windowR++;
            }
            while (windowL <= m && sum[windowL] >= min) {
                windowL++;
            }
            ans += windowR - windowL;

        }

        long[] helper = new long[r - l + 1];
        int p1 = l;
        int p2 = m + 1;
        int i = 0;
        while (p1 <= m && p2 <= r) {
            helper[i++] = sum[p1] > sum[p2] ? sum[p2++] : sum[p1++];
        }
        while (p1 <= m)
            helper[i++] = sum[p1++];
        while (p2 <= r)
            helper[i++] = sum[p2++];

        for (int j = 0; j < helper.length; j++) {
            sum[l + j] = helper[j];
        }
        return ans;
    }


}
