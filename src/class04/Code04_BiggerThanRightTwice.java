package class04;

/**
 * This class provides a method to count the number of reverse pairs where arr[i] > 2 * arr[j]
 * using a modified merge sort.
 * Leetcode link: https://leetcode.cn/problems/reverse-pairs/
 */
public class Code04_BiggerThanRightTwice {

    /**
     * Counts the number of reverse pairs where arr[i] > 2 * arr[j].
     *
     * @param arr the input array
     * @return the number of such reverse pairs
     */
    public static int reversePairs(int[] arr) {
        // If the array is null or has less than 2 elements, there are no such pairs
        if (arr == null || arr.length < 2) {
            return 0;
        }
        // Start the recursive process
        return process(arr, 0, arr.length - 1);
    }

    /**
     * Recursively counts reverse pairs for arr[l..r] and sorts the subarray.
     *
     * @param arr the input array
     * @param l   left index
     * @param r   right index
     * @return the number of reverse pairs in arr[l..r]
     */
    public static int process(int[] arr, int l, int r) {
        // Base case: single element has no reverse pairs
        if (l == r) {
            return 0;
        }
        // Calculate the middle index
        int mid = l + ((r - l) >> 1);
        // Recursively count reverse pairs in left and right halves and merge
        return process(arr, l, mid) + process(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    /**
     * Merges two sorted subarrays and counts reverse pairs where arr[i] > 2 * arr[j] during the merge.
     *
     * @param arr the input array
     * @param L   left index of the first subarray
     * @param m   right index of the first subarray
     * @param r   right index of the second subarray
     * @return the number of such reverse pairs generated during this merge
     */
    public static int merge(int[] arr, int L, int m, int r) {
        // [L....M] [M+1....R]
        int ans = 0; // Accumulates the number of reverse pairs
        int windowR = m + 1; // Right pointer for the window
        // For each element in the left subarray, count how many in the right satisfy arr[i] > 2 * arr[j]
        for (int i = L; i <= m; i++) {
            while (windowR <= r && (long) arr[i] > (long) arr[windowR] * 2) {
                windowR++;
            }
            ans += windowR - m - 1;
        }
        int[] help = new int[r - L + 1]; // Temporary array for merging
        int i = 0; // Index for help array
        int p1 = L; // Pointer for left subarray
        int p2 = m + 1; // Pointer for right subarray
        // Merge elements from both subarrays in sorted order
        while (p1 <= m && p2 <= r) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // Copy remaining elements from left subarray
        while (p1 <= m) {
            help[i++] = arr[p1++];
        }
        // Copy remaining elements from right subarray
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        // Copy merged elements back to original array
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return ans;
    }

    /**
     * Brute-force comparator for reverse pairs, used for testing.
     *
     * @param arr the input array
     * @return the number of reverse pairs where arr[i] > 2 * arr[j]
     */
    public static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if ((long) arr[i] > (((long) arr[j]) << 1)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * Generates a random array for testing.
     *
     * @param maxSize  maximum possible size of the array
     * @param maxValue maximum possible value of an element
     * @return the generated random array
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    /**
     * Copies the given array.
     *
     * @param arr the array to copy
     * @return a new array with the same elements as arr
     */
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    /**
     * Checks if two arrays are equal.
     *
     * @param arr1 first array
     * @param arr2 second array
     * @return true if arrays are equal, false otherwise
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the elements of the array.
     *
     * @param arr the array to print
     */
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    /**
     * Main method to test the reverse pair implementation.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int testTime = 500000; // Number of tests
        int maxSize = 100; // Maximum array size
        int maxValue = 100; // Maximum element value
        System.out.println("测试开始");
        //for (int i = 0; i < testTime; i++) {
        //int[] arr1 = generateRandomArray(maxSize, maxValue); // Generate random array
        int[] arr1 = new int[]{2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647};
        int[] arr2 = copyArray(arr1); // Copy the array
        if (reversePairs(arr1) != comparator(arr2)) { // Compare results
            System.out.println("Oops!");
            printArray(arr1);
            printArray(arr2);
            //break;
        }
        //}
        System.out.println("测试结束");
    }

}
