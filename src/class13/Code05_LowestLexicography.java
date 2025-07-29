package class13;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * This class provides two methods to find the lexicographically smallest string
 * by concatenating all strings in an array in some order.
 */
public class Code05_LowestLexicography {

    /**
     * Brute-force method: Generates all permutations and returns the smallest one.
     * @param strs Array of strings to concatenate.
     * @return Lexicographically smallest concatenation.
     */
    public static String lowestString1(String[] strs) {
        if (strs == null || strs.length == 0) { // Check for null or empty input
            return ""; // Return empty string if input is invalid
        }
        TreeSet<String> ans = process(strs); // Generate all possible concatenations
        return ans.size() == 0 ? "" : ans.first(); // Return the smallest one
    }

    /**
     * Recursively generates all permutations of the input strings and returns all possible concatenations.
     * @param strs Array of strings to permute.
     * @return TreeSet containing all possible concatenated results.
     * process(["a", "b", "c"])
     * ├─ 选"a"为首：first = "a", nexts = ["b", "c"]
     * │  └─ process(["b", "c"])
     * │     ├─ 选"b"为首：first = "b", nexts = ["c"]
     * │     │  └─ process(["c"]) → {"c"}
     * │     │     → "b" + "c" = "bc"
     * │     └─ 选"c"为首：first = "c", nexts = ["b"]
     * │           └─ process(["b"]) → {"b"}
     * │              → "c" + "b" = "cb"
     * │     → 返回 {"bc", "cb"}
     * │  → "a" + "bc" = "abc"
     * │    "a" + "cb" = "acb"
     * ├─ 选"b"为首：first = "b", nexts = ["a", "c"]
     * │  ...（同理递归）
     * └─ 选"c"为首：first = "c", nexts = ["a", "b"]
     *    ...（同理递归）
     *
     * 最终会得到所有排列拼接结果：abc, acb, bac, bca, cab, cba
     * TreeSet 自动排序，取最小的 "abc"。
     *
     * 每一层递归就是选一个字符串做前缀，递归处理剩下的字符串，直到最后一层只剩一个字符串，返回它本身。再一层层往上拼接组合。
     */
    public static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>(); // TreeSet to store results in sorted order
        if (strs.length == 0) { // Base case: no strings left
            ans.add(""); // Add empty string as the only result
            return ans;
        }
        // Try each string as the first in the permutation
        for (int i = 0; i < strs.length; i++) {
            String first = strs[i]; // Pick the i-th string as the first
            String[] nexts = removeIndexString(strs, i); // Remove the i-th string for the next recursion
            TreeSet<String> next = process(nexts); // Recursively get all permutations of the remaining strings
            for (String cur : next) { // For each result from recursion
                ans.add(first + cur); // Concatenate current string and add to results
            }
        }
        return ans; // Return all possible concatenations
    }

    /**
     * Removes the string at the specified index from the array and returns a new array.
     * @param arr Original array.
     * @param index Index to remove.
     * @return New array with the element at index removed.
     */
    public static String[] removeIndexString(String[] arr, int index) {
        int N = arr.length; // Length of the original array
        String[] ans = new String[N - 1]; // New array with one less element
        int ansIndex = 0; // Index for the new array
        for (int i = 0; i < N; i++) { // Iterate through original array
            if (i != index) { // Skip the element at the specified index
                ans[ansIndex++] = arr[i]; // Copy other elements
            }
        }
        return ans; // Return the new array
    }

    /**
     * Greedy method: Sorts strings using a custom comparator and concatenates them.
     * @param strs Array of strings to concatenate.
     * @return Lexicographically smallest concatenation.
     */
    public static String lowestString2(String[] strs) {
        if (strs == null || strs.length == 0) { // Check for null or empty input
            return ""; // Return empty string if input is invalid
        }
        Arrays.sort(strs, new MyComparator()); // Sort strings using custom comparator
        String res = ""; // Result string
        for (int i = 0; i < strs.length; i++) { // Concatenate all strings in order
            res += strs[i];
        }
        return res; // Return the concatenated result
    }

    // ------------------- Testing Utilities -------------------

    /**
     * Generates a random string of up to strLen characters.
     * @param strLen Maximum length of the string.
     * @return Randomly generated string.
     */
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1]; // Random length between 1 and strLen
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5); // Random value between 0 and 4
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value); // Random uppercase or lowercase letter
        }
        return String.valueOf(ans); // Convert char array to string
    }

    /**
     * Generates a random array of strings.
     * @param arrLen Maximum number of strings in the array.
     * @param strLen Maximum length of each string.
     * @return Randomly generated string array.
     */
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1]; // Random array length between 1 and arrLen
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen); // Fill with random strings
        }
        return ans;
    }

    /**
     * Copies a string array.
     * @param arr Array to copy.
     * @return New array with the same contents.
     */
    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length]; // New array of same length
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]); // Copy each string
        }
        return ans;
    }

    /**
     * Main method for testing both approaches.
     * Generates random test cases and compares results of both methods.
     */
    public static void main(String[] args) {
        int arrLen = 6; // Maximum array length
        int strLen = 5; // Maximum string length
        int testTimes = 10000; // Number of test cases
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen); // Generate random array
            String[] arr2 = copyStringArray(arr1); // Copy for second method
            if (!lowestString1(arr1).equals(lowestString2(arr2))) { // Compare results
                for (String str : arr1) {
                    System.out.print(str + ","); // Print failing case
                }
                System.out.println();
                System.out.println("Oops!"); // Indicate error
            }
        }
        System.out.println("finish!"); // All tests done
    }

    /**
     * Custom comparator for sorting strings.
     * For two strings a and b, compares (a+b) and (b+a) lexicographically.
     * This ensures the concatenation order yields the smallest result.
     */
    public static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a); // Compare concatenated strings
        }
    }

}
