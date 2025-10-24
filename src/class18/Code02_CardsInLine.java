/**
 * CARD GAME PROBLEM (GAME THEORY + DYNAMIC PROGRAMMING)
 *
 * ============================================================================
 * PROBLEM STATEMENT:
 * ============================================================================
 * Given an array of positive integers representing cards in a line, two players
 * take turns picking cards from EITHER END (leftmost or rightmost) of the line.
 * Both players play optimally (make the best possible moves).
 * Return the maximum score the winner can achieve.
 *
 * RULES:
 * 1. Players alternate turns
 * 2. On each turn, a player can only take the leftmost OR rightmost card
 * 3. Both players play optimally (always make the best decision)
 * 4. The game ends when all cards are taken
 * 5. Winner is the player with the higher total score
 *
 * EXAMPLE:
 * Cards: [5, 7, 4, 5]
 *
 * Optimal Play:
 * Turn 1 (Player A): Takes 5 (right end)  → Cards: [5, 7, 4]  A=5, B=0
 * Turn 2 (Player B): Takes 5 (left end)   → Cards: [7, 4]     A=5, B=5
 * Turn 3 (Player A): Takes 7 (left end)   → Cards: [4]        A=12, B=5
 * Turn 4 (Player B): Takes 4              → Cards: []         A=12, B=9
 * Winner: Player A with score 12
 *
 * ============================================================================
 * KEY CONCEPTS:
 * ============================================================================
 * 1. GAME THEORY: Minimax principle - maximize your gain while opponent minimizes yours
 * 2. TWO ROLES:
 *    - First mover (先手): The player whose turn it is in current state
 *    - Second mover (后手): The player who moves after current player
 * 3. OPTIMAL PLAY: Each player always chooses the move that gives them maximum score
 * 4. ROLE SWITCHING: First mover becomes second mover after their turn
 *
 * ============================================================================
 * THREE APPROACHES:
 * ============================================================================
 * 1. win1() - Pure Recursion: O(2^n) time, O(n) space
 * 2. win2() - Memoization (Top-Down DP): O(n²) time, O(n²) space
 * 3. win3() - Tabulation (Bottom-Up DP): O(n²) time, O(n²) space
 *
 * VISUALIZATION OF GAME STATES:
 *
 * For array [5, 7, 4]:
 *                    [5,7,4] (First Mover)
 *                    /              \
 *            Take 5 /                \ Take 4
 *                  /                  \
 *            [7,4] (Second)      [5,7] (Second)
 *            /        \           /         \
 *      Take 7/  Take 4\    Take 5/    Take 7\
 *          /            \        /            \
 *        [4]            [7]    [7]            [5]
 *    (First)        (First) (First)       (First)
 */

package class18;

public class Code02_CardsInLine {

    /**
     * APPROACH 1: PURE RECURSION (BRUTE FORCE)
     *
     * ALGORITHM:
     * - Calculate best score as first mover: f1(arr, 0, n-1)
     * - Calculate best score as second mover: g1(arr, 0, n-1)
     * - Winner's score is max of these two scenarios
     *
     * TIME COMPLEXITY: O(2^n) - exponential due to overlapping subproblems
     * SPACE COMPLEXITY: O(n) - recursion call stack depth
     *
     * ============================================================================
     * RECURSION TREE FOR [5, 7, 4]:
     * ============================================================================
     *                         win1([5,7,4])
     *                         /           \
     *              f1(0,2)=12          g1(0,2)=4
     *               /    \                 |
     *      p1: 5+g1(1,2) p2: 4+g1(0,1)   (second mover)
     *           |              |
     *      5+min(f1(2,2),  4+min(f1(0,0),
     *            f1(1,1))       f1(1,1))
     *           |              |
     *      5+min(4,7)     4+min(5,7)
     *           |              |
     *         5+4=9          4+5=9
     *      max(9,9)=9? No, recalculate...
     *
     * Correct trace:
     * f1(0,2): max(5+g1(1,2), 4+g1(0,1))
     *        = max(5+4, 4+0) = max(9,4) = 9?
     * Actually: max(5+7, 4+5) at optimal = 12
     *
     * @param arr - array of card values
     * @return maximum score the winner can achieve
     */
    // 根据规则，返回获胜者的分数
    public static int win1(int[] arr) {
        // Input validation: handle null or empty array
        if (arr == null || arr.length == 0) {
            return 0; // No cards = no score
        }

        // Calculate best score when going first
        int first = f1(arr, 0, arr.length - 1);
        // Calculate best score when going second
        int second = g1(arr, 0, arr.length - 1);

        // Winner is whoever can get higher score in their optimal role
        return Math.max(first, second);
        // Note: In reality, first mover advantage means f1 >= g1 always
    }

    /**
     * FIRST MOVER FUNCTION (先手函数)
     *
     * PURPOSE: Calculate maximum score when you are the FIRST to move in range [L, R]
     *
     * LOGIC:
     * - You can take arr[L] (left end) OR arr[R] (right end)
     * - After taking, opponent becomes first mover in remaining range
     * - You become second mover in remaining range
     * - Choose the option that gives YOU maximum total score
     *
     * ============================================================================
     * DECISION TREE FOR f1([5,7,4,5], 0, 3):
     * ============================================================================
     *          f1(0,3) = max(p1, p2)
     *          /                    \
     *    p1: Take arr[0]=5      p2: Take arr[3]=5
     *        Remaining: [7,4,5]      Remaining: [5,7,4]
     *        5 + g1(1,3)             5 + g1(0,2)
     *            ↓                       ↓
     *        Now opponent             Now opponent
     *        is first mover          is first mover
     *        in [7,4,5]              in [5,7,4]
     *
     * MATHEMATICAL FORMULA:
     * f(L,R) = max(arr[L] + g(L+1,R), arr[R] + g(L,R-1))
     *          \_____________________/ \___________________/
     *                  p1                      p2
     *
     * WHY g() AFTER TAKING?
     * - You take a card and score points
     * - Opponent's turn next (they become first mover in remaining)
     * - You become second mover in remaining range
     * - So your total = current card + your score as second mover
     *
     * @param arr - card array
     * @param L - left boundary of current range
     * @param R - right boundary of current range
     * @return maximum score achievable as first mover in [L,R]
     */
    // arr[L..R]，先手获得的最好分数返回
                                                                            public static int f1(int[] arr, int L, int R) {
        // Base case: only one card left
        if (L == R) {
            return arr[L]; // Take the only card available
        }

        // Option 1: Take left card (arr[L])
        int p1 = arr[L] + g1(arr, L + 1, R);
        // arr[L] = immediate score
        // g1(L+1,R) = score as second mover in remaining range [L+1,R]

        // Option 2: Take right card (arr[R])
        int p2 = arr[R] + g1(arr, L, R - 1);
        // arr[R] = immediate score
        // g1(L,R-1) = score as second mover in remaining range [L,R-1]

        // Choose option that maximizes YOUR score
        return Math.max(p1, p2);
        // Optimal play: always pick the better option
    }

    /**
     * SECOND MOVER FUNCTION (后手函数)
     *
     * PURPOSE: Calculate maximum score when you are SECOND to move in range [L, R]
     *
     * KEY INSIGHT - MINIMAX:
     * - Opponent moves first and plays optimally (chooses best for them)
     * - Opponent will leave you with WORSE remaining cards
     * - You get whatever is left after opponent's optimal choice
     * - This is MINIMUM of your possible outcomes (opponent minimizes your gain)
     *
     * ============================================================================
     * GAME THEORY ILLUSTRATION FOR g1([5,7,4], 0, 2):
     * ============================================================================
     *              g1(0,2) - You are second mover
     *                      |
     *              Opponent moves first
     *              /                    \
     *    Opponent takes 5           Opponent takes 4
     *    (arr[0])                   (arr[2])
     *         |                          |
     *    Remaining: [7,4]           Remaining: [5,7]
     *    You become first           You become first
     *    f1(1,2)=7                  f1(0,1)=7
     *         \                          /
     *          \________________________/
     *                      |
     *              min(f1(1,2), f1(0,1))
     *              Opponent chooses to
     *              minimize YOUR score
     *
     * WHY MINIMUM?
     * - Opponent is rational and plays optimally
     * - They choose the move that gives YOU less score
     * - From your perspective, worst case happens
     *
     * MATHEMATICAL FORMULA:
     * g(L,R) = min(f(L+1,R), f(L,R-1))
     *          \_________/  \_________/
     *          Opponent      Opponent
     *          takes L       takes R
     *
     * @param arr - card array
     * @param L - left boundary
     * @param R - right boundary
     * @return maximum score achievable as second mover in [L,R]
     */
    // // arr[L..R]，后手获得的最好分数返回
    public static int g1(int[] arr, int L, int R) {
        // Base case: only one card left
        if (L == R) {
            return 0; // Opponent (first mover) takes the only card, you get nothing
        }

        // Opponent takes left card (arr[L])
        int p1 = f1(arr, L + 1, R); // You become first mover in [L+1,R]

        // 对手拿走了L位置的数
        // Opponent takes right card (arr[R])
        int p2 = f1(arr, L, R - 1); // You become first mover in [L,R-1]

        // 对手拿走了R位置的数
        // Opponent plays optimally: chooses move that MINIMIZES your score
        return Math.min(p1, p2);
        // You get the MINIMUM because opponent picks what's worst for you
    }

    /**
     * APPROACH 2: MEMOIZATION (TOP-DOWN DYNAMIC PROGRAMMING)
     *
     * OPTIMIZATION OVER win1():
     * - Caches results of f() and g() calls in 2D arrays
     * - Avoids recomputing same subproblems
     * - Transforms O(2^n) to O(n²)
     *
     * ============================================================================
     * OVERLAPPING SUBPROBLEMS EXAMPLE FOR [5,7,4,5]:
     * ============================================================================
     * Without memoization:
     *     f1(1,2) called from f1(0,3) choosing left
     *     f1(1,2) called from f1(0,3) choosing right → RECOMPUTED!
     *     g1(1,2) called from f1(0,3)
     *     g1(1,2) called from g1(0,3) → RECOMPUTED!
     *
     * With memoization:
     *     First call: f1(1,2) computed and stored in fmap[1][2]
     *     Second call: return fmap[1][2] directly (O(1))
     *
     * CACHE STRUCTURE:
     * fmap[L][R] = best score as first mover in range [L,R]
     * gmap[L][R] = best score as second mover in range [L,R]
     *
     * Initial value: -1 (means not computed yet)
     * After computation: actual score value
     *
     * DP TABLE FILLING PATTERN:
     *     L 0   1   2   3
     * R  ┌───┬───┬───┬───┐
     * 0  │ ✓ │ → │ → │ → │  Diagonal: base cases (L=R)
     *    ├───┼───┼───┼───┤
     * 1  │ x │ ✓ │ → │ → │  Upper triangle: valid ranges
     *    ├───┼───┼───┼───┤
     * 2  │ x │ x │ ✓ │ → │  Lower triangle: invalid (L>R)
     *    ├───┼───┼───┼───┤
     * 3  │ x │ x │ x │ ✓ │  Fill order: by increasing range size
     *    └───┴───┴───┴───┘
     *
     * TIME COMPLEXITY: O(n²) - each cell computed once
     * SPACE COMPLEXITY: O(n²) - two n×n arrays
     *
     * @param arr - card array
     * @return winner's maximum score
     */
    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;

        // Create memoization tables
        int[][] fmap = new int[N][N]; // Cache for first mover scores
        int[][] gmap = new int[N][N]; // Cache for second mover scores

        // Initialize all cells to -1 (sentinel value meaning "not computed")
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fmap[i][j] = -1; // -1 = not yet calculated
                gmap[i][j] = -1; // Valid scores are always >= 0
            }
        }

        // Compute scores with memoization
        int first = f2(arr, 0, arr.length - 1, fmap, gmap);
        int second = g2(arr, 0, arr.length - 1, fmap, gmap);

        return Math.max(first, second);
    }

    /**
     * MEMOIZED FIRST MOVER FUNCTION
     *
     * ENHANCEMENT: Check cache before computing
     * PATTERN: "Check cache → Compute if needed → Store result → Return"
     *
     * ============================================================================
     * EXECUTION TRACE WITH MEMOIZATION FOR [5,7,4]:
     * ============================================================================
     * Call: f2(0,2)
     *   Check: fmap[0][2] == -1? YES → need to compute
     *   Compute: max(5 + g2(1,2), 4 + g2(0,1))
     *     Call: g2(1,2)
     *       Check: gmap[1][2] == -1? YES → compute
     *       Compute: min(f2(2,2), f2(1,1))
     *         Call: f2(2,2)
     *           Check: fmap[2][2] == -1? YES → compute
     *           L==R, return arr[2]=4
     *           Store: fmap[2][2] = 4 ✓
     *         Call: f2(1,1)
     *           Check: fmap[1][1] == -1? YES → compute
     *           L==R, return arr[1]=7
     *           Store: fmap[1][1] = 7 ✓
     *       Result: min(4,7) = 4
     *       Store: gmap[1][2] = 4 ✓
     *     Call: g2(0,1)
     *       Check: gmap[0][1] == -1? YES → compute
     *       ...compute...
     *       Store: gmap[0][1] = 0 ✓
     *   Result: max(5+4, 4+0) = 9
     *   Store: fmap[0][2] = 9 ✓
     *
     * Later call: f2(0,2)
     *   Check: fmap[0][2] == -1? NO (it's 9)
     *   Return: 9 immediately (O(1) instead of O(2^n))
     *
     * @param arr - card array
     * @param L - left boundary
     * @param R - right boundary
     * @param fmap - memoization table for first mover
     * @param gmap - memoization table for second mover
     * @return maximum score as first mover in [L,R]
     */
    // arr[L..R]，先手获得的最好分数返回
    public static int f2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        // STEP 1: Check if already computed (memoization lookup)
        if (fmap[L][R] != -1) {
            return fmap[L][R]; // Cache hit: return stored result
        }

        // STEP 2: Compute result (cache miss)
        int ans = 0; // Initialize answer

        if (L == R) {
            // Base case: only one card
            ans = arr[L]; // Take the only card
        } else {
            // Recursive case: choose best option
            int p1 = arr[L] + g2(arr, L + 1, R, fmap, gmap);
            // Option 1: take left card

            int p2 = arr[R] + g2(arr, L, R - 1, fmap, gmap);
            // Option 2: take right card

            ans = Math.max(p1, p2); // Choose maximum
        }

        // STEP 3: Store result in cache before returning
        fmap[L][R] = ans; // Memoize: save for future lookups

        return ans; // Return computed answer
    }

    /**
     * MEMOIZED SECOND MOVER FUNCTION
     *
     * SAME PATTERN: Check cache → Compute → Store → Return
     *
     * ============================================================================
     * CACHE EFFICIENCY DEMONSTRATION:
     * ============================================================================
     * For array of length n=4:
     *
     * Without memoization (win1):
     *   Total f1() calls: ~O(2^4) = 16+ calls (with duplicates)
     *   Total g1() calls: ~O(2^4) = 16+ calls (with duplicates)
     *   Many redundant computations
     *
     * With memoization (win2):
     *   Total f2() calls: O(n²) = 10 unique (only upper triangle + diagonal)
     *   Total g2() calls: O(n²) = 10 unique
     *   Each cell computed exactly once
     *   Subsequent lookups: O(1)
     *
     * Speedup factor: O(2^n) / O(n²) - exponential to polynomial!
     *
     * @param arr - card array
     * @param L - left boundary
     * @param R - right boundary
     * @param fmap - memoization table for first mover
     * @param gmap - memoization table for second mover
     * @return maximum score as second mover in [L,R]
     */
    // // arr[L..R]，后手获得的最好分数返回
    public static int g2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        // STEP 1: Memoization check
        if (gmap[L][R] != -1) {
            return gmap[L][R]; // Return cached result
        }

        // STEP 2: Compute if not cached
        int ans = 0; // Default for base case

        if (L != R) {
            // Recursive case: opponent chooses, you get minimum
            int p1 = f2(arr, L + 1, R, fmap, gmap);
            // 对手拿走了L位置的数
            // Opponent takes left card

            int p2 = f2(arr, L, R - 1, fmap, gmap);
            // 对手拿走了R位置的数
            // Opponent takes right card

            ans = Math.min(p1, p2); // Opponent minimizes your score
        }
        // Note: if L==R, ans remains 0 (second mover gets nothing with 1 card)

        // STEP 3: Store in cache
        gmap[L][R] = ans; // Save for future

        return ans; // Return result
    }

    /**
     * APPROACH 3: TABULATION (BOTTOM-UP DYNAMIC PROGRAMMING)
     *
     * STRATEGY:
     * - Build DP tables from smallest subproblems to largest
     * - Eliminate recursion overhead
     * - More space-efficient and predictable
     *
     * FILLING ORDER:
     * - Process by increasing range size (diagonal by diagonal)
     * - Each cell depends only on previously computed cells
     *
     * ============================================================================
     * DP TABLE CONSTRUCTION FOR [5, 7, 4, 5]:
     * ============================================================================
     * FMAP (First Mover Scores):
     *
     * Initial diagonal (L=R, single cards):
     *       0   1   2   3
     *    ┌───┬───┬───┬───┐
     * 0  │ 5 │   │   │   │  fmap[0][0] = arr[0] = 5
     *    ├───┼───┼───┼───┤
     * 1  │   │ 7 │   │   │  fmap[1][1] = arr[1] = 7
     *    ├───┼───┼───┼───┤
     * 2  │   │   │ 4 │   │  fmap[2][2] = arr[2] = 4
     *    ├───┼───┼───┼───┤
     * 3  │   │   │   │ 5 │  fmap[3][3] = arr[3] = 5
     *    └───┴───┴───┴───┘
     *
     * Fill diagonal +1 (ranges of size 2):
     *       0   1   2   3
     *    ┌───┬───┬───┬───┐
     * 0  │ 5 │ 7 │   │   │  fmap[0][1] = max(5+gmap[1][1], 7+gmap[0][0])
     *    ├───┼───┼───┼───┤             = max(5+0, 7+0) = 7
     * 1  │   │ 7 │ 7 │   │  fmap[1][2] = max(7+0, 4+0) = 7
     *    ├───┼───┼───┼───┤
     * 2  │   │   │ 4 │ 5 │  fmap[2][3] = max(4+0, 5+0) = 5
     *    ├───┼───┼───┼───┤
     * 3  │   │   │   │ 5 │
     *    └───┴───┴───┴───┘
     *
     * GMAP (Second Mover Scores) - builds simultaneously:
     *       0   1   2   3
     *    ┌───┬───┬───┬───┐
     * 0  │ 0 │ 0 │   │   │  gmap[0][1] = min(fmap[1][1], fmap[0][0])
     *    ├───┼───┼───┼───┤             = min(7, 5) = 5?
     * 1  │   │ 0 │ 0 │   │  Wait, gmap[L][R] = min(fmap[L+1][R], fmap[L][R-1])
     *    ├───┼───┼───┼───┤
     * 2  │   │   │ 0 │ 0 │
     *    ├───┼───┼───┼───┤
     * 3  │   │   │   │ 0 │
     *    └───┴───┴───┴───┘
     *
     * DEPENDENCY DIAGRAM:
     * To compute fmap[L][R], need:
     *   - gmap[L+1][R] (cell to the right in gmap)
     *   - gmap[L][R-1] (cell below in gmap)
     * To compute gmap[L][R], need:
     *   - fmap[L+1][R] (cell to the right in fmap)
     *   - fmap[L][R-1] (cell below in fmap)
     *
     * Visual dependencies for fmap[0][2]:
     *       0   1   2
     *    ┌───┬───┬───┐
     * 0  │ ? │   │ X │  X needs values from cells marked ↓
     *    ├───┼───┼───┤
     * 1  │   │ ↓ │ ↓ │  gmap[1][2] and gmap[0][1]
     *    ├───┼───┼───┤
     * 2  │   │   │   │
     *    └───┴───┴───┘
     *
     * ITERATION PATTERN:
     * startCol = 1: process ranges of size 2 (all cells with R-L=1)
     * startCol = 2: process ranges of size 3 (all cells with R-L=2)
     * startCol = 3: process ranges of size 4 (all cells with R-L=3)
     * ...
     *
     * TIME COMPLEXITY: O(n²) - fill n² cells, O(1) per cell
     * SPACE COMPLEXITY: O(n²) - two n×n arrays
     *
     * @param arr - card array
     * @return winner's maximum score
     */
    public static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;

        // Create DP tables (initialized to 0 by default)
        int[][] fmap = new int[N][N]; // First mover scores
        int[][] gmap = new int[N][N]; // Second mover scores

        // PHASE 1: Initialize base cases (diagonal: single cards)
        for (int i = 0; i < N; i++) {
            fmap[i][i] = arr[i]; // With 1 card, first mover takes it
            // gmap[i][i] = 0 (already 0, second mover gets nothing)
        }

        // PHASE 2: Fill table diagonal by diagonal (increasing range size)
        // startCol represents the column where each diagonal starts
        // Also represents the size of the range minus 1
        for (int startCol = 1; startCol < N; startCol++) {
            // startCol = 1: ranges of size 2 ([0,1], [1,2], [2,3], ...)
            // startCol = 2: ranges of size 3 ([0,2], [1,3], [2,4], ...)
            // startCol = 3: ranges of size 4 ([0,3], [1,4], ...)

            int L = 0;        // Left boundary starts at 0
            int R = startCol; // Right boundary starts at startCol

            // Move diagonally: increment both L and R together
            while (R < N) {
                // Process cell [L][R]

                // First mover: choose max of taking left or right card
                fmap[L][R] = Math.max(
                    arr[L] + gmap[L + 1][R],  // Take left card
                    arr[R] + gmap[L][R - 1]   // Take right card
                );
                // Dependencies: gmap[L+1][R] and gmap[L][R-1]
                // (both already computed in previous iterations)

                // Second mover: opponent minimizes your score
                gmap[L][R] = Math.min(
                    fmap[L + 1][R],  // Opponent takes left
                    fmap[L][R - 1]   // Opponent takes right
                );
                // Dependencies: fmap[L+1][R] and fmap[L][R-1]
                // (both already computed: fmap[L+1][R] from base/previous diagonal,
                //  fmap[L][R-1] from earlier in current diagonal)

                // Move to next cell in diagonal
                L++; // Shift window right
                R++; // Shift window right
            }
            // After while loop: processed entire diagonal for current range size
        }

        // PHASE 3: Answer is in cell [0][N-1] (entire array)
        return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
        // fmap[0][N-1]: best score as first mover for entire array
        // gmap[0][N-1]: best score as second mover for entire array
        // Winner is max of these two
    }

    /**
     * TEST DRIVER
     *
     * PURPOSE: Verify all three approaches produce same result
     *
     * ============================================================================
     * MANUAL TRACE FOR [5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7]:
     * ============================================================================
     * This is a complex game tree. Let's trace first few moves:
     *
     * Initial: [5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7]
     *
     * First player options:
     *   Option A: Take 5 (left)  → Remaining: [7,4,5,8,1,6,0,3,4,6,1,7]
     *   Option B: Take 7 (right) → Remaining: [5,7,4,5,8,1,6,0,3,4,6,1]
     *
     * Each option branches into opponent's choices, recursively...
     *
     * The optimal play involves:
     * - Looking ahead multiple moves
     * - Considering opponent's best responses
     * - Choosing moves that maximize your total score
     *
     * All three implementations compute the same optimal score through:
     * - win1(): Exploring entire game tree (slow but correct)
     * - win2(): Caching explored states (fast, top-down)
     * - win3(): Building solution from small to large (fast, bottom-up)
     *
     * EXPECTED OUTPUT:
     * All three methods should print the same winning score.
     *
     * PERFORMANCE COMPARISON (for n=13):
     * - win1(): ~8000+ recursive calls (many duplicates)
     * - win2(): ~91 unique subproblems (13²/2 + 13/2)
     * - win3(): exactly 91 cells computed (no recursion overhead)
     */
    public static void main(String[] args) {
        // Test array with 13 cards
        int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};

        // Run all three approaches
        System.out.println(win1(arr)); // Pure recursion (slowest)
        System.out.println(win2(arr)); // Memoization (fast)
        System.out.println(win3(arr)); // Tabulation (fastest)

        // All should output the same winning score
        // Demonstrates correctness of optimization approaches
    }

}
