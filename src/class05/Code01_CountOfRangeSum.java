package class05;

// This problem can be directly tested on LeetCode:
// https://leetcode.cn/problems/count-of-range-sum/

/**
 * This class solves the "Count of Range Sum" problem.
 * Given an integer array nums, count the number of range sums that lie in [lower, upper].
 * The algorithm uses a merge sort approach with O(NlogN) time complexity.
 * <br /><br />
 *
 * Step-by-step explanation and idea:
 * <br /><br />
 * 1. **Brute-force approach**:
 *    - For each subarray (i, j), calculate the sum and check if it is in [lower, upper].
 *    - This is O(N^2) and too slow for large arrays.
 * <br /><br />
 * 2. **Prefix sum transformation**:
 *    - Let prefixSum[i] = sum of nums[0..i].
 *    - The sum of subarray nums[i..j] = prefixSum[j] - prefixSum[i-1] (or prefixSum[j] if i==0).
 *    - So, for each j, we want to count how many i <= j such that:
 *      lower <= prefixSum[j] - prefixSum[i-1] <= upper
 *      => prefixSum[j] - upper <= prefixSum[i-1] <= prefixSum[j] - lower
 *    - This reduces the problem to, for each prefixSum[j], counting how many previous prefix sums fall into a certain range.
 * <br /><br />
 * 3. **Efficient counting using merge sort**:
 *    - We use a modified merge sort on the prefix sum array.
 *    - For each merge step, for each right part element, we use two pointers to find the range in the left part that fits the condition.
 *    - This is possible because merge sort keeps the left and right parts sorted.
 *    - The counting is done during the merge step, and the array is sorted as usual.
 *    - This reduces the time complexity to O(NlogN).
 * <br /><br />
 * 4. **Implementation details**:
 *    - We build the prefix sum array.
 *    - We recursively process the prefix sum array using the process() and merge() methods.
 *    - The merge() method counts valid pairs and merges the sorted arrays.
 * <br /><br />
 * This approach is inspired by the classic "reverse pairs" or "small sum" problems, where merge sort is used to count pairs efficiently.
 */
public class Code01_CountOfRangeSum {

    /**
     * Main method to count the number of range sums in [lower, upper].
     * @param nums Input integer array
     * @param lower Lower bound of the range
     * @param upper Upper bound of the range
     * @return Number of valid range sums
     */
    public static int countRangeSum(int[] nums, int lower, int upper) {
        // If the array is empty, return 0
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // Create prefix sum array, sum[i] is the sum of nums[0..i]
        long[] sum = new long[nums.length];
        sum[0] = nums[0]; // Initialize the first prefix sum
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i]; // Calculate prefix sums
        }
        // Recursively process the entire prefix sum array
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    /**
     * Recursively process sum[L..R], return the count of range sums in [lower, upper].
     * @param sum Prefix sum array
     * @param L Left boundary
     * @param R Right boundary
     * @param lower Lower bound of the range
     * @param upper Upper bound of the range
     * @return Number of valid range sums in this segment
     */
    public static int process(long[] sum, int L, int R, int lower, int upper) {
        // Base case: only one element
        if (L == R) {
            // Check if the single prefix sum is within the range
            return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
        }
        // Find the middle index
        int M = L + ((R - L) >> 1);
        // Recursively process left, right, and cross-mid segments
        return process(sum, L, M, lower, upper) + process(sum, M + 1, R, lower, upper)
                + merge(sum, L, M, R, lower, upper);
    }

    /**
     * Merge step of merge sort, also counts the number of valid cross-segment range sums.
     * @param arr Prefix sum array
     * @param L Left boundary
     * @param M Middle index
     * @param R Right boundary
     * @param lower Lower bound of the range
     * @param upper Upper bound of the range
     * @return Number of valid cross-segment range sums
     */
    public static int merge(long[] arr, int L, int M, int R, int lower, int upper) {
        int ans = 0; // Counter for valid range sums
        int windowL = L; // Left pointer for sliding window
        int windowR = L; // Right pointer for sliding window
        // For each prefix sum in the right half
        for (int i = M + 1; i <= R; i++) {
            long min = arr[i] - upper; // Lower bound for left prefix sum
            long max = arr[i] - lower; // Upper bound for left prefix sum
            // Move windowR so that arr[windowR] <= max
            while (windowR <= M && arr[windowR] <= max) {
                windowR++;
            }
            // Move windowL so that arr[windowL] >= min
            while (windowL <= M && arr[windowL] < min) {
                windowL++;
            }
            // All arr[windowL..windowR-1] are valid
            ans += windowR - windowL;
            // Visualization:
            // arr[windowL...windowR-1] all satisfy min <= arr[j] <= max
            // That is, arr[i] - arr[j] ∈ [lower, upper]
        }
        // Merge two sorted halves
        long[] help = new long[R - L + 1]; // Helper array for merge
        int i = 0; // Index for help array
        int p1 = L; // Pointer for left half
        int p2 = M + 1; // Pointer for right half
        // Merge the two sorted segments
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // Copy remaining elements from left half
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        // Copy remaining elements from right half
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        // Copy merged result back to original array
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return ans; // Return the count for this merge
    }

}