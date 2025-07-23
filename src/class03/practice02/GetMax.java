package class03.practice02;

public class GetMax {

    public static int getMax(int[] arr) {
        if (arr == null || arr.length == 0)
            return 0;
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (r == l) {
            return arr[r];
        }
        int m = l + ((r - l) >> 2);
        return Math.max(process(arr, l, m), process(arr, m + 1, r));
    }


    public static void main(String[] args) {
        int[] arr = new int[]{3, 5, 7, 8, 2, 3, 0, 9, 11, 23};
        int max = getMax(arr);
        System.out.println(max);
    }
}
