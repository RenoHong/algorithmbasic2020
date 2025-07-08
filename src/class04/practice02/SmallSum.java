package class04.practice02;

public class SmallSum {


    public static int smallSum(int[] arr) {
        if (arr == null || arr.length == 0)
            return 0;

        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int m = l + ((r - l) >> 1);
        return process(arr, l, m)
                + process(arr, m + 1, r)
                + merge(arr, l, m, r);
    }

    private static int merge(int[] arr, int l, int m, int r) {
        int[] helper = new int[r - l + 1];
        int res = 0;
        int p1 = l;
        int p2 = m + 1;
        int i = 0;
        while (p1 <= m && p2 <= r) {
            res += arr[p1] < arr[p2] ? arr[p1] * (r - p2 + 1) : 0;
            helper[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= m) {
            helper[i++] = arr[p1++];
        }
        while (p2 <= r) {
            helper[i++] = arr[p2++];
        }

        for (int j = 0; j < helper.length; j++) {
            arr[l + j] = helper[j];
        }
        return res;
    }

}
