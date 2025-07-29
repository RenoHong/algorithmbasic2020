package class12;

import java.util.ArrayList;

// 在线测试链接 : https://leetcode.cn/problems/largest-bst-subtree
//333. Largest BST Subtree
//Given a binary tree, find the largest subtree which is a Binary Search Tree (BST),
// where largest means subtree with largest number of nodes in it.
//
//Note:
//A subtree must include all of its descendants.
//Input: [10,5,15,1,8,null,7]
//
//        10
//        / \
//        5  15
//        / \   \
//        1   8   7
//
//        Output: 3
//        Explanation: The Largest BST Subtree in this case is the highlighted one.
//        The return value is the subtree's size, which is 3.
//Follow up:
//        Can you figure out ways to solve it with O(n) time complexity?
public class Code05_MaxSubBSTSize {

    // Returns the size of the largest BST subtree in the given binary tree.
    public static int largestBSTSubtree(TreeNode head) {
        if (head == null) { // If the tree is empty, return 0.
            return 0;
        }
        return process(head).maxBSTSubtreeSize; // Start the recursive process and return the result.
    }

    // Recursively processes each node to gather information about BST subtrees.
    public static Info process(TreeNode x) {
        if (x == null) { // Base case: if node is null, return null.
            return null;
        }
        Info leftInfo = process(x.left);   // Recursively process left subtree.
        Info rightInfo = process(x.right); // Recursively process right subtree.
        int max = x.val;                   // Initialize max value as current node's value.
        int min = x.val;                   // Initialize min value as current node's value.
        int allSize = 1;                   // Initialize size as 1 (current node).
        if (leftInfo != null) {            // If left subtree exists,
            max = Math.max(leftInfo.max, max); // update max value.
            min = Math.min(leftInfo.min, min); // update min value.
            allSize += leftInfo.allSize;       // add left subtree size.
        }
        if (rightInfo != null) {           // If right subtree exists,
            max = Math.max(rightInfo.max, max); // update max value.
            min = Math.min(rightInfo.min, min); // update min value.
            allSize += rightInfo.allSize;       // add right subtree size.
        }
        int p1 = -1;                       // Largest BST size in left subtree.
        if (leftInfo != null) {
            p1 = leftInfo.maxBSTSubtreeSize;
        }
        int p2 = -1;                       // Largest BST size in right subtree.
        if (rightInfo != null) {
            p2 = rightInfo.maxBSTSubtreeSize;
        }
        int p3 = -1;                       // Largest BST size if current subtree is BST.
        // Check if left and right subtrees are BSTs.
        boolean leftBST = leftInfo == null ? true : (leftInfo.maxBSTSubtreeSize == leftInfo.allSize);
        boolean rightBST = rightInfo == null ? true : (rightInfo.maxBSTSubtreeSize == rightInfo.allSize);
        if (leftBST && rightBST) { // If both subtrees are BSTs,
            boolean leftMaxLessX = leftInfo == null ? true : (leftInfo.max < x.val); // left max < current
            boolean rightMinMoreX = rightInfo == null ? true : (x.val < rightInfo.min); // current < right min
            if (leftMaxLessX && rightMinMoreX) { // If BST property holds,
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;   // left size
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize; // right size
                p3 = leftSize + rightSize + 1; // total size if current subtree is BST
            }
        }
        // Return Info object with the largest BST size, total size, max and min values.
        return new Info(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
    }

    // Brute-force method for validation: returns the size of the largest BST subtree.
    public static int right(TreeNode head) {
        if (head == null) { // If tree is empty, return 0.
            return 0;
        }
        int h = getBSTSize(head); // Check if current subtree is BST and get its size.
        if (h != 0) {
            return h;
        }
        // Otherwise, check left and right subtrees.
        return Math.max(right(head.left), right(head.right));
    }

    // Checks if the subtree is a BST and returns its size, or 0 if not BST.
    public static int getBSTSize(TreeNode head) {
        if (head == null) {
            return 0;
        }
        ArrayList<TreeNode> arr = new ArrayList<>(); // List to store in-order traversal.
        in(head, arr); // Fill arr with in-order traversal.
        for (int i = 1; i < arr.size(); i++) { // Check if in-order is strictly increasing.
            if (arr.get(i).val <= arr.get(i - 1).val) {
                return 0; // Not a BST.
            }
        }
        return arr.size(); // Return size if BST.
    }

    // In-order traversal to fill the list with nodes.
    public static void in(TreeNode head, ArrayList<TreeNode> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);  // Traverse left subtree.
        arr.add(head);       // Add current node.
        in(head.right, arr); // Traverse right subtree.
    }

    // Generates a random BST for testing.
    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // Helper to recursively generate a random BST.
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) { // Randomly decide to stop.
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue)); // Random value.
        head.left = generate(level + 1, maxLevel, maxValue);  // Generate left child.
        head.right = generate(level + 1, maxLevel, maxValue); // Generate right child.
        return head;
    }

    // Main method for testing the solution.
    public static void main(String[] args) {
        int maxLevel = 4;      // Maximum depth of generated trees.
        int maxValue = 100;    // Maximum node value.
        int testTimes = 1000000; // Number of tests.
        System.out.println("测试开始"); // Print start message.
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue); // Generate random tree.
            if (largestBSTSubtree(head) != right(head)) { // Compare efficient and brute-force results.
                System.out.println("出错了！"); // Print error if results differ.
            }
        }
        System.out.println("测试结束"); // Print end message.
    }

    // Definition for a binary tree node.
    public static class TreeNode {
        public int val;         // Node value.
        public TreeNode left;   // Left child.
        public TreeNode right;  // Right child.

        public TreeNode(int value) {
            val = value;
        }
    }

    // Info class holds information about subtree for the main algorithm.
    public static class Info {
        public int maxBSTSubtreeSize; // Size of the largest BST in this subtree.
        public int allSize;           // Total number of nodes in this subtree.
        public int max;               // Maximum value in this subtree.
        public int min;               // Minimum value in this subtree.

        public Info(int m, int a, int ma, int mi) {
            maxBSTSubtreeSize = m;
            allSize = a;
            max = ma;
            min = mi;
        }
    }

}
