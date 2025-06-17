package class03;

/**
 * This class provides a method to find the maximum value in an array using a recursive divide-and-conquer approach.
 */
public class Code08_GetMax {

    /**
     * Finds the maximum value in the given array.
     *
     * @param arr the input array
     * @return the maximum value in the array
     */
    public static int getMax(int[] arr) {
        // Call the recursive process method with the full range of the array
        return process(arr, 0, arr.length - 1);
    }

    /**
     * Recursively finds the maximum value in arr between indices L and R (inclusive).
     *
     * @param arr the input array
     * @param L   the left index of the range
     * @param R   the right index of the range
     * @return the maximum value in arr[L..R]
     */
    public static int process(int[] arr, int L, int R) {
        // If the range contains only one element, return it (base case)
        if (L == R) {
            return arr[L];
        }
        // Calculate the middle index of the current range
        int mid = L + ((R - L) >> 1); // Find the midpoint to avoid overflow
        // Recursively find the maximum in the left half
        int leftMax = process(arr, L, mid);
        // Recursively find the maximum in the right half
        int rightMax = process(arr, mid + 1, R);
        // Return the larger of the two maximums
        return Math.max(leftMax, rightMax);
    }

}
