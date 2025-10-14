package class13; // Declares the package name for organizing classes

import java.util.LinkedList; // Imports LinkedList for BFS queue implementation

// 测试链接 : https://leetcode.cn/problems/check-completeness-of-a-binary-tree/
// This class provides two approaches to check if a binary tree is a Complete Binary Tree (CBT)
// A complete binary tree is a tree where all levels are fully filled except possibly the last level,
// which is filled from left to right
public class Code01_IsCBT {

    /**
     * Method 1: BFS (Breadth-First Search) approach to check if tree is complete
     * Key Concept: Use level-order traversal and check two conditions:
     * 1. No node should have only a right child (missing left child)
     * 2. Once we encounter a node that's not full (missing child), all subsequent nodes must be leaves
     *
     * @param head - root node of the binary tree
     * @return true if the tree is a complete binary tree, false otherwise
     */
    public static boolean isCompleteTree1(TreeNode head) {
        // Base case: An empty tree is considered complete
        if (head == null) {
            return true;
        }
        // Queue for level-order traversal (BFS)
        LinkedList<TreeNode> queue = new LinkedList<>();
        // 是否遇到过左右两个孩子不双全的节点
        // Flag to track if we've encountered a node with incomplete children
        boolean leaf = false;
        // Temporary variables to hold left and right children
        TreeNode l = null;
        TreeNode r = null;
        // Start BFS by adding root to queue
        queue.add(head);
        // Process all nodes level by level
        while (!queue.isEmpty()) {
            // Dequeue the front node for processing
            head = queue.poll();
            // Get left and right children of current node
            l = head.left;
            r = head.right;
            // Check for violations of complete binary tree property
            if (
                // 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
                // Condition 1: If we previously saw an incomplete node, current node must be a leaf
                // (both children must be null)
                    (leaf && (l != null || r != null)) ||
                    // Condition 2: A node cannot have only right child (left must exist if right exists)
                    (l == null && r != null)

            ) {
                return false; // Tree is not complete
            }
            // Add left child to queue if it exists
            if (l != null) {
                queue.add(l);
            }
            // Add right child to queue if it exists
            if (r != null) {
                queue.add(r);
            }
            // If current node doesn't have both children, mark that we've seen an incomplete node
            // All subsequent nodes must be leaves
            if (l == null || r == null) {
                leaf = true;
            }
        }
        // If we processed all nodes without violations, tree is complete
        return true;
    }

    /**
     * Method 2: Recursive tree DP approach to check if tree is complete
     * Key Concept: Collect information from subtrees (isFull, isCBT, height) and
     * combine them to determine if current tree is a CBT
     *
     * @param head - root node of the binary tree
     * @return true if the tree is a complete binary tree, false otherwise
     */
    public static boolean isCompleteTree2(TreeNode head) {
        // Call recursive helper and extract isCBT property from returned Info object
        return process(head).isCBT;
    }

    /**
     * Recursive helper function that collects tree properties
     * Returns Info object containing: isFull, isCBT, and height
     *
     * Four cases where a tree rooted at X is a CBT:
     * 1. Both subtrees are full and have equal height
     * 2. Left subtree is CBT, right is full, left height = right height + 1
     * 3. Left subtree is full, right is full, left height = right height + 1
     * 4. Left subtree is full, right is CBT, left height = right height
     *
     * @param x - current node being processed
     * @return Info object with properties of subtree rooted at x
     */
    public static Info process(TreeNode x) {
        // Base case: null node is considered a full and complete tree with height 0
        if (x == null) {
            return new Info(true, true, 0);
        }
        // Recursively get information from left subtree
        Info leftInfo = process(x.left);
        // Recursively get information from right subtree
        Info rightInfo = process(x.right);
        // Calculate height of current tree: max of subtree heights + 1
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        // Tree is full if both subtrees are full and have equal height
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        // Initialize isCBT flag
        boolean isCBT = false;
        // Case 1: Both subtrees full with equal height -> current tree is CBT
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            isCBT = true;
        // Case 2: Left is CBT, right is full, left height is 1 more than right
        } else if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        // Case 3: Both subtrees full, left height is 1 more than right
        } else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        // Case 4: Left is full, right is CBT, both have equal height
        } else if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
            isCBT = true;
        }
        // Return Info object with all computed properties
        return new Info(isFull, isCBT, height);
    }

    // for test
    /**
     * Generates a random binary tree for testing purposes
     * @param maxLevel - maximum depth of the tree
     * @param maxValue - maximum value for node values
     * @return root of the generated random tree
     */
    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    /**
     * Recursive helper to generate random binary tree
     * Each node has 50% chance of being null
     *
     * @param level - current level in the tree
     * @param maxLevel - maximum allowed depth
     * @param maxValue - maximum value for node values
     * @return generated TreeNode or null
     */
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        // Stop generation if max level exceeded or randomly decided to stop
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        // Create node with random value
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        // Recursively generate left subtree
        head.left = generate(level + 1, maxLevel, maxValue);
        // Recursively generate right subtree
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    /**
     * Main method to test both implementations
     * Runs 1 million random test cases to verify both methods produce same results
     */
    public static void main(String[] args) {
        int maxLevel = 5; // Maximum tree depth for testing
        int maxValue = 100; // Maximum node value
        int testTimes = 1000000; // Number of test iterations
        // Run extensive randomized testing
        for (int i = 0; i < testTimes; i++) {
            // Generate a random binary tree
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            // Compare results from both methods
            if (isCompleteTree1(head) != isCompleteTree2(head)) {
                System.out.println("Oops!"); // Report if methods disagree
            }
        }
        System.out.println("finish!"); // All tests passed
    }

    // 不要提交这个类
    /**
     * TreeNode class representing a node in the binary tree
     * Contains value and pointers to left and right children
     */
    public static class TreeNode {
        public int val; // Node value
        public TreeNode left; // Pointer to left child
        public TreeNode right; // Pointer to right child

        /**
         * Constructor to create a node with given value
         * @param v - value to store in the node
         */
        public TreeNode(int v) {
            val = v;
        }
    }

    /**
     * Info class to store properties of a subtree
     * Used in the recursive tree DP approach
     */
    public static class Info {
        public boolean isFull; // Whether subtree is a full binary tree
        public boolean isCBT; // Whether subtree is a complete binary tree
        public int height; // Height of the subtree

        /**
         * Constructor to create Info object with all properties
         * @param full - is the subtree full
         * @param cbt - is the subtree complete
         * @param h - height of the subtree
         */
        public Info(boolean full, boolean cbt, int h) {
            isFull = full;
            isCBT = cbt;
            height = h;
        }
    }

}
