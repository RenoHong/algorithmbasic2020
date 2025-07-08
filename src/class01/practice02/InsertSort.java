package class01.practice02;

import java.util.Arrays;

public class InsertSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            int key = arr[i];
            while (j >= 0 && key < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }

    }

    private static void printArray(int[] arr) {
        if (arr == null || arr.length == 0)
            return;

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]) ;
            if (i == arr.length - 1) {
                sb.append("]");
            } else {
                sb.append(", ");
            }
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 6, 2, 6, 89, 23, 1, 0};
        sort(arr);
        printArray(arr);
    }

}
