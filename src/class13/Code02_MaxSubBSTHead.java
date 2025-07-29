package class13;

import java.util.ArrayList;

// Main class to find the head of the largest BST subtree in a binary tree.
public class Code02_MaxSubBSTHead {

    // Returns the size of the BST if the subtree rooted at 'head' is a BST, otherwise 0.
    public static int getBSTSize(Node head) {
        if (head == null) { // If the node is null, size is 0.
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>(); // List to store in-order traversal.
        in(head, arr); // Fill arr with in-order traversal of the tree.
        for (int i = 1; i < arr.size(); i++) { // Check if in-order is strictly increasing.
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0; // Not a BST.
            }
        }
        return arr.size(); // Return size if BST.
    }

    // In-order traversal to fill the list with nodes.
    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) { // Base case: null node.
            return;
        }
        in(head.left, arr);  // Traverse left subtree.
        arr.add(head);       // Add current node.
        in(head.right, arr); // Traverse right subtree.
    }

    // Brute-force method: returns the head of the largest BST subtree.
    public static Node maxSubBSTHead1(Node head) {
        if (head == null) { // If tree is empty, return null.
            return null;
        }
        if (getBSTSize(head) != 0) { // If current subtree is BST, return head.
            return head;
        }
        Node leftAns = maxSubBSTHead1(head.left);   // Recurse on left subtree.
        Node rightAns = maxSubBSTHead1(head.right); // Recurse on right subtree.
        // Return the subtree with the larger BST size.
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }

    // Optimized method: returns the head of the largest BST subtree using post-order traversal.
    public static Node maxSubBSTHead2(Node head) {
        if (head == null) { // If tree is empty, return null.
            return null;
        }
        return process(head).maxSubBSTHead; // Use Info object to get the answer.
    }

    // Recursively processes each node to gather information about BST subtrees.
    public static Info process(Node X) {
        if (X == null) { // Base case: null node.
            return null;
        }
        Info leftInfo = process(X.left);   // Recursively process left subtree.
        Info rightInfo = process(X.right); // Recursively process right subtree.
        int min = X.value; // Initialize min as current node's value.
        int max = X.value; // Initialize max as current node's value.
        Node maxSubBSTHead = null; // Head of the largest BST found so far.
        int maxSubBSTSize = 0;     // Size of the largest BST found so far.
        if (leftInfo != null) { // Update min, max, and largest BST info from left subtree.
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
            maxSubBSTHead = leftInfo.maxSubBSTHead;
            maxSubBSTSize = leftInfo.maxSubBSTSize;
        }
        if (rightInfo != null) { // Update min, max, and largest BST info from right subtree.
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
            if (rightInfo.maxSubBSTSize > maxSubBSTSize) {
                maxSubBSTHead = rightInfo.maxSubBSTHead;
                maxSubBSTSize = rightInfo.maxSubBSTSize;
            }
        }
        // Check if current node with its left and right subtrees forms a BST.
        if ((leftInfo == null ? true : (leftInfo.maxSubBSTHead == X.left && leftInfo.max < X.value))
                && (rightInfo == null ? true : (rightInfo.maxSubBSTHead == X.right && rightInfo.min > X.value))) {
            // If so, update head and size to current node and its subtree.
            maxSubBSTHead = X;
            maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize) + 1;
        }
        // Return Info object with all relevant information for parent calls.
        return new Info(maxSubBSTHead, maxSubBSTSize, min, max);
    }

    // Generates a random BST for testing.
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // Helper to recursively generate a random BST.
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) { // Randomly decide to stop.
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue)); // Random value.
        head.left = generate(level + 1, maxLevel, maxValue);  // Generate left child.
        head.right = generate(level + 1, maxLevel, maxValue); // Generate right child.
        return head;
    }

    // Main method for testing the solution.
    public static void main(String[] args) {
        int maxLevel = 4;      // Maximum depth of generated trees.
        int maxValue = 100;    // Maximum node value.
        int testTimes = 1000000; // Number of tests.
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue); // Generate random tree.
            if (maxSubBSTHead1(head) != maxSubBSTHead2(head)) { // Compare brute-force and optimized results.
                System.out.println("Oops!"); // Print error if results differ.
            }
        }
        System.out.println("finish!"); // Print end message.
    }

    // Definition for a binary tree node.
    public static class Node {
        public int value;   // Node value.
        public Node left;   // Left child.
        public Node right;  // Right child.

        public Node(int data) {
            this.value = data;
        }
    }

    // Info class holds information about subtree for the main algorithm.
    public static class Info {
        public Node maxSubBSTHead; // Head of the largest BST in this subtree.
        public int maxSubBSTSize;  // Size of the largest BST in this subtree.
        public int min;            // Minimum value in this subtree.
        public int max;            // Maximum value in this subtree.

        public Info(Node h, int size, int mi, int ma) {
            maxSubBSTHead = h;
            maxSubBSTSize = size;
            min = mi;
            max = ma;
        }
    }

}
