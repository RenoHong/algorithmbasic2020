package class14;

import java.util.HashSet;

//题目：给定一个字符串str，只由‘X’和‘.’两种字符构成。‘X’表示墙，不能放灯，
//也不需要点亮‘.’表示居民点，可以放灯，需要点亮如果灯放在i位置，
//可以让i-1，i和i+1三个位置被点亮返回如果点亮str中所有需要点亮的位置，至少需要几盏灯。

// Main class for solving the minimum number of lights problem
public class Code01_Light {

    // Brute-force recursive solution to find the minimum number of lights needed
    public static int minLight1(String road) {
        // If the input road is null or empty, no lights are needed
        if (road == null || road.length() == 0) {
            return 0;
        }
        // Start recursive process with an empty set of lights
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    // Recursive process to try all possible ways of placing lights
    // str[index....] position, freely choose to put a light or not
    // str[0..index-1] positions have already been decided, lights placed are in 'lights'
    // The goal is to illuminate all '.' positions with the minimum number of lights
    //    Here’s a compact recursion tree showing how `process(char[] str, int index, HashSet<Integer> lights)`
    //    explores choices on a small example road: `.X.`
    //
    //    Legend:
    //            - Node format: `(index, lights) -> return`
    //            - `+∞` means `Integer.MAX_VALUE` (invalid configuration).
    //    root: (0, {})
    //            ├─ no: (1, {})
    //            │  └─ (2, {})
    //            │     ├─ no:  (3, {}) -> +∞   // '.' at 0 and 2 not covered
    //            │     └─ yes: (3, {2}) -> +∞   // '.' at 0 not covered
    //            └─ yes: (1, {0})
    //                └─ (2, {0})
    //                  ├─ no:  (3, {0})   -> +∞ // '.' at 2 not covered
    //                  └─ yes: (3, {0,2}) -> 2  // both '.' covered
    //
    //    Propagate mins:
    //            - (2, {}) returns min(+∞, +∞) = +∞
    //            - (2, {0}) returns min(+∞, 2) = 2
    //            - root returns min(+∞, 2) = 2
    //
    //
    //    How it works:
    //            - At each `index`, if `str[index]` is `'.'`, branch into “no light” and “place light”.
    //            - If `str[index]` is `'X'`, only advance to `index + 1`.
    //            - At `index == str.length`, validate every `'.'` is covered by a light at `i-1`, `i`, or `i+1`.
    //            Valid leaves return `lights.size()`, invalid return `+∞`.
    //            - Each node returns `min(no, yes)`.
    public static int process(char[] str, int index, HashSet<Integer> lights) {
        // If we've reached the end of the road
        if (index == str.length) { // End condition
            // Check if all '.' positions are illuminated
            for (int i = 0; i < str.length; i++) {
                if (str[i] != 'X') { // If current position is '.'
                    // If no light covers position i
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE; // Not illuminated, invalid solution
                    }
                }
            }
            // Return the number of lights used in this solution
            return lights.size();
        } else { // If we haven't reached the end
            // Try not placing a light at current index
            int no = process(str, index + 1, lights);
            int yes = Integer.MAX_VALUE;
            // Try placing a light if current position is '.'
            if (str[index] == '.') {
                lights.add(index); // Place a light at index
                yes = process(str, index + 1, lights); // Recurse to next index
                lights.remove(index); // Backtrack: remove the light
            }
            // Return the minimum lights needed between placing and not placing a light
            return Math.min(no, yes);
        }
    }

    // Greedy solution to find the minimum number of lights needed
//            Here is why the greedy in `minLight2` is correct:
//
//        - Invariant: at each step, `i` points to the first not-yet-lit position; all indices < `i` are already lit with the minimum number of lights.
//        - If `str[i] == 'X'`, it needs no light and cannot host one, so skipping to `i+1` preserves the invariant.
//        - If `str[i] == '.'`, any valid solution must place a light at `i` or `i+1`:
//        - Placing at `i-1` is impossible (already decided) and placing at `i+2` leaves `i` dark.
//        - If `str[i+1] == 'X'` or `i+1` is out of bounds, the only feasible choice to light `i` is placing at `i`; after that, `i+1` is lit/blocked, so move to `i+2`.
//        - If `str[i+1] == '.'`, placing at `i+1` weakly dominates placing at `i`: it still lights `i` and `i+1`, and can also light `i+2`. By an exchange argument, any optimal solution that lights at `i` can be shifted to light at `i+1` without increasing the count. Hence the greedy “place at `i+1`” (implemented by jumping `i += 3`) is optimal.
//
//   Equivalently, on every maximal run of dots of length `k`, the minimum lights is `ceil(k / 3)`. The algorithm simulates this by consuming 1, 2, or 3 dots per light:
//        - `'.X'` consumes 1 dot (jump `+2`);
//        - `'..'` (and possibly a third dot) consumes up to 3 dots (jump `+3`).
//
//   Thus each step is either forced or dominance-based, maintaining optimality and yielding the minimum count in linear time.
    public static int minLight2(String road) {
        char[] str = road.toCharArray(); // Convert road to char array
        int i = 0; // Current position
        int light = 0; // Number of lights used
        while (i < str.length) { // Iterate through the road
            if (str[i] == 'X') { // If current position is a wall
                i++; // Move to next position
            } else {
                light++; // Place a light
                if (i + 1 == str.length) { // If next position is out of bounds
                    break; // Done
                } else { // If next position exists
                    // If next position is a wall, skip two positions
                    // Notes: We have already added a light at i, so i+1 is covered anyway.
                    // If next position is also a wall, + 2 means skip 1 positions
                    if (str[i + 1] == 'X') {
                        i = i + 2;
                    } else {
                        // Otherwise, skip two positions
                        i = i + 3;
                    }
                }
            }
        }
        return light; // Return total lights used
    }

    // More concise greedy solution
    // Count the number of '.' between two 'X', divide by 3 and round up
    // Accumulate the number of lights
    public static int minLight3(String road) {
        char[] str = road.toCharArray(); // Convert road to char array
        int cur = 0; // Current count of consecutive '.'
        int light = 0; // Number of lights used
        for (char c : str) { // Iterate through each character
            if (c == 'X') { // If current character is a wall
                light += (cur + 2) / 3; // Add lights needed for previous dots
                cur = 0; // Reset dot counter
            } else {
                cur++; // Increment dot counter
            }
        }
        light += (cur + 2) / 3; // Add lights for last segment of dots
        return light; // Return total lights used
    }

    // Generate a random road string for testing
    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1]; // Random length up to len
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.'; // Randomly assign 'X' or '.'
        }
        return String.valueOf(res); // Convert char array to string
    }

    // Main method for testing the three solutions
    public static void main(String[] args) {
        int len = 20; // Maximum length of road
        int testTime = 100000; // Number of test cases
        for (int i = 0; i < testTime; i++) {
            String test = ".X." ;//randomString(len); // Generate random road
            int ans1 = minLight1(test); // Brute-force answer
            int ans2 = minLight2(test); // Greedy answer
            int ans3 = minLight3(test); // Concise greedy answer
            // If any answers differ, print "oops!"
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!"); // All tests passed
    }
}
