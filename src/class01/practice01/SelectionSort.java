package class01.practice01;

import java.util.Arrays;

public class SelectionSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            //Warning: j must smaller than arr.length, not arr.length-1.
            //Because this is smaller than, equals not included.
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static boolean isArrayEquals(int[] ori, int[] dest) {
        if (ori == null && dest != null)
            return false;
        if (ori != null && dest == null)
            return false;
        if (ori == null && dest == null)
            return true;
        if (ori.length != dest.length)
            return false;
        for (int i = 0; i < ori.length; i++) {
            if (ori[i] != dest[i])
                return false;
        }
        return true;
    }

    public static void main(String[] args) {

        int testTimes = 1000000;
        int maxLen = 50;
        int maxValue = 100;

        for (int i = 0; i < testTimes; i++) {
            int[] test1 = generateRandomArray(maxLen, maxValue);
            int[] test2 = Arrays.copyOf(test1, test1.length);
            int[] ori = Arrays.copyOf(test1, test1.length);

            if (!isArrayEquals(test1, test2)) {
                System.out.println("Oops!");
                System.out.println("Original array:" + ori);
                System.out.println("test1:" + test1);
                System.out.println("test2:" + test2);
            }

        }

        System.out.println("Test completed.");

    }
}
