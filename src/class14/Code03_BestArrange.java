package class14;

import javax.persistence.criteria.From;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This class provides two methods to solve the meeting arrangement problem:
 * 1. Brute-force recursion to try all possibilities.
 * 2. Greedy algorithm based on earliest end time.
 */
public class Code03_BestArrange {

    /**
     * Brute-force! Try all possible arrangements!
     * @param programs Array of meetings (Program objects)
     * @return The maximum number of meetings that can be arranged
     */
    public static int bestArrange1(Program[] programs) {
        // If input is null or empty, return 0
        if (programs == null || programs.length == 0) {
            return 0;
        }
        // Start recursion with 0 meetings done and timeline at 0
        return process(programs, 0, 0);
    }


    /**
     * Recursive process to try all possible meeting arrangements.
     * @param programs Remaining meetings to arrange
     * @param done Number of meetings already arranged
     * @param timeLine Current time point
     * @return The maximum number of meetings that can be arranged from this state
     */
    public static int process(Program[] programs, int done, int timeLine) {
        // If no meetings left, return the number done so far
        if (programs.length == 0) {
            return done;
        }
        // There are still meetings left
        int max = done; // Track the maximum number of meetings
        // Try to arrange each meeting as the next one
        for (int i = 0; i < programs.length; i++) {
            // If the meeting can be scheduled after the current timeline
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i); // Remove the scheduled meeting
                // This line is necessary. Although done+1 is usually greater than done,
                // max will also be compared with the result of recursion, which may be even greater.
                // So we must use Math.max to compare.
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max; // Return the maximum found
    }

    /**
     * Returns a new array with the element at index i removed.
     * @param programs Original array of meetings
     * @param i Index to remove
     * @return New array without the i-th meeting
     */
    public static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1]; // New array with one less element
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) { // Copy all except the i-th
                ans[index++] = programs[k];
            }
        }
        return ans;
    }

    /**
     * Greedy algorithm: always choose the meeting that ends earliest.
     * @param programs Array of meetings (Program objects)
     * @return The maximum number of meetings that can be arranged
     */
    public static int bestArrange2(Program[] programs) {
        // Sort meetings by end time (earliest first)
        Arrays.sort(programs, new ProgramComparator());
        int timeLine = 0; // Current time
        int result = 0;   // Number of meetings arranged
        // Traverse each meeting in order of end time
        for (int i = 0; i < programs.length; i++) {
            // If the meeting can be scheduled after the current timeline
            if (timeLine <= programs[i].start) {
                result++; // Arrange this meeting
                timeLine = programs[i].end; // Move timeline to the end of this meeting
            }
        }
        return result; // Return the total number arranged
    }

    /**
     * Generates a random array of meetings for testing.
     * @param programSize Maximum number of meetings
     * @param timeMax Maximum possible time value
     * @return Array of randomly generated meetings
     */
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1); // Ensure end > start
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    /**
     * Main method for testing both algorithms.
     * Generates random test cases and compares the results of both methods.
     */
    public static void main(String[] args) {
        int programSize = 12; // Maximum number of meetings
        int timeMax = 20;     // Maximum time value
        int timeTimes = 1000000; // Number of test cases
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            // Compare brute-force and greedy results
            if (bestArrange1(programs) != bestArrange2(programs)) {
                System.out.println("Oops!"); // Print if results differ
            }
        }
        System.out.println("finish!"); // All tests done
    }

    /**
     * Class representing a meeting with a start and end time.
     */
    public static class Program {
        public int start; // Meeting start time
        public int end;   // Meeting end time

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Comparator for sorting meetings by end time (earliest first).
     */
    public static class ProgramComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end; // Compare by end time
        }

    }

}
