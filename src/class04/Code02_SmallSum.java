package class04;

/**
 * This class provides a method to calculate the "small sum" of an array using a modified merge sort.
 */
public class Code02_SmallSum {

    /**
     * Calculates the small sum of the given array.
     *
     * @param arr the input array
     * @return the small sum
     */
    public static int smallSum(int[] arr) {
        // If the array is null or has less than 2 elements, small sum is 0
        if (arr == null || arr.length < 2) {
            return 0;
        }
        // Start the recursive process
        return process(arr, 0, arr.length - 1);
    }

    /**
     * Recursively calculates the small sum for arr[l..r] and sorts the subarray.
     *
     * @param arr the input array
     * @param l   left index
     * @param r   right index
     * @return the small sum for arr[l..r]
     */
    public static int process(int[] arr, int l, int r) {
        // Base case: single element has no small sum
        if (l == r) {
            return 0;
        }
        // Calculate the middle index
        int mid = l + ((r - l) >> 1);
        // Recursively calculate small sum for left and right halves and merge
        return
                process(arr, l, mid)
                        +
                        process(arr, mid + 1, r)
                        +
                        merge(arr, l, mid, r);
    }

    /**
     * Merges two sorted subarrays and calculates the small sum during the merge.
     *
     * @param arr the input array
     * @param L   left index of the first subarray
     * @param m   right index of the first subarray
     * @param r   right index of the second subarray
     * @return the small sum generated during this merge
     */
    public static int merge(int[] arr, int L, int m, int r) {
        int[] help = new int[r - L + 1]; // Temporary array for merging
        int i = 0; // Index for help array
        int p1 = L; // Pointer for left subarray
        int p2 = m + 1; // Pointer for right subarray
        int res = 0; // Accumulates the small sum
        // Merge elements and calculate small sum
        while (p1 <= m && p2 <= r) {
            //This line calculates the "small sum" during the merge step of merge sort. When `arr[p1] < arr[p2]`,
            // it means the current element in the left subarray (`arr[p1]`) is less than the current element in the right subarray (`arr[p2]`).
            // Because both subarrays are sorted, all remaining elements from `p2` to `r` in the right subarray are greater than `arr[p1]`.
            // So, for each of these elements, `arr[p1]` contributes to the small sum. The number of such elements is `(r - p2 + 1)`,
            // so the total contribution is `(r - p2 + 1) * arr[p1]`. If `arr[p1] >= arr[p2]`, nothing is added.
            res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
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
        return res;
    }

    /**
     * Brute-force comparator for small sum, used for testing.
     *
     * @param arr the input array
     * @return the small sum
     */
    public static int comparator(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                res += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return res;
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
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
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
     * Main method to test the small sum implementation.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int testTime = 500000; // Number of tests
        int maxSize = 100; // Maximum array size
        int maxValue = 100; // Maximum element value
        boolean succeed = true; // Flag to track test success
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue); // Generate random array
            int[] arr2 = copyArray(arr1); // Copy the array
            if (smallSum(arr1) != comparator(arr2)) { // Compare results
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}
