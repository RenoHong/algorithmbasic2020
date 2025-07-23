package class04;

/**
 * This class provides a method to count reverse pairs in an array using a modified merge sort.
 * A reverse pair in an array is a pair of indices ((i, j)) such that (i < j) and (arr[i] > arr[j]).
 * It counts how many times a larger number appears before a smaller number in the array.
 * This is often used to measure how unsorted an array is.
 **/

public class Code03_ReversePair {

    /**
     * Counts the number of reverse pairs in the given array.
     *
     * @param arr the input array
     * @return the number of reverse pairs
     */
    public static int reverPairNumber(int[] arr) {
        // If the array is null or has less than 2 elements, there are no reverse pairs
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
     * Merges two sorted subarrays and counts reverse pairs during the merge.
     *
     * @param arr the input array
     * @param L   left index of the first subarray
     * @param m   right index of the first subarray
     * @param r   right index of the second subarray
     * @return the number of reverse pairs generated during this merge
     */
    public static int merge(int[] arr, int L, int m, int r) {
        int[] help = new int[r - L + 1]; // Temporary array for merging
        int i = help.length - 1; // Index for help array (from end)
        int p1 = m; // Pointer for left subarray (from end)
        int p2 = r; // Pointer for right subarray (from end)
        int res = 0; // Accumulates the number of reverse pairs
        // Merge elements and count reverse pairs
        while (p1 >= L && p2 > m) {
            //In the merge step of counting reverse pairs, arr[p1] > arr[p2] means that
            // all elements from arr[m+1] to arr[p2] (right subarray) are less than arr[p1],
            // because both subarrays are sorted. The number of such elements is p2 - m.
            // So, every time this condition is true, you add p2 - m to the result,
            // representing all reverse pairs formed by arr[p1] and the right subarray elements from m+1 to p2
            res += arr[p1] > arr[p2] ? (p2 - m) : 0;
            help[i--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
        }
        // Copy remaining elements from left subarray
        while (p1 >= L) {
            help[i--] = arr[p1--];
        }
        // Copy remaining elements from right subarray
        while (p2 > m) {
            help[i--] = arr[p2--];
        }
        // Copy merged elements back to original array
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return res;
    }

    /**
     * Brute-force comparator for reverse pairs, used for testing.
     *
     * @param arr the input array
     * @return the number of reverse pairs
     */
    public static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
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
     * Main method to test the reverse pair implementation.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int testTime = 500000; // Number of tests
        int maxSize = 100; // Maximum array size
        int maxValue = 100; // Maximum element value
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue); // Generate random array
            int[] arr2 = copyArray(arr1); // Copy the array
            if (reverPairNumber(arr1) != comparator(arr2)) { // Compare results
                System.out.println("Oops!");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
