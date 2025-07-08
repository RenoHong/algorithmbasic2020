package class05.practice02;

import java.util.Random;

public class PartitionAndQuickSort {

    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2)
            return;
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l >= r)
            return;

        swap(arr, l + new Random().nextInt(r-1+1), r);
        int[] m = netherlandsFlag(arr, l, r);
        process(arr, l, m[0] -1);
        process(arr, m[1]+1, r);
    }

    private static int partition(int[] arr, int l, int r) {
        if (l == r)
            return l;
        int less = l - 1;
        int more = r;
        int i = l;
        while (i < more) {
            if (arr[i] < arr[r]) {
                swap(arr, i++, ++less);
            } else if (arr[i] > arr[r]) {
                swap(arr, i, --more);
            } else {
                i++;
            }
        }
        swap(arr, ++less, r);
        return less ;
    }


    public static int[] netherlandsFlag(int[] arr, int l, int r) {
        if (arr == null || arr.length == 0 || l > r) {
            return new int[]{-1, -1};
        }
        if (l == r) {
            return new int[]{l, l};
        }
        int lessThan = l - 1;
        int largeThan = r;
        int index = l;
        while (index < largeThan) {
            if (arr[index] < arr[r]) {
                swap(arr, index++, ++lessThan);
            } else if (arr[index] > arr[r]) {
                swap(arr, index, --largeThan);
            } else {
                index++;
            }
        }
        swap(arr, r, largeThan);
        return new int[]{lessThan + 1, largeThan + 1};
    }

    private static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    private static void print(int[] arr) {
        if (arr == null)
            return;
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1)
                sb.append(arr[i]);
            else
                sb.append(arr[i]).append(", ");
        }
        sb.append("}");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        int[] array = new int[]{3, 7, 9, 4, 5, 6, 8, 4};
        quickSort1(array);
        print(array);
    }
}
