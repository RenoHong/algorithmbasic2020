package class11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Binary Tree Maximum Width Calculator
 * This class provides two different approaches to find the maximum width of a binary tree
 * Width is defined as the maximum number of nodes at any level of the tree
 */
public class Code05_TreeMaxWidth {

    /**
     * Method 1: Calculate maximum width using HashMap to track node levels
     * Time Complexity: O(n) where n is number of nodes
     * Space Complexity: O(n) for queue and hashmap
     *
     * @param head root node of the binary tree
     * @return maximum width (number of nodes) at any level
     */
    public static int maxWidthUseMap(Node head) {
        // Base case: empty tree has width 0
        if (head == null) {
            return 0;
        }

        // Initialize queue for level-order traversal (BFS)
        Queue<Node> queue = new LinkedList<>();
        queue.add(head); // Add root node to start traversal

        // HashMap to track which level each node belongs to
        // Key: Node reference, Value: Level number (starting from 1)
        HashMap<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1); // Root is at level 1

        int curLevel = 1;       // Current level we're counting nodes for
        int curLevelNodes = 0;  // Number of nodes found in current level so far
        int max = 0;           // Maximum width found so far

        // Continue until all nodes are processed
        while (!queue.isEmpty()) {
            // Remove and process next node from queue
            Node cur = queue.poll();
            // Get the level of current node from our mapping
            int curNodeLevel = levelMap.get(cur);

            // Add left child to queue if it exists
            if (cur.left != null) {
                // Left child is one level deeper than parent
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);
            }

            // Add right child to queue if it exists
            if (cur.right != null) {
                // Right child is one level deeper than parent
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);
            }

            // Check if current node belongs to the level we're counting
            if (curNodeLevel == curLevel) {
                // Increment count for current level
                curLevelNodes++;
            } else {
                // We've moved to next level, so finalize previous level
                // Update maximum width if current level was wider
                max = Math.max(max, curLevelNodes);
                // Move to next level and reset counter
                curLevel++;
                curLevelNodes = 1; // Current node is first node of new level
            }
        }

        // Don't forget to check the last level processed
        max = Math.max(max, curLevelNodes);
        return max;
    }

    /**
     * Method 2: Calculate maximum width without HashMap (more space efficient)
     * Uses two pointers to track current and next level boundaries
     * Time Complexity: O(n) where n is number of nodes
     * Space Complexity: O(w) where w is maximum width (for queue only)
     *
     * @param head root node of the binary tree
     * @return maximum width (number of nodes) at any level
     */
    public static int maxWidthNoMap(Node head) {
        // Base case: empty tree has width 0
        if (head == null) {
            return 0;
        }

        // Initialize queue for level-order traversal
        Queue<Node> queue = new LinkedList<>();
        queue.add(head); // Add root to start

        Node curEnd = head;    // Rightmost node of current level
        Node nextEnd = null;   // Will track rightmost node of next level
        int max = 0;          // Maximum width found so far
        int curLevelNodes = 0; // Count of nodes in current level

        // Process all nodes level by level
        while (!queue.isEmpty()) {
            // Get next node to process
            Node cur = queue.poll();

            // Add left child if exists
            if (cur.left != null) {
                queue.add(cur.left);
                // Update next level's rightmost node
                nextEnd = cur.left;
            }

            // Add right child if exists
            if (cur.right != null) {
                queue.add(cur.right);
                // Update next level's rightmost node (overwrites left if both exist)
                nextEnd = cur.right;
            }

            // Count current node in current level
            curLevelNodes++;

            // Check if we've reached end of current level
            if (cur == curEnd) {
                // Update maximum width if current level is wider
                max = Math.max(max, curLevelNodes);
                // Reset for next level
                curLevelNodes = 0;
                // Move boundary to next level's end
                curEnd = nextEnd;
            }
        }
        return max;
    }

    /**
     * Test utility: Generate a random binary search tree for testing
     *
     * @param maxLevel maximum depth of the tree
     * @param maxValue maximum value for node data
     * @return root of generated random tree
     */
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        // Start generation from level 1
        return generate(1, maxLevel, maxValue);
    }

    /**
     * Recursive helper method to generate random binary tree
     *
     * @param level current level being generated
     * @param maxLevel maximum allowed depth
     * @param maxValue maximum value for node data
     * @return generated subtree root or null
     */
    public static Node generate(int level, int maxLevel, int maxValue) {
        // Stop generation if max level reached or random termination
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }

        // Create new node with random value
        Node head = new Node((int) (Math.random() * maxValue));
        // Recursively generate left and right subtrees
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    /**
     * Main method for testing both algorithms
     * Generates random trees and compares results from both methods
     */
    public static void main(String[] args) {
        int maxLevel = 10;      // Maximum tree depth for testing
        int maxValue = 100;     // Maximum node value for testing
        int testTimes = 1000000; // Number of test cases to run

        // Run extensive testing
        for (int i = 0; i < testTimes; i++) {
            // Generate random test tree
            Node head = generateRandomBST(maxLevel, maxValue);
            // Compare results from both methods
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                // If results differ, there's a bug
                System.out.println("Oops!");
            }
        }
        // All tests passed
        System.out.println("finish!");
    }

    /**
     * Binary tree node class
     * Simple structure containing value and references to children
     */
    public static class Node {
        public int value;  // Data stored in this node
        public Node left;  // Reference to left child (can be null)
        public Node right; // Reference to right child (can be null)

        /**
         * Constructor to create new node with given value
         *
         * @param data the integer value to store in this node
         */
        public Node(int data) {
            this.value = data;
            // left and right are automatically initialized to null
        }
    }
}
