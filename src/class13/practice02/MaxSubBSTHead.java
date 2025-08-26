package class13.practice02;

import java.util.ArrayList;

public class MaxSubBSTHead {

    public static class Node{
        public int value ;
        public Node left ;
        public Node right ;
        public Node(int val){
            value = val ;
        }
    }

    public static class Info{
        public int maxSubBSTSize ; 
        public Node maxSubBSTNode ;
        public int max, min ;
        public Info(int maxSubBSTSize , Node maxSubBSTNode, int max, int min){
            this.maxSubBSTNode = maxSubBSTNode ;
            this.maxSubBSTSize = maxSubBSTSize ;
            this.max = max ;
            this.min = min ;
        }
    }

    public static Node maxSubBSTHead2(Node head){
        if(head == null) 
            return null ;
        return process(head).maxSubBSTNode ;
    }

    private static Info process(Node head){
        if(head == null){
            return null ;
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right); 

        int max = head.value;
        int min = head.value ;
        int maxSubBSTSize = 0  ;
        Node maxSubBSTNode = null;

        if(leftInfo!= null){
            max = Math.max(max, leftInfo.max) ;
            min = Math.min(min, leftInfo.min);
            maxSubBSTSize = Math.max(maxSubBSTSize, leftInfo.maxSubBSTSize);
            maxSubBSTNode = leftInfo.maxSubBSTNode ;
        }
        if(rightInfo != null){
            max = Math.max(max, rightInfo.max) ;
            min = Math.min(min, rightInfo.min);
            if(maxSubBSTSize < rightInfo.maxSubBSTSize){
                maxSubBSTNode = rightInfo.maxSubBSTNode ;
                maxSubBSTSize = rightInfo.maxSubBSTSize ;
            }
        }
        if((leftInfo == null || (leftInfo.maxSubBSTNode == head.left && leftInfo.max < head.value))
        && (rightInfo ==null || (rightInfo.maxSubBSTNode == head.right && rightInfo.min > head.value))){
            maxSubBSTNode = head ;
            maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize) +
                (rightInfo == null? 0: rightInfo.maxSubBSTSize) + 1 ;
        }

        return new Info(maxSubBSTSize, maxSubBSTNode, max, min) ;

    }



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

}
