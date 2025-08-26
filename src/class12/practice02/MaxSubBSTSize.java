package class12.practice02;

// Class to compute the size of the largest BST subtree in a binary tree.
public class MaxSubBSTSize {

    // Node definition for the binary tree.
    public static class Node{
        public int value ;
        public Node left , right ;
        public Node(int v){
            value = v ;
        }
    }

    // Info class holds subtree statistics for the algorithm.
    public static class Info{
        public int maxSubBSTSize ; 
        public int allSize ; 
        public int max, min ;
        public Info(int maxSubBSTSize, int allSize, int max, int min){
            this.maxSubBSTSize = maxSubBSTSize ;
            this.allSize = allSize ;
            this.max = max ;
            this.min = min ;
        }
    }

    // Returns the size of the largest BST subtree in the given binary tree.
    public static int maxSubBSTSize(Node head){
        // Good: null check prevents NullPointerException.
        if (head == null) {
            return 0;
        }
        return process(head).maxSubBSTSize ;
    }

    // Recursively processes each node to gather information about BST subtrees.
    private static Info process(Node head){
        if(head == null){
            return null ;
        }
        Info leftInfo = process(head.left) ;
        Info rightInfo = process(head.right) ;

        int max = head.value ;
        int min = head.value ;
        int allSize = 1 ;

        int p1 = -1; // Suggestion: rename to leftBSTSize
        if(leftInfo != null){
           p1 = leftInfo.maxSubBSTSize ;
           max = Math.max(max, leftInfo.max) ;
           min = Math.min(min, leftInfo.min) ; 
           allSize += leftInfo.allSize ;
        }
        int p2 = -1; // Suggestion: rename to rightBSTSize
        if(rightInfo != null){
            p2 = rightInfo.maxSubBSTSize ;
            max = Math.max(max, rightInfo.max) ;
            min = Math.min(min, rightInfo.min) ;
            allSize += rightInfo.allSize ;
        }

        int p3 = 0 ; // Suggestion: rename to currentBSTSize
        // The following logic checks if the left and right subtrees are BSTs and if the current node can form a BST with them.
        boolean isLeftBST = leftInfo == null ? true : leftInfo.allSize == leftInfo.maxSubBSTSize && leftInfo.max < head.value ;
        boolean isRightBST = rightInfo == null ? true : rightInfo.allSize == rightInfo.maxSubBSTSize && rightInfo.min > head.value ;
        if(isLeftBST && isRightBST){
            if(leftInfo != null)
                p3 += leftInfo.maxSubBSTSize ;
            if(rightInfo != null)
                p3 += rightInfo.maxSubBSTSize ;
            p3 += 1;
        }

        // Returns the Info object with the largest BST size, total size, max and min values.
        return new Info(Math.max(p1, Math.max(p2,p3)), allSize, max, min);

    }

    // Review summary:
    // 1. Consider adding a main method or unit tests for validation.
    // 2. Variable names p1, p2, p3 could be more descriptive.
    // 3. Add JavaDoc comments for public methods and classes.
    // 4. Code logic is correct and efficient (O(N)), using post-order traversal and information passing.
    // 5. Consistent formatting and spacing is recommended.

}
