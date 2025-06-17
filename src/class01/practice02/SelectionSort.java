package class01.practice02;

public class SelectionSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return; // No need to sort if the array is null or has less than 2 elements 
        }

        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j; // Update minIndex if a smaller element is found
                }
            }
            int t = arr[i];
            arr[i] = arr[minIndex]; // Swap the found minimum element with the first element
            arr[minIndex] = t; // Place the minimum element at the current position
        }

    }

    public static void main(String[] args) {
        int[] arr = {64, 25, 12, 22, 11};
        sort(arr);
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

}
