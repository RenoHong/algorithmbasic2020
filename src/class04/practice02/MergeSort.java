package class04.practice02;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {

    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length <= 1)
            return;

        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int m = l + ((r - l) >> 1);
        process(arr, l, m);
        process(arr, m + 1, r);
        merge(arr, l, m, r);
    }

    private static void merge(int[] arr, int l, int m, int r) {
        int[] helper = new int[r - l + 1];
        int p1 = l;
        int p2 = m + 1;
        int i = 0;
        while (p1 <= m && p2 <= r) {
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
    }


    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2)
            return;
        int mergeSize = 1;
        int n = arr.length;

        while (mergeSize < n) {
            int l = 0;
            while (l < n) {
                if (mergeSize >= n - l) {
                    break;
                }
                int m = l + mergeSize - 1;
                int r = m + Math.min(mergeSize, n - m - 1);
                merge(arr, l, m, r);
                l = r + 1;
            }
            if (mergeSize > (n >> 1)) {
                break;
            }
            mergeSize <<= 1;
        }

    }


    public static int[] randomArray(int maxLen, int maxValue) {
        Random rand = new Random();
        int size = rand.nextInt(maxLen) + 1;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = rand.nextInt(maxValue + 1) - rand.nextInt(maxValue);
        }
        return res;
    }

    public static boolean isEquals(int[] arr1, int[] arr2) {
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1 == null) {
            return false;
        }
        if (arr2 == null) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] copy(int[] arr) {
        if (arr == null) return null;
        int[] res = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int testTimes = 1000;
        int maxLen = 100;
        int maxValue = 10000;

        System.out.println(">>>");
        for (int i = 0; i < testTimes; i++) {

            int[] arr = randomArray(maxLen, maxValue);
            int[] arr2 = copy(arr);
            mergeSort1(arr);
            mergeSort2(arr2);

            if (!isEquals(arr, arr2)) {
                System.out.println("Opps!");
                System.out.println(Arrays.toString(arr));
                System.out.println(Arrays.toString(arr2));
                break;
            }
        }
        System.out.println("<<<");
    }

}
