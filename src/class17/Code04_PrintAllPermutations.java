/**
 * STRING PERMUTATION GENERATOR - THREE DIFFERENT APPROACHES
 *
 * PURPOSE: Generate all possible permutations of characters in a string
 *
 * KEY CONCEPTS:
 * - Permutation: Rearrangement of all elements where order matters
 * - For n distinct characters: n! permutations (n factorial)
 * - For n characters with duplicates: fewer permutations (need deduplication)
 *
 * THREE IMPLEMENTATIONS:
 * 1. permutation1(): Uses ArrayList to track remaining characters - O(n!) time, O(n²) space
 * 2. permutation2(): Uses in-place swapping - O(n!) time, O(n) space, allows duplicates
 * 3. permutation3(): Uses in-place swapping with deduplication - O(n!) time, O(n) space
 */

package class17; // Package declaration - organizes code into namespace

import java.util.ArrayList;
import java.util.List;

public class Code04_PrintAllPermutations { // Class declaration - contains three permutation algorithms

    /**
     * APPROACH 1: PERMUTATION WITH ARRAYLIST (REMAINING CHARACTERS TRACKING)
     *
     * ALGORITHM:
     * - Maintain a list of unused characters
     * - At each step, try placing each remaining character next
     * - Remove character from remaining list, recurse, then restore it
     *
     * TIME COMPLEXITY: O(n! × n) - n! permutations, each requiring O(n) list operations
     * SPACE COMPLEXITY: O(n²) - recursion depth n, each level copies ArrayList
     *
     * @param s - input string to generate permutations
     * @return List of all permutation strings
     */
    public static List<String> permutation1(String s) { // Method signature - takes string, returns list of permutations
        List<String> ans = new ArrayList<>(); // Create list to store all permutation results
        // Explanation: This will hold all complete permutations as strings

        if (s == null || s.length() == 0) { // Check for null or empty input
            return ans; // Return empty list for invalid input
            // Explanation: No permutations possible for null/empty string
        }

        char[] str = s.toCharArray(); // Convert string to character array for easier manipulation
        // Explanation: Arrays are more efficient than String for character access

        ArrayList<Character> rest = new ArrayList<Character>(); // Create list to track remaining unused characters
        // Explanation: This list will shrink/grow as we choose characters for permutations

        for (char cha : str) { // Iterate through all characters in input
            rest.add(cha); // Add each character to the remaining list
            // Explanation: Initialize rest with all characters - all are initially available
        }

        String path = ""; // Initialize empty path string to build permutations
        // Explanation: Will accumulate characters as we build each permutation

        f(rest, path, ans); // Start recursive permutation generation
        // Explanation: Begin recursion with all characters available and empty path

        return ans; // Return the complete list of all permutations
        // Explanation: After recursion completes, ans contains all n! permutations
    }

    /**
     * RECURSIVE HELPER FOR APPROACH 1
     *
     * MECHANISM:
     * - Base case: When no characters remain, current path is a complete permutation
     * - Recursive case: Try each remaining character as next choice
     * - For each choice: remove from rest, recurse with extended path, restore rest
     *
     * BACKTRACKING PATTERN:
     * 1. Make choice (remove character)
     * 2. Recurse with new state
     * 3. Undo choice (restore character)
     *
     * @param rest - list of characters not yet used in current permutation
     * @param path - current partial permutation being built
     * @param ans - accumulator list for all complete permutations
     */
    public static void f(ArrayList<Character> rest, String path, List<String> ans) { // Recursive method to generate permutations
        if (rest.isEmpty()) { // Base case: all characters have been used
            ans.add(path); // Add completed permutation to result list
            // Explanation: When rest is empty, path contains a complete permutation
        } else { // Recursive case: still have characters to place
            int N = rest.size(); // Get count of remaining characters to choose from
            // Explanation: Cache size to avoid repeated calls during loop

            for (int i = 0; i < N; i++) { // Try each remaining character as next choice
                // Explanation: Each remaining character gets a chance to be placed next

                char cur = rest.get(i); // Get character at current position
                // Explanation: This is the character we're trying in this iteration

                rest.remove(i); // Remove chosen character from remaining list
                // Explanation: Character is now used, shouldn't be available for deeper recursion

                f(rest, path + cur, ans); // Recurse with reduced rest and extended path
                // Explanation: Build permutations for remaining characters with cur added to path

                rest.add(i, cur); // BACKTRACK: Restore character to original position
                // Explanation: Undo the choice so next loop iteration has correct rest list
            }
        }
        // Explanation: After all iterations, every way to place remaining characters is explored
    }

    /**
     * APPROACH 2: PERMUTATION WITH IN-PLACE SWAPPING (NO DEDUPLICATION)
     *
     * ALGORITHM:
     * - Use index to mark boundary between fixed prefix and remaining suffix
     * - At each step, swap each suffix character to current position
     * - Recurse to fix next position, then swap back to restore state
     *
     * ADVANTAGES OVER APPROACH 1:
     * - No ArrayList allocation/deallocation overhead
     * - Purely in-place modifications
     * - Better space complexity O(n) vs O(n²)
     *
     * DISADVANTAGE:
     * - Generates duplicate permutations if input has duplicate characters
     *
     * TIME COMPLEXITY: O(n! × n) - n! permutations, each requires O(n) to build string
     * SPACE COMPLEXITY: O(n) - recursion depth only
     *
     * @param s - input string to generate permutations
     * @return List of all permutation strings (may contain duplicates)
     */
    public static List<String> permutation2(String s) { // Method signature - in-place swapping approach
        List<String> ans = new ArrayList<>(); // Create result list
        // Explanation: Will store all generated permutations

        if (s == null || s.length() == 0) { // Validate input
            return ans; // Return empty list for invalid input
            // Explanation: Edge case handling for null/empty strings
        }

        char[] str = s.toCharArray(); // Convert to mutable character array
        // Explanation: Need array for in-place swapping operations

        g1(str, 0, ans); // Start recursion from index 0 (beginning of string)
        // Explanation: Index 0 means no characters are fixed yet

        return ans; // Return all generated permutations
        // Explanation: Contains all n! permutations (with duplicates if input has duplicates)
    }

    /**
     * RECURSIVE HELPER FOR APPROACH 2
     *
     * VISUALIZATION (for "abc"):
     * index=0: try 'a','b','c' at position 0
     *   index=1: for each, try remaining chars at position 1
     *     index=2: for each, only one char left at position 2
     *       index=3: base case - complete permutation
     *
     * INVARIANT: Characters at indices [0, index-1] are fixed (part of current partial permutation)
     *            Characters at indices [index, n-1] are candidates for position index
     *
     * @param str - character array being permuted
     * @param index - current position being filled (boundary between fixed and unfixed)
     * @param ans - accumulator for all complete permutations
     */
    public static void g1(char[] str, int index, List<String> ans) { // Recursive permutation with swapping
        if (index == str.length) { // Base case: all positions filled
            ans.add(String.valueOf(str)); // Convert char array to string and add to results
            // Explanation: When index reaches length, we have a complete permutation
        } else { // Recursive case: still have positions to fill
            for (int i = index; i < str.length; i++) { // Try each unfixed character at current position
                // Explanation: Loop from index to end, each character gets chance to be at position index

                swap(str, index, i); // Swap character at i to position index
                // Explanation: Place character i at the current position we're fixing

                g1(str, index + 1, ans); // Recurse to fill next position
                // Explanation: With position index fixed, recursively fill positions [index+1, n-1]

                swap(str, index, i); // BACKTRACK: Swap back to restore original order
                // Explanation: Undo the swap so next loop iteration starts with correct state
            }
        }
        // Explanation: After loop, all ways to arrange characters from index onward are explored
    }

    /**
     * APPROACH 3: PERMUTATION WITH DEDUPLICATION
     *
     * ENHANCEMENT OVER APPROACH 2:
     * - Adds duplicate prevention mechanism
     * - Uses boolean array to track which characters already tried at current position
     * - Skips characters that were already used at this position in previous iterations
     *
     * DEDUPLICATION STRATEGY:
     * - At each recursion level, track which character values we've already tried
     * - If we see the same character value again, skip it (already generated those permutations)
     * - This prevents generating duplicate permutations when input has repeated characters
     *
     * EXAMPLE: For "acc"
     * - Without dedup: generates "acc", "acc", "cac", "cca", "cac", "cca" (6 perms with dupes)
     * - With dedup: generates "acc", "cac", "cca" (3 unique perms)
     *
     * TIME COMPLEXITY: O(n! × n) - generates only unique permutations
     * SPACE COMPLEXITY: O(n) - recursion depth + O(256) for visited array at each level
     *
     * @param s - input string to generate permutations
     * @return List of all unique permutation strings
     */
    public static List<String> permutation3(String s) { // Method signature - deduplication approach
        List<String> ans = new ArrayList<>(); // Create result list
        // Explanation: Will store unique permutations only

        if (s == null || s.length() == 0) { // Validate input
            return ans; // Return empty list for invalid input
            // Explanation: Handle edge cases consistently
        }

        char[] str = s.toCharArray(); // Convert to character array
        // Explanation: Need mutable array for swapping

        g2(str, 0, ans); // Start recursion with deduplication
        // Explanation: Begin filling positions from index 0

        return ans; // Return list of unique permutations
        // Explanation: No duplicate permutations even if input has duplicate characters
    }

    /**
     * RECURSIVE HELPER FOR APPROACH 3 (WITH DEDUPLICATION)
     *
     * KEY DIFFERENCE FROM g1():
     * - Maintains visited[] array at each recursion level
     * - Only processes each distinct character value once per position
     *
     * DEDUPLICATION LOGIC:
     * - visited[char] tracks if we've already tried this character at current position
     * - If character was seen before at this level, skip it
     * - This prevents generating duplicate branches in recursion tree
     *
     * WHY THIS WORKS:
     * - If we have two 'a's in remaining positions, swapping first 'a' to current position
     *   generates same permutations as swapping second 'a' to current position
     * - So we only need to try one 'a' at each position
     *
     * @param str - character array being permuted
     * @param index - current position being filled
     * @param ans - accumulator for unique permutations
     */
    public static void g2(char[] str, int index, List<String> ans) { // Recursive permutation with deduplication
        if (index == str.length) { // Base case: all positions filled
            ans.add(String.valueOf(str)); // Add complete permutation to results
            // Explanation: Found a unique complete permutation
        } else { // Recursive case: fill current position
            boolean[] visited = new boolean[256]; // Create visited array for ASCII characters
            // Explanation: Track which character values we've tried at this recursion level
            // Array size 256 covers all possible char values (0-255 for extended ASCII)

            for (int i = index; i < str.length; i++) { // Iterate through unfixed characters
                // Explanation: Consider each remaining character as candidate for current position

                if (!visited[str[i]]) { // Check if this character value was already tried at this position
                    // Explanation: Only process if we haven't seen this character value before at this level

                    visited[str[i]] = true; // Mark this character value as tried
                    // Explanation: Prevent trying same character value again at this position

                    swap(str, index, i); // Swap character to current position
                    // Explanation: Place character at the position we're fixing

                    g2(str, index + 1, ans); // Recurse to fill next position
                    // Explanation: Generate all permutations with this character fixed at current position

                    swap(str, index, i); // BACKTRACK: Restore original order
                    // Explanation: Undo swap for next iteration
                }
                // Explanation: If visited[str[i]] is true, skip this iteration to avoid duplicates
            }
        }
        // Explanation: visited[] is local to each recursion level, so different levels can try same characters
    }

    /**
     * UTILITY METHOD: SWAP TWO CHARACTERS IN ARRAY
     *
     * PURPOSE: Exchange characters at two positions in-place
     * PATTERN: Classic three-step swap using temporary variable
     *
     * @param chs - character array to modify
     * @param i - first index to swap
     * @param j - second index to swap
     */
    public static void swap(char[] chs, int i, int j) { // Helper method to swap two characters
        char tmp = chs[i]; // Save character at position i
        // Explanation: Need temporary storage since we're about to overwrite chs[i]

        chs[i] = chs[j]; // Copy character from j to i
        // Explanation: First half of the swap

        chs[j] = tmp; // Copy saved character to j
        // Explanation: Complete the swap using saved value
    }

    /**
     * TEST DRIVER
     *
     * PURPOSE: Demonstrate and compare all three approaches
     * INPUT: "acc" - string with duplicate characters to show deduplication effect
     *
     * EXPECTED OUTPUTS:
     * - permutation1(): acc, acc, cac, cca, cac, cca (6 results with duplicates)
     * - permutation2(): acc, acc, cac, cca, cac, cca (6 results with duplicates)
     * - permutation3(): acc, cac, cca (3 unique results)
     */
    public static void main(String[] args) { // Main method - entry point for testing
        String s = "acc"; // Test string with duplicate characters
        // Explanation: Chosen to demonstrate deduplication - has one 'a' and two 'c's

        List<String> ans1 = permutation1(s); // Test approach 1: ArrayList-based
        // Explanation: Generate permutations using remaining character list approach

        for (String str : ans1) { // Print all permutations from approach 1
            System.out.println(str); // Output each permutation
            // Explanation: Display results for verification
        }

        System.out.println("======="); // Print separator between approaches
        // Explanation: Visual separator for readability

        List<String> ans2 = permutation2(s); // Test approach 2: swapping without dedup
        // Explanation: Generate permutations using in-place swapping

        for (String str : ans2) { // Print all permutations from approach 2
            System.out.println(str); // Output each permutation
            // Explanation: Shows that this approach generates duplicates
        }

        System.out.println("======="); // Print separator
        // Explanation: Visual separator for readability

        List<String> ans3 = permutation3(s); // Test approach 3: swapping with dedup
        // Explanation: Generate unique permutations only

        for (String str : ans3) { // Print all unique permutations from approach 3
            System.out.println(str); // Output each permutation
            // Explanation: Shows that this approach eliminates duplicates
        }
        // Explanation: Comparison shows trade-offs: approach 3 is most efficient for strings with duplicates
    }
}
