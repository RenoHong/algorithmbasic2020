package class13; // Package declaration for organizing classes

import java.util.ArrayList; // Import ArrayList for storing in-order traversal results

// Main class to find the head of the largest BST subtree in a binary tree.
// Problem: Given a binary tree, find the node that is the root of the largest BST subtree
// A BST (Binary Search Tree) has the property: left < node < right for all nodes
public class Code02_MaxSubBSTHead {

    // Returns the size of the BST if the subtree rooted at 'head' is a BST, otherwise 0.
    /**
     * Helper method to check if a subtree is a BST and return its size
     * Key Concept: A BST's in-order traversal is strictly increasing
     *
     * @param head - root of the subtree to check
     * @return size of BST if valid, 0 if not a BST
     */
    public static int getBSTSize(Node head) {
        if (head == null) { // If the node is null, size is 0.
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>(); // List to store in-order traversal.
        in(head, arr); // Fill arr with in-order traversal of the tree.
        // Check if in-order traversal is strictly increasing
        for (int i = 1; i < arr.size(); i++) { // Check if in-order is strictly increasing.
            // If current value <= previous value, not a valid BST
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0; // Not a BST.
            }
        }
        return arr.size(); // Return size if BST.
    }

    // In-order traversal to fill the list with nodes.
    /**
     * Performs in-order traversal (left -> node -> right) to collect all nodes
     * Used to verify BST property by checking if traversal is sorted
     *
     * @param head - current node in traversal
     * @param arr - list to collect nodes in order
     */
    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) { // Base case: null node.
            return;
        }
        in(head.left, arr);  // Traverse left subtree.
        arr.add(head);       // Add current node.
        in(head.right, arr); // Traverse right subtree.
    }

    // Brute-force method: returns the head of the largest BST subtree.
    /**
     * Method 1: Brute force approach - checks every subtree
     * Time Complexity: O(N^2) where N is number of nodes
     * For each node, validates if it's a BST (O(N) per node)
     *
     * @param head - root of the binary tree
     * @return head node of the largest BST subtree
     */
    public static Node maxSubBSTHead1(Node head) {
        if (head == null) { // If tree is empty, return null.
            return null;
        }
        // Check if current subtree rooted at head is a BST
        if (getBSTSize(head) != 0) { // If current subtree is BST, return head.
            return head; // This is the largest BST including this node
        }
        Node leftAns = maxSubBSTHead1(head.left);   // Recurse on left subtree.
        Node rightAns = maxSubBSTHead1(head.right); // Recurse on right subtree.
        // Return the subtree with the larger BST size.
        // Compare sizes of largest BST in left vs right subtree
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }

    // Optimized method: returns the head of the largest BST subtree using post-order traversal.
    /**
     * Method 2: Optimized tree DP approach using post-order traversal
     * Time Complexity: O(N) - each node visited once
     * Key Concept: Collect info from children to determine BST properties
     *
     * @param head - root of the binary tree
     * @return head node of the largest BST subtree
     */
    public static Node maxSubBSTHead2(Node head) {
        if (head == null) { // If tree is empty, return null.
            return null;
        }
        return process(head).maxSubBSTHead; // Use Info object to get the answer.
    }

    // Recursively processes each node to gather information about BST subtrees.
    /**
     * Core recursive function for tree DP solution
     * Collects 4 pieces of info from each subtree:
     * 1. maxSubBSTHead - root of largest BST in subtree
     * 2. maxSubBSTSize - size of largest BST in subtree
     * 3. min - minimum value in subtree
     * 4. max - maximum value in subtree
     *
     * Logic: A tree rooted at X is a BST if:
     * - Left subtree is a BST ending at X.left AND left.max < X.value
     * - Right subtree is a BST starting at X.right AND right.min > X.value
     *
     * @param X - current node being processed
     * @return Info object containing all necessary information
     */
    public static Info process(Node X) {
        if (X == null) { // Base case: null node.
            return null; // Return null Info for null nodes
        }
        Info leftInfo = process(X.left);   // Recursively process left subtree.
        Info rightInfo = process(X.right); // Recursively process right subtree.
        // Initialize min and max with current node's value
        int min = X.value; // Initialize min as current node's value.
        int max = X.value; // Initialize max as current node's value.
        Node maxSubBSTHead = null; // Head of the largest BST found so far.
        int maxSubBSTSize = 0;     // Size of the largest BST found so far.
        // Update min, max from left subtree info
        if (leftInfo != null) { // Update min, max, and largest BST info from left subtree.
            min = Math.min(min, leftInfo.min); // Update min with left subtree's min
            max = Math.max(max, leftInfo.max); // Update max with left subtree's max
            maxSubBSTHead = leftInfo.maxSubBSTHead; // Initially consider left's largest BST
            maxSubBSTSize = leftInfo.maxSubBSTSize; // Initially consider left's BST size
        }
        // Update min, max from right subtree info and compare BST sizes
        if (rightInfo != null) { // Update min, max, and largest BST info from right subtree.
            min = Math.min(min, rightInfo.min); // Update min with right subtree's min
            max = Math.max(max, rightInfo.max); // Update max with right subtree's max
            // If right subtree has a larger BST, use it instead
            if (rightInfo.maxSubBSTSize > maxSubBSTSize) {
                maxSubBSTHead = rightInfo.maxSubBSTHead;
                maxSubBSTSize = rightInfo.maxSubBSTSize;
            }
        }
        // Check if current node with its left and right subtrees forms a BST.
        // Condition 1: left subtree is either null OR (is a BST rooted at X.left AND max < X.value)
        // Condition 2: right subtree is either null OR (is a BST rooted at X.right AND min > X.value)
        if ((leftInfo == null ? true : (leftInfo.maxSubBSTHead == X.left && leftInfo.max < X.value))
                && (rightInfo == null ? true : (rightInfo.maxSubBSTHead == X.right && rightInfo.min > X.value))) {
            // If so, update head and size to current node and its subtree.
            // Current node X becomes the root of a larger BST
            maxSubBSTHead = X;
            // Calculate total size: left BST size + right BST size + 1 (current node)
            maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize) + 1;
        }
        // Return Info object with all relevant information for parent calls.
        return new Info(maxSubBSTHead, maxSubBSTSize, min, max);
    }

    // Generates a random BST for testing.
    /**
     * Entry point to generate random binary tree for testing
     * @param maxLevel - maximum depth of tree
     * @param maxValue - maximum value for nodes
     * @return root of generated tree
     */
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // Helper to recursively generate a random BST.
    /**
     * Recursive helper to build random binary tree
     * Note: Despite the name, this generates a random binary tree, not necessarily a BST
     *
     * @param level - current depth in tree
     * @param maxLevel - maximum allowed depth
     * @param maxValue - maximum node value
     * @return generated node or null
     */
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) { // Randomly decide to stop.
            return null; // 50% chance to create null node
        }
        Node head = new Node((int) (Math.random() * maxValue)); // Random value.
        head.left = generate(level + 1, maxLevel, maxValue);  // Generate left child.
        head.right = generate(level + 1, maxLevel, maxValue); // Generate right child.
        return head;
    }

    // Main method for testing the solution.
    /**
     * Test driver that compares brute force and optimized solutions
     * Runs 1 million random tests to verify correctness
     */
    public static void main(String[] args) {
        int maxLevel = 4;      // Maximum depth of generated trees.
        int maxValue = 100;    // Maximum node value.
        int testTimes = 1000000; // Number of tests.
        // Run extensive testing with random trees
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue); // Generate random tree.
            if (maxSubBSTHead1(head) != maxSubBSTHead2(head)) { // Compare brute-force and optimized results.
                System.out.println("Oops!"); // Print error if results differ.
            }
        }
        System.out.println("finish!"); // Print end message.
    }

    // Definition for a binary tree node.
    /**
     * Node class representing a node in the binary tree
     */
    public static class Node {
        public int value;   // Node value.
        public Node left;   // Left child.
        public Node right;  // Right child.

        /**
         * Constructor to create a node with given value
         * @param data - value to store in node
         */
        public Node(int data) {
            this.value = data;
        }
    }

    // Info class holds information about subtree for the main algorithm.
    /**
     * Info class to encapsulate all information needed from a subtree
     * Used in the tree DP approach to pass information up the recursion
     */
    public static class Info {
        public Node maxSubBSTHead; // Head of the largest BST in this subtree.
        public int maxSubBSTSize;  // Size of the largest BST in this subtree.
        public int min;            // Minimum value in this subtree.
        public int max;            // Maximum value in this subtree.

        /**
         * Constructor to create Info object with all properties
         * @param h - head of largest BST
         * @param size - size of largest BST
         * @param mi - minimum value in subtree
         * @param ma - maximum value in subtree
         */
        public Info(Node h, int size, int mi, int ma) {
            maxSubBSTHead = h;
            maxSubBSTSize = size;
            min = mi;
            max = ma;
        }
    }

}
