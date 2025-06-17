package class01.practice02;

public class BubbleSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] < arr[i - 1]) {
                int t = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = t;
            }
        }
    }

    public static void main(String[] args) {
        int[] test = {11, 2, 3, 67, 9, 83};
        sort(test);
        for (int i : test) {
            System.out.print(i + " ");
        }
    }
}
