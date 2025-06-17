package class01.practice02;

public class BSExist {

    public static int search(int[] arr, int val) {
        if (arr == null || arr.length < 1) return -1;
        int l = 0;
        int r = arr.length - 1;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (arr[m] == val)
                return m;
            else if (arr[m] > val) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return arr[l] == val ? l : -1;
    }


    public static void main(String[] args) {
        int[] arr = {0, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(search(arr, 6));
    }

}
