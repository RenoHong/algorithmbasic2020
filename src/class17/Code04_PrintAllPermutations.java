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
 *
 * ============================================================================
 * VISUALIZATION OF PERMUTATION CONCEPT (for "abc"):
 * ============================================================================
 * Position:  [0] [1] [2]
 *
 * Permutations (3! = 6 total):
 *   1. a b c
 *   2. a c b
 *   3. b a c
 *   4. b c a
 *   5. c a b
 *   6. c b a
 *
 * DECISION TREE:
 *           (start)
 *          /   |   \
 *         a    b    c         <- Position 0: choose first char
 *        / \  / \  / \
 *       b c a c a b           <- Position 1: choose second char
 *       | | | | | |
 *       c b c a b a           <- Position 2: choose last char
 *       | | | | | |
 *      abc acb bac bca cab cba <- Complete permutations
 */

package class17;

import java.util.ArrayList;
import java.util.List;

public class Code04_PrintAllPermutations {

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
     * ============================================================================
     * EXECUTION TRACE FOR "ab":
     * ============================================================================
     * Initial: rest=[a,b], path=""
     *
     * Step 1: Choose 'a'
     *   rest=[b], path="a"
     *   Step 1.1: Choose 'b'
     *     rest=[], path="ab" → OUTPUT: "ab"
     *   [Backtrack: restore 'b']
     *
     * Step 2: Choose 'b'
     *   rest=[a], path="b"
     *   Step 2.1: Choose 'a'
     *     rest=[], path="ba" → OUTPUT: "ba"
     *   [Backtrack: restore 'a']
     *
     * Results: ["ab", "ba"]
     *
     * @param s - input string to generate permutations
     * @return List of all permutation strings
     */
    public static List<String> permutation1(String s) {
        List<String> ans = new ArrayList<>(); // Result container - stores all permutations

        if (s == null || s.length() == 0) { // Guard clause for invalid input
            return ans; // Early return with empty list
        }

        char[] str = s.toCharArray(); // Convert string to char array for iteration

        ArrayList<Character> rest = new ArrayList<Character>(); // Mutable list of available characters

        for (char cha : str) { // Populate rest with all input characters
            rest.add(cha); // Each character is initially available
        }

        String path = ""; // Accumulator for current permutation being built

        f(rest, path, ans); // Kick off recursive generation

        return ans; // Return completed list of all permutations
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
     * ============================================================================
     * RECURSION TREE FOR "abc":
     * ============================================================================
     *                    f(rest=[a,b,c], path="", ans)
     *                   /          |           \
     *                  /           |            \
     *     f([b,c],"a") ──┐  f([a,c],"b") ──┐  f([a,b],"c") ──┐
     *        /  \           \      /  \           \      /  \           \
     *       /    \           \    /    \           \    /    \           \
     *   f([],"ab") f([],"ac")   f([],"ba") f([],"bc")   f([],"ca") f([],"cb")
     *      ↓         ↓              ↓         ↓              ↓         ↓
     *    "abc"     "acb"          "bac"     "bca"          "cab"     "cba"
     *
     * BACKTRACKING ILLUSTRATION:
     * rest=[a,b,c], path=""
     *   → Choose 'a': rest=[b,c], path="a"
     *     → Choose 'b': rest=[c], path="ab"
     *       → Choose 'c': rest=[], path="abc" ✓ OUTPUT
     *       ← Restore 'c': rest=[c], path="ab"
     *     ← Restore 'b': rest=[b,c], path="a"
     *     → Choose 'c': rest=[b], path="ac"
     *       ...continues...
     *
     * @param rest - list of characters not yet used in current permutation
     * @param path - current partial permutation being built
     * @param ans - accumulator list for all complete permutations
     */
    public static void f(ArrayList<Character> rest, String path, List<String> ans) {
        if (rest.isEmpty()) { // Base case: no more characters to choose
            ans.add(path); // Current path is complete permutation - add to results
        } else { // Recursive case: still building permutation
            int N = rest.size(); // Number of choices available at this step

            for (int i = 0; i < N; i++) { // Try each remaining character
                // Loop invariant: rest contains exactly the characters not yet in path

                char cur = rest.get(i); // Select character at index i

                rest.remove(i); // Remove selected character from available pool
                // State change: rest.size() decreases by 1

                f(rest, path + cur, ans); // Recurse with extended path
                // Recursive call: path grows by 1 char, rest shrinks by 1 char

                rest.add(i, cur); // BACKTRACK: Restore character at original position
                // Critical: must add at index i to maintain order for next iteration
                // State restored: rest is exactly as it was before this iteration
            }
        }
        // Post-condition: rest is unchanged after all iterations (due to backtracking)
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
     * ============================================================================
     * SWAPPING VISUALIZATION FOR "abc":
     * ============================================================================
     * Initial: ['a','b','c'], index=0
     *
     * Fixed: [] | Remaining: [a,b,c]
     *   Swap(0,0): [a,b,c] → recurse(index=1)
     *     Fixed: [a] | Remaining: [b,c]
     *       Swap(1,1): [a,b,c] → recurse(index=2)
     *         Fixed: [a,b] | Remaining: [c]
     *           Swap(2,2): [a,b,c] → OUTPUT "abc"
     *       Swap(1,2): [a,c,b] → recurse(index=2)
     *         Fixed: [a,c] | Remaining: [b]
     *           Swap(2,2): [a,c,b] → OUTPUT "acb"
     *   Swap(0,1): [b,a,c] → recurse(index=1)
     *     ...continues for all positions...
     *
     * ARRAY STATE CHANGES (partial trace):
     * [a,b,c] swap(0,0)→ [a,b,c] swap(1,1)→ [a,b,c] → "abc"
     * [a,b,c] swap(1,1)← [a,b,c] swap(1,2)→ [a,c,b] → "acb"
     * [a,c,b] swap(1,2)← [a,b,c]
     * [a,b,c] swap(0,0)← [a,b,c] swap(0,1)→ [b,a,c] → ...
     *
     * @param s - input string to generate permutations
     * @return List of all permutation strings (may contain duplicates)
     */
    public static List<String> permutation2(String s) {
        List<String> ans = new ArrayList<>(); // Container for results

        if (s == null || s.length() == 0) { // Input validation
            return ans; // Empty list for invalid input
        }

        char[] str = s.toCharArray(); // Convert to mutable array for swapping

        g1(str, 0, ans); // Start recursion from position 0 (no characters fixed yet)

        return ans; // Return all generated permutations
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
     * ============================================================================
     * DETAILED EXECUTION TRACE FOR "ab":
     * ============================================================================
     * g1(['a','b'], 0, ans)
     *   i=0: swap(0,0) → ['a','b']
     *     g1(['a','b'], 1, ans)
     *       i=1: swap(1,1) → ['a','b']
     *         g1(['a','b'], 2, ans)
     *           index=2=length → OUTPUT "ab"
     *         swap(1,1) → ['a','b'] (no change)
     *     swap(0,0) → ['a','b'] (no change)
     *
     *   i=1: swap(0,1) → ['b','a']
     *     g1(['b','a'], 1, ans)
     *       i=1: swap(1,1) → ['b','a']
     *         g1(['b','a'], 2, ans)
     *           index=2=length → OUTPUT "ba"
     *         swap(1,1) → ['b','a'] (no change)
     *     swap(0,1) → ['a','b'] (restored)
     *
     * KEY OBSERVATIONS:
     * - Each swap moves a character from "remaining" zone to "fixed" zone
     * - Backtracking (second swap) restores array for next iteration
     * - Index acts as partition: [0..index-1]=fixed, [index..n-1]=remaining
     *
     * @param str - character array being permuted
     * @param index - current position being filled (boundary between fixed and unfixed)
     * @param ans - accumulator for all complete permutations
     */
    public static void g1(char[] str, int index, List<String> ans) {
        if (index == str.length) { // Base case: all positions have been filled
            ans.add(String.valueOf(str)); // Convert array to string and save
            // At this point, str represents one complete permutation
        } else { // Recursive case: position 'index' needs to be filled
            // Loop invariant: str[0..index-1] is the fixed prefix
            //                 str[index..n-1] contains candidates for position 'index'

            for (int i = index; i < str.length; i++) { // Try each candidate
                // Iteration i: try placing str[i] at position 'index'

                swap(str, index, i); // Move candidate to current position
                // Effect: str[index] now holds the character we're trying
                //         str[i] now holds what was at str[index]

                g1(str, index + 1, ans); // Recurse to fill next position
                // Recursive call operates on str[index+1..n-1] with str[0..index] fixed

                swap(str, index, i); // BACKTRACK: undo the swap
                // Critical: restores str to state before this iteration
                // Ensures next iteration of loop sees correct str state
            }
            // Post-loop: str is in original state due to backtracking
        }
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
     * ============================================================================
     * DEDUPLICATION VISUALIZATION FOR "acc":
     * ============================================================================
     * Without dedup (approach 2):
     *   Position 0: try 'a', 'c'₁, 'c'₂
     *     'a' at pos 0 → [a,c₁,c₂] → generates "acc₁c₂", "ac₂c₁"
     *     'c'₁ at pos 0 → [c₁,a,c₂] → generates "c₁ac₂", "c₁c₂a"
     *     'c'₂ at pos 0 → [c₂,a,c₁] → generates "c₂ac₁", "c₂c₁a" ← DUPLICATE!
     *   Result: 6 permutations (but c₁ and c₂ are identical)
     *
     * With dedup (approach 3):
     *   Position 0: try 'a', 'c'₁ (skip 'c'₂ because visited['c']=true)
     *     'a' at pos 0 → [a,c₁,c₂] → generates "acc"
     *     'c'₁ at pos 0 → [c₁,a,c₂] → generates "cac", "cca"
     *   Result: 3 unique permutations
     *
     * VISITED ARRAY MECHANISM:
     * g2([a,c,c], 0, ans)
     *   visited = [false × 256]
     *   i=0: str[0]='a', visited['a']=false
     *     → visited['a']=true, swap(0,0), recurse
     *   i=1: str[1]='c', visited['c']=false
     *     → visited['c']=true, swap(0,1), recurse
     *   i=2: str[2]='c', visited['c']=true ← SKIP! Already tried 'c'
     *
     * @param s - input string to generate permutations
     * @return List of all unique permutation strings
     */
    public static List<String> permutation3(String s) {
        List<String> ans = new ArrayList<>(); // Result container

        if (s == null || s.length() == 0) { // Input validation
            return ans; // Empty result for invalid input
        }

        char[] str = s.toCharArray(); // Convert to char array

        g2(str, 0, ans); // Start recursion with deduplication enabled

        return ans; // Return unique permutations only
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
     * ============================================================================
     * EXECUTION TRACE FOR "acc":
     * ============================================================================
     * g2(['a','c','c'], 0, ans)
     *   visited = [false × 256]
     *   i=0: str[0]='a' (97), visited[97]=false
     *     visited[97]=true
     *     swap(0,0) → ['a','c','c']
     *     g2(['a','c','c'], 1, ans)
     *       visited = [false × 256]  ← NEW visited array for this level
     *       i=1: str[1]='c' (99), visited[99]=false
     *         visited[99]=true
     *         swap(1,1) → ['a','c','c']
     *         g2(['a','c','c'], 2, ans)
     *           visited = [false × 256]
     *           i=2: str[2]='c' (99), visited[99]=false
     *             visited[99]=true
     *             swap(2,2) → ['a','c','c']
     *             g2(['a','c','c'], 3, ans)
     *               index=3=length → OUTPUT "acc"
     *             swap(2,2) → ['a','c','c']
     *       i=2: str[2]='c' (99), visited[99]=true ← SKIP!
     *     swap(0,0) → ['a','c','c']
     *
     *   i=1: str[1]='c' (99), visited[99]=false
     *     visited[99]=true
     *     swap(0,1) → ['c','a','c']
     *     g2(['c','a','c'], 1, ans)
     *       ...generates "cac" and "cca"...
     *     swap(0,1) → ['a','c','c']
     *
     *   i=2: str[2]='c' (99), visited[99]=true ← SKIP! (already tried 'c' at i=1)
     *
     * CRITICAL INSIGHT:
     * - visited[] is LOCAL to each recursion level (created fresh each time)
     * - This allows same character to be used at different positions
     * - But prevents same character from being tried twice at SAME position
     *
     * @param str - character array being permuted
     * @param index - current position being filled
     * @param ans - accumulator for unique permutations
     */
    public static void g2(char[] str, int index, List<String> ans) {
        if (index == str.length) { // Base case: complete permutation formed
            ans.add(String.valueOf(str)); // Add to results
        } else { // Recursive case: fill position 'index'
            boolean[] visited = new boolean[256]; // Deduplication tracker for this level
            // Array size 256: covers all ASCII characters (0-255)
            // Default values: all false (no characters tried yet at this position)

            for (int i = index; i < str.length; i++) { // Consider each candidate
                // Loop examines str[index..n-1] as candidates for position 'index'

                if (!visited[str[i]]) { // Check: have we tried this character value before?
                    // visited[str[i]] uses character as array index (implicit cast to int)
                    // true = already tried this char value at this position
                    // false = first time seeing this char value at this position

                    visited[str[i]] = true; // Mark: we're now trying this character
                    // Prevents processing same character value again in this loop

                    swap(str, index, i); // Place candidate at current position

                    g2(str, index + 1, ans); // Recurse to fill next position
                    // Note: next level gets its own fresh visited[] array

                    swap(str, index, i); // BACKTRACK: restore array
                }
                // If visited[str[i]] is true: skip this iteration entirely
                // Effect: same character value only processed once per recursion level
            }
            // Post-condition: every distinct character value tried exactly once
        }
    }

    /**
     * UTILITY METHOD: SWAP TWO CHARACTERS IN ARRAY
     *
     * PURPOSE: Exchange characters at two positions in-place
     * PATTERN: Classic three-step swap using temporary variable
     *
     * ============================================================================
     * SWAP MECHANICS:
     * ============================================================================
     * Before: chs[i]=X, chs[j]=Y
     *
     * Step 1: tmp = chs[i]        → tmp=X
     * Step 2: chs[i] = chs[j]     → chs[i]=Y
     * Step 3: chs[j] = tmp        → chs[j]=X
     *
     * After:  chs[i]=Y, chs[j]=X
     *
     * EXAMPLE:
     * chs = ['a','b','c'], swap(0,2)
     * tmp = 'a'              (save chs[0])
     * chs[0] = 'c'           (copy chs[2] to chs[0])
     * chs[2] = 'a'           (restore saved value to chs[2])
     * Result: ['c','b','a']
     *
     * @param chs - character array to modify
     * @param i - first index to swap
     * @param j - second index to swap
     */
    public static void swap(char[] chs, int i, int j) {
        char tmp = chs[i]; // Temporary storage: save value at position i
        // Required because next step overwrites chs[i]

        chs[i] = chs[j]; // Copy: move value from position j to position i
        // Now chs[i] has the value we want

        chs[j] = tmp; // Restore: place saved value at position j
        // Now chs[j] has the original value from chs[i]
        // Swap complete: values at i and j are exchanged
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
     *
     * ============================================================================
     * OUTPUT COMPARISON:
     * ============================================================================
     * Input: "acc" (1 'a', 2 'c's)
     *
     * Approach 1 (ArrayList):        Approach 2 (Swap):           Approach 3 (Dedup):
     * acc  ┐                         acc  ┐                       acc
     * acc  │← duplicates             acc  │← duplicates           cac
     * cac  │                         cac  │                       cca
     * cca  │                         cca  │
     * cac  │← duplicates             cac  │← duplicates
     * cca  ┘                         cca  ┘
     *
     * Total: 6 (with dupes)          Total: 6 (with dupes)        Total: 3 (unique)
     *
     * PERFORMANCE ANALYSIS:
     * Approach 1: Slowest (ArrayList overhead) but conceptually simple
     * Approach 2: Faster (in-place) but generates duplicates
     * Approach 3: Fastest for inputs with duplicates (generates fewer results)
     */
    public static void main(String[] args) {
        String s = "acc"; // Test input with duplicates
        // Chosen specifically to demonstrate deduplication behavior
        // Contains: 1 'a' and 2 identical 'c' characters

        List<String> ans1 = permutation1(s); // Test: ArrayList-based approach

        for (String str : ans1) { // Display all permutations from approach 1
            System.out.println(str);
        }

        System.out.println("======="); // Visual separator

        List<String> ans2 = permutation2(s); // Test: swap-based approach (no dedup)

        for (String str : ans2) { // Display all permutations from approach 2
            System.out.println(str); // Will show duplicate permutations
        }

        System.out.println("======="); // Visual separator

        List<String> ans3 = permutation3(s); // Test: swap-based with deduplication

        for (String str : ans3) { // Display unique permutations only
            System.out.println(str); // No duplicates in output
        }

        // Comparison demonstrates:
        // 1. All approaches generate correct permutations
        // 2. Approaches 1 & 2 produce duplicates for inputs with repeated characters
        // 3. Approach 3 efficiently eliminates duplicates during generation
        // 4. For "acc": approach 3 generates 3 results vs 6 for others (50% reduction)
    }
}
