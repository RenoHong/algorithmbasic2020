package class06.practice02;

import java.util.Arrays;
import java.util.PriorityQueue;

public class SortArrayDistanceLessK {

    public static void sort(int[] arr, int k) {
        if (arr == null || arr.length < 2)
            return;
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        int index = 0;
        for (; index <= Math.min(arr.length, k - 1); index++) {
            heap.add(arr[index]);
        }
        int i = 0;
        for (; index < arr.length; i++, index++) {
            heap.add(index);
            arr[i] = heap.poll();
        }
        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }

    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 1, 2, 0, 4, 5, 79};
        sort(arr, 5);
        System.out.println(Arrays.toString(arr));
    }

}
