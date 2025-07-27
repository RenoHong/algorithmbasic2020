// Package declaration - organizes code into class11 module
package class11;

// Import necessary data structures for the algorithms
import java.util.HashMap;      // For Method 1: tracks node-to-level mapping
import java.util.LinkedList;   // Provides Queue implementation for BFS
import java.util.Queue;        // Interface for level-order traversal

/**
 * ALGORITHM EXPLANATION:
 *
 * Problem: Find maximum width of binary tree
 * Width = maximum number of nodes at any single level
 *
 * Approach: Level-order traversal (BFS) to count nodes per level
 *
 * Two Methods Provided:
 * 1. HashMap-based: Uses extra space to track node levels explicitly
 * 2. Boundary-tracking: More space-efficient, tracks level boundaries
 */
public class Code05_TreeMaxWidth {

    /**
     * METHOD 1 DETAILED EXPLANATION:
     *
     * Strategy: Use HashMap to explicitly track which level each node belongs to
     *
     * Why this works:
     * - BFS naturally processes nodes level by level
     * - HashMap allows us to know exactly which level any node belongs to
     * - We can count nodes per level and track maximum
     *
     * Trade-offs:
     * + Easy to understand and implement
     * + Clear separation of concerns
     * - Extra O(n) space for HashMap
     * - HashMap operations have slight overhead
     */
    public static int maxWidthUseMap(Node head) {
        if (head == null) {           // Edge case: empty tree
            return 0;                 // Width is 0 for empty tree
        }

        // Core data structures for BFS traversal
        Queue<Node> queue = new LinkedList<>();  // FIFO queue for BFS
        queue.add(head);                         // Start with root node

        // Key insight: HashMap maps each node to its level number
        HashMap<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);                   // Root is at level 1 (not 0)

        // State variables for tracking current level
        int curLevel = 1;                        // Level we're currently counting
        int curLevelNodes = 0;                   // Nodes found in current level
        int max = 0;                            // Global maximum width found

        // Main BFS loop - continues until all nodes processed
        while (!queue.isEmpty()) {
            Node cur = queue.poll();             // Get next node (FIFO order)
            int curNodeLevel = levelMap.get(cur); // Look up this node's level

            // Process left child if it exists
            if (cur.left != null) {
                // Key insight: child level = parent level + 1
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);             // Add to queue for future processing
            }

            // Process right child if it exists
            if (cur.right != null) {
                // Key insight: child level = parent level + 1
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);            // Add to queue for future processing
            }

            // Check if current node belongs to level we're counting
            if (curNodeLevel == curLevel) {
                curLevelNodes++;                 // Count this node in current level
            } else {
                // We've moved to next level - finalize previous level count
                max = Math.max(max, curLevelNodes);  // Update global maximum
                curLevel++;                      // Move to next level
                curLevelNodes = 1;              // Current node starts new level count
            }
        }

        // Critical: Don't forget last level (loop ends before final comparison)
        max = Math.max(max, curLevelNodes);
        return max;
    }

    /**
     * METHOD 2 DETAILED EXPLANATION:
     *
     * Strategy: Track level boundaries without HashMap
     *
     * Key insight: In BFS, we can identify level boundaries by tracking:
     * - curEnd: rightmost node of current level
     * - nextEnd: rightmost node being discovered for next level
     *
     * Why this works:
     * - When we finish processing curEnd, we've finished current level
     * - nextEnd gets updated as we discover nodes for next level
     * - No need to store level info for every node
     *
     * Trade-offs:
     * + More space efficient (no HashMap)
     * + Slightly faster (no HashMap lookups)
     * - Requires careful boundary tracking logic
     * - Less intuitive than Method 1
     */
    public static int maxWidthNoMap(Node head) {
        if (head == null) {                     // Edge case: empty tree
            return 0;                           // Width is 0
        }

        Queue<Node> queue = new LinkedList<>();  // BFS queue
        queue.add(head);                        // Start with root

        // Boundary tracking variables - core innovation of this method
        Node curEnd = head;                     // Last node of current level
        Node nextEnd = null;                    // Last node discovered for next level
        int max = 0;                           // Global maximum width
        int curLevelNodes = 0;                 // Current level node count

        while (!queue.isEmpty()) {
            Node cur = queue.poll();            // Get next node to process

            // Add children and update next level boundary
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;             // Update next level's rightmost node
            }

            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;            // Right child becomes new rightmost
            }

            curLevelNodes++;                    // Count current node

            // Check if we've reached end of current level
            if (cur == curEnd) {                // Current node is last of its level
                max = Math.max(max, curLevelNodes);  // Update maximum
                curLevelNodes = 0;              // Reset counter for next level
                curEnd = nextEnd;               // Move boundary to next level
            }
        }
        return max;
    }

    /**
     * TEST GENERATION METHODS:
     *
     * Purpose: Create random binary trees for algorithm testing
     * Strategy: Recursive generation with random termination
     */
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);  // Start from level 1
    }

    /**
     * Recursive tree generator
     *
     * Termination conditions:
     * 1. Reached maximum depth (maxLevel)
     * 2. Random termination (Math.random() < 0.5 gives 50% chance)
     *
     * This creates varied tree shapes for comprehensive testing
     */
    public static Node generate(int level, int maxLevel, int maxValue) {
        // Two termination conditions for realistic tree variety
        if (level > maxLevel || Math.random() < 0.5) {
            return null;                        // Terminate this branch
        }

        Node head = new Node((int) (Math.random() * maxValue));  // Random node value
        head.left = generate(level + 1, maxLevel, maxValue);     // Recursive left
        head.right = generate(level + 1, maxLevel, maxValue);    // Recursive right
        return head;
    }

    /**
     * TESTING FRAMEWORK:
     *
     * Strategy: Comparative testing
     * - Generate many random trees
     * - Compare results from both methods
     * - Any discrepancy indicates a bug
     */
    public static void main(String[] args) {
        int maxLevel = 10;                      // Tree depth limit
        int maxValue = 100;                     // Node value range
        int testTimes = 1000000;               // Extensive testing

        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);  // Generate test case

            // Compare both methods - they must always agree
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");    // Bug detected!
            }
        }
        System.out.println("finish!");         // All tests passed
    }

    /**
     * BINARY TREE NODE:
     *
     * Simple structure representing tree nodes
     * Contains value and references to children
     */
    public static class Node {
        public int value;                       // Node's data
        public Node left;                       // Left child reference
        public Node right;                      // Right child reference

        public Node(int data) {
            this.value = data;                  // Set node value
            // left and right automatically null (Java default)
        }
    }
}
