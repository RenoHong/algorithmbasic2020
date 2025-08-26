package class14;

import java.util.PriorityQueue;

// Problem Definition:
// Given an array representing the weights of gold bars, you want to split all bars into one bar.
// Each time you merge two bars, the cost is the sum of their weights.
// The goal is to find the minimum total cost to merge all bars into one.
// This is a classic greedy problem, similar to Huffman coding.

// Main class for solving the Less Money Split Gold problem
public class Code02_LessMoneySplitGold {

    /**
     * What: Baseline brute-force to compute the minimum total merge cost.
     * Why needed: Serves as a correctness oracle for the greedy solution (lessMoney2) in randomized tests.
     * How implemented: Tries every possible merge order via recursion. The parameter 'pre' carries the
     *                  accumulated cost along the recursion path. When one bar remains, return 'pre'.
     * Design choice: Keep state immutable by constructing a new array for each merge (simple and safe).
     *                This keeps reasoning straightforward at the cost of higher time/space complexity.
     * Complexity: Exponential time; the number of merge orders is super-exponential (roughly Catalan-like growth).
     *             Space is O(n) for recursion depth + O(n) per state copy.
     * Example: For [2,3,4], it explores (2,3)->5 then (5,4)->9, etc., and returns min(14,15,16)=14.
     */
    public static int lessMoney1(int[] arr) {
        // If the input array is null or empty, no cost is needed
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // Start recursive process with initial cost 0
        return process(arr, 0);
    }

    /**
     * What: Core exhaustive search that explores all pairwise merges and returns the minimum total cost.
     * Why: To ensure we consider every possible merge sequence, so the result is guaranteed optimal.
     * How:
     *   - Base case: when arr.length == 1, the cost 'pre' is final and returned.
     *   - Recursive step: for every i<j, merge arr[i] and arr[j], pay (arr[i]+arr[j]),
     *                     build the next array (length-1), and recurse. Take the minimum over all choices.
     * Design choices:
     *   - Use 'pre' to accumulate cost instead of recomputing sums on the way back.
     *   - Use a helper (copyAndMergeTwo) to keep this method focused on the recursion logic.
     * Correctness intuition: Any full merge plan corresponds to a full binary tree with leaves as original bars.
     *                       Enumerating all pairs step-by-step enumerates all such trees and all leaf orderings.
     * Complexity: Exponential branching on pairs; space O(n) recursion depth plus array copies.
     * Visual:
     *   For [2,3,4], pre=0
     *        [2,3,4]
     *        ├─ (2,3)->5, pre=5  → [5,4] → (5,4)->9, pre=14 → [9] return 14
     *        ├─ (2,4)->6, pre=6  → [6,3] → (6,3)->9, pre=15 → [9] return 15
     *        └─ (3,4)->7, pre=7  → [7,2] → (7,2)->9, pre=16 → [9] return 16
     */
    public static int process(int[] arr, int pre) {
        // Base case: only one bar left, return accumulated cost
        if (arr.length == 1) {
            return pre;
        }
        int ans = Integer.MAX_VALUE; // Initialize answer to max value
        // Try all pairs of bars to merge
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                // Merge bars at i and j, add their cost, and recurse
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans; // Return minimum cost found
    }

    /**
     * What: Build the next problem state by merging two positions i and j into a single bar.
     * Why: The exhaustive search needs to move from size n to n-1 while preserving other elements.
     * How: Copy all elements except i and j into a new array, then append arr[i]+arr[j] as the merged bar.
     * Design choices:
     *   - Append the merged sum at the end. The relative position is irrelevant to the total cost because
     *     the algorithm explores all future pairings anyway.
     *   - Simplicity over micro-optimizations (O(n) copy per transition is fine for brute-force).
     * Complexity: O(n) time to construct the next array; O(n) space for the new array.
     */
    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1]; // New array with one less element
        int ansi = 0; // Index for new array
        // Copy all bars except i and j
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        // Add merged bar at the end
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

//
//    The method on line 61 (`lessMoney2`) uses a greedy algorithm with a min-heap (priority queue) to solve the "Less Money Split Gold" problem efficiently.
//
//            **Explanation:**
//
//            - The goal is to merge all gold bars into one, minimizing the total cost. Each merge costs the sum of the two bars' weights.
//            - The greedy strategy is to always merge the two smallest bars first. This is optimal because merging larger bars earlier would increase the cost in subsequent merges.
//- The method works as follows:
//            1. All bar weights are added to a min-heap, which always gives access to the smallest elements.
//            2. While more than one bar remains, the two smallest bars are removed, merged, and their sum is added to the total cost.
//  3. The merged bar is put back into the heap, and the process repeats.
//            4. When only one bar remains, the total cost is returned.
//
//    This approach is similar to building a Huffman tree, where combining the smallest weights first leads to the minimum total cost. The priority queue ensures efficient selection of the smallest bars at each step.
    // Greedy solution using a min-heap (priority queue)
    // Always merge the two smallest bars for minimum cost
    /**
     * What: Optimal greedy solution using a min-heap (Huffman-like merging).
     * Why: Minimizing the weighted sum of merges is exactly the Huffman merging problem,
     *      where repeatedly combining the two smallest items yields a global optimum.
     * How:
     *   - Push all weights into a min-heap.
     *   - Repeatedly pop two smallest (a, b), pay cost (a+b), push (a+b) back.
     *   - When one item remains, accumulated sum is the minimum total cost.
     * Design choices:
     *   - PriorityQueue ensures O(log n) access to the current two smallest values.
     *   - Maintain 'sum' for the total cost and 'cur' for the current merged value to show intent clearly.
     * Correctness sketch (exchange argument):
     *   - Suppose an optimal plan merges some not-smallest pair first.
     *   - Swapping that merge to use the two smallest first never increases cost and can only help,
     *     leading to an optimal plan that always merges two smallest at each step.
     * Complexity: Time O(n log n) (n inserts + (n-1) merges, each heap op O(log n)); Space O(n) for the heap.
     * Diagram (example: [2, 3, 4, 6]):
     *   Heap: [2,3,4,6]
     *   1) pop 2,3 → cur=5, sum=5, push 5 → heap [4,5,6]
     *   2) pop 4,5 → cur=9, sum=14, push 9 → heap [6,9]
     *   3) pop 6,9 → cur=15, sum=29 → done. Answer = 29.
     */
    public static int lessMoney2(int[] arr) {
        PriorityQueue<Integer> pQ = new PriorityQueue<>(); // Min-heap for bar weights
        // Add all bar weights to the heap
        for (int i = 0; i < arr.length; i++) {
            pQ.add(arr[i]);
        }
        int sum = 0; // Total cost
        int cur = 0; // Current merge cost
        // While more than one bar remains
        while (pQ.size() > 1) {
            cur = pQ.poll() + pQ.poll(); // Merge two smallest bars
            sum += cur; // Add merge cost to total
            pQ.add(cur); // Add merged bar back to heap
        }
        return sum; // Return total minimum cost
    }

    /**
     * What: Utility to generate random test arrays.
     * Why: Used by the main method to perform randomized testing that compares brute-force vs greedy.
     * How: Random length in [0, maxSize], each element uniformly random in [0, maxValue].
     * Design choices:
     *   - Includes length 0 to test edge cases.
     *   - Non-negative weights (consistent with “gold bar weights” domain).
     * Complexity: O(n) time and space where n is the generated length.
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())]; // Random length up to maxSize
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)); // Random value up to maxValue
        }
        return arr;
    }

    /**
     * What: Randomized tester that validates the greedy solution (lessMoney2) against the brute-force oracle (lessMoney1).
     * Why: Powerful way to catch subtle bugs by exploring many random scenarios, including corner cases.
     * How:
     *   - For testTime iterations, generate a random array and compare results.
     *   - If any mismatch occurs, print "Oops!" (signals a bug).
     *   - If loop finishes with no mismatches, print "finish!".
     * Design choices:
     *   - High iteration count (100,000) increases confidence; adjust down if runtime is a concern.
     *   - Small maxSize keeps brute-force feasible; cost grows exponentially with size.
     * Complexity: Overall dominated by brute-force runs; average runtime depends on generated sizes.
     */
    public static void main(String[] args) {
        int testTime = 100000; // Number of test cases
        int maxSize = 6; // Maximum array size
        int maxValue = 1000; // Maximum bar weight
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue); // Generate random test case
            // Compare brute-force and greedy solutions
            if (lessMoney1(arr) != lessMoney2(arr)) {
                System.out.println("Oops!"); // Print if results differ
            }
        }
        System.out.println("finish!"); // All tests passed
    }

}
