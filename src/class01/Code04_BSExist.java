package class01;

import java.util.Arrays;

/**
 * Implements binary search to check if a number exists in a sorted array.
 */
public class Code04_BSExist {

    /**
     * Determines if a number exists in a sorted array using binary search.
     *
     * @param sortedArr The sorted array to search.
     * @param num       The number to find.
     * @return True if num exists in sortedArr, false otherwise.
     */
    public static boolean exist(int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0) {
            return false;
        }
        int L = 0;
        int R = sortedArr.length - 1;
        int mid = 0;
        // L..R
        while (L < R) { // L..R 至少两个数的时候
            mid = L + ((R - L) >> 1);
            if (sortedArr[mid] == num) {
                return true;
            } else if (sortedArr[mid] > num) {
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return sortedArr[L] == num;
    }

    /**
     * Brute-force method to check if a number exists in an array.
     *
     * @param sortedArr The array to search.
     * @param num       The number to find.
     * @return True if num exists in sortedArr, false otherwise.
     */
    public static boolean test(int[] sortedArr, int num) {
        for (int cur : sortedArr) {
            if (cur == num) {
                return true;
            }
        }
        return false;
    }


    /**
     * Generates a random array with values in the range [-maxValue, maxValue].
     *
     * @param maxSize  Maximum possible size of the array.
     * @param maxValue Maximum absolute value for elements.
     * @return The generated random array.
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    /**
     * Main method to test the binary search implementation.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != exist(arr, value)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}