package class04.practice02;

public class ReversePair {

    public static int reversePair(int[] arr) {
        if (arr == null || arr.length < 2)
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
        int p1 = m;
        int p2 = r;
        int i = helper.length - 1;
        int res = 0;
        while (p1 >= l && p2 >= m + 1) {
            res += arr[p1] > arr[p2] ? p2 - m :0 ;
           helper[i--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
        }
        while(p1 >= l){
            helper[i--] = arr[p1--] ;
        }
        while(p2>= m+1){
            helper[i--] = arr[p2--];
        }
        return res ;
    }

}
