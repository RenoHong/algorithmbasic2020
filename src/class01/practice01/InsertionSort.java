package class01.practice01;

import java.util.Arrays;

public class InsertionSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            //一旦j位置的值比 key 大，那就要把左边腾出来每次往左移。覆盖了j+1的位置。j+1 是i的位子也就是 key的位置。所以不怕值丢失。
            // j一定要先判断是否 >= 0 否者 arr[j]会越界。

            //Let me draw a diagram to help you understand how the insertion sort works in this code section:
            //
            //Suppose we have the array: [3, 5, 2, 1, 4]
            //
            //For i=1 (value=5):
            //```
            //[3, 5, 2, 1, 4]
            //    ^ key=5
            //   j=0
            //```
            //Since arr[j]=3 < key=5, no movement needed.
            //
            //For i=2 (value=2):
            //```
            //[3, 5, 2, 1, 4]
            //       ^ key=2
            //      j=1
            //```
            //1. arr[j]=5 > key=2, so arr[j+1]=arr[j], making array [3, 5, 5, 1, 4]
            //2. j-- (j=0)
            //3. arr[j]=3 > key=2, so arr[j+1]=arr[j], making array [3, 3, 5, 1, 4]
            //4. j-- (j=-1)
            //5. Exit loop, place key at j+1 (position 0): [2, 3, 5, 1, 4]
            //
            //This part of the code checks each element to the left of the current position.
            // When it finds elements larger than the current key value,
            // it shifts them right to make space for inserting the key value in its correct sorted position.
            while (j >= 0 && key < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }

    }

    public static void main(String[] args) {
        int[] arr = {9, 3, 4, 2, 1, 7, 6, 8};
        sort(arr);
        Arrays.stream(arr).forEach(System.out::print);
    }
}
