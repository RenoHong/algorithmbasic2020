package class13; // Package declaration

import java.util.ArrayList; // For storing list of subordinates
import java.util.List; // List interface

// This class solves the "Maximum Happy" problem (also known as "House Robber III" or "Party Invitation")
// Problem: Given a company hierarchy tree, each employee has a happiness value
// Rule: An employee and their direct boss cannot both attend the party
// Goal: Maximize total happiness at the party
public class Code04_MaxHappy {

    /**
     * Method 1: Recursive approach with boolean flag
     * Key Concept: For each employee, decide whether they attend based on boss's attendance
     * Time Complexity: O(N) where N is number of employees
     *
     * @param boss - root of the employee hierarchy tree
     * @return maximum happiness achievable
     */
    public static int maxHappy1(Employee boss) {
        // Base case: no employees means 0 happiness
        if (boss == null) {
            return 0;
        }
        // Start recursion from boss, assuming boss's boss doesn't come (false)
        return process1(boss, false);
    }

    // 当前来到的节点叫cur，
    // up表示cur的上级是否来，
    // 该函数含义：
    // 如果up为true，表示在cur上级已经确定来，的情况下，cur整棵树能够提供最大的快乐值是多少？
    // 如果up为false，表示在cur上级已经确定不来，的情况下，cur整棵树能够提供最大的快乐值是多少？
    /**
     * Recursive helper that computes max happiness for subtree
     *
     * @param cur - current employee being considered
     * @param up - true if cur's boss is attending, false otherwise
     * @return maximum happiness from cur's subtree given boss's attendance status
     */
    public static int process1(Employee cur, boolean up) {
        if (up) { // 如果cur的上级来的话，cur没得选，只能不来
            // If boss is attending, current employee CANNOT attend
            // Sum up happiness from all subordinates (who now have choice)
            int ans = 0;
            for (Employee next : cur.nexts) {
                // Recurse on each subordinate with up=false (cur not attending)
                ans += process1(next, false);
            }
            return ans;
        } else { // 如果cur的上级不来的话，cur可以选，可以来也可以不来
            // If boss is NOT attending, current employee has a choice
            // Option 1 (p1): Current employee ATTENDS
            int p1 = cur.happy; // Start with current employee's happiness
            // Option 2 (p2): Current employee does NOT attend
            int p2 = 0;
            // For each subordinate, calculate both scenarios
            for (Employee next : cur.nexts) {
                // If current attends, subordinates cannot (up=true for subordinates)
                p1 += process1(next, true);
                // If current doesn't attend, subordinates have choice (up=false)
                p2 += process1(next, false);
            }
            // Return the maximum of both options
            return Math.max(p1, p2);
        }
    }

    /**
     * Method 2: Optimized tree DP approach
     * Key Concept: For each node, compute two values:
     * - no: max happiness if this employee does NOT attend
     * - yes: max happiness if this employee DOES attend
     * Time Complexity: O(N) - visit each node once
     *
     * @param head - root of the employee hierarchy tree
     * @return maximum happiness achievable
     */
    public static int maxHappy2(Employee head) {
        // Get info from entire tree
        Info allInfo = process(head);
        // Return max of two scenarios: head attends or doesn't attend
        return Math.max(allInfo.no, allInfo.yes);
    }

    /**
     * Recursive helper that computes Info for subtree rooted at x
     * Info contains:
     * - no: max happiness if x does NOT attend
     * - yes: max happiness if x DOES attend
     *
     * Logic:
     * - If x doesn't attend: subordinates can choose, take max for each
     * - If x attends: subordinates cannot attend, must take their "no" values
     *
     * @param x - current employee being processed
     * @return Info object with both scenarios
     */
    public static Info process(Employee x) {
        // Base case: null employee contributes 0 happiness in both scenarios
        if (x == null) {
            return new Info(0, 0);
        }
        // Initialize: if x doesn't attend, happiness starts at 0
        int no = 0;
        // If x attends, start with x's happiness value
        int yes = x.happy;
        // Process all subordinates
        for (Employee next : x.nexts) {
            // Recursively get info from subordinate
            Info nextInfo = process(next);
            // If x doesn't attend, subordinates can choose: take max of their options
            no += Math.max(nextInfo.no, nextInfo.yes);
            // If x attends, subordinates cannot attend: must take their "no" value
            yes += nextInfo.no;

        }
        // Return combined info for this subtree
        return new Info(no, yes);
    }

    // for test
    /**
     * Generates a random employee hierarchy tree for testing
     * Has 2% chance of returning null
     *
     * @param maxLevel - maximum depth of hierarchy
     * @param maxNexts - maximum number of subordinates per employee
     * @param maxHappy - maximum happiness value
     * @return root employee (boss) or null
     */
    public static Employee genarateBoss(int maxLevel, int maxNexts, int maxHappy) {
        // 2% chance to generate null tree
        if (Math.random() < 0.02) {
            return null;
        }
        // Create boss with random happiness value
        Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
        // Recursively generate subordinates
        genarateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    // for test
    /**
     * Recursively generates subordinates for an employee
     * Creates a random organization hierarchy
     *
     * @param e - current employee to add subordinates to
     * @param level - current depth in hierarchy
     * @param maxLevel - maximum allowed depth
     * @param maxNexts - maximum subordinates per employee
     * @param maxHappy - maximum happiness value
     */
    public static void genarateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
        // Stop if max level reached
        if (level > maxLevel) {
            return;
        }
        // Randomly determine number of subordinates (0 to maxNexts)
        int nextsSize = (int) (Math.random() * (maxNexts + 1));
        // Create each subordinate
        for (int i = 0; i < nextsSize; i++) {
            // Create subordinate with random happiness
            Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
            // Add to current employee's subordinate list
            e.nexts.add(next);
            // Recursively generate subordinates for this employee
            genarateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }

    /**
     * Main method to test both implementations
     * Runs 100,000 random tests comparing both methods
     */
    public static void main(String[] args) {
        int maxLevel = 4; // Maximum hierarchy depth
        int maxNexts = 7; // Maximum subordinates per employee
        int maxHappy = 100; // Maximum happiness value
        int testTimes = 100000; // Number of test cases
        // Run extensive testing
        for (int i = 0; i < testTimes; i++) {
            // Generate random employee hierarchy
            Employee boss = genarateBoss(maxLevel, maxNexts, maxHappy);
            // Compare results from both methods
            if (maxHappy1(boss) != maxHappy2(boss)) {
                System.out.println("Oops!"); // Report mismatch
            }
        }
        System.out.println("finish!"); // All tests passed
    }

    /**
     * Employee class representing a node in the organization hierarchy
     * Each employee has a happiness value and a list of direct subordinates
     */
    public static class Employee {
        public int happy; // Happiness value this employee brings to party
        public List<Employee> nexts; // List of direct subordinates

        /**
         * Constructor to create employee with given happiness
         * @param h - happiness value
         */
        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>(); // Initialize empty subordinate list
        }

    }

    /**
     * Info class to store two scenarios for tree DP approach
     * Used to pass information up the recursion tree
     */
    public static class Info {
        public int no; // Max happiness if this employee does NOT attend
        public int yes; // Max happiness if this employee DOES attend

        /**
         * Constructor to create Info object
         * @param n - happiness when not attending
         * @param y - happiness when attending
         */
        public Info(int n, int y) {
            no = n;
            yes = y;
        }
    }

}
