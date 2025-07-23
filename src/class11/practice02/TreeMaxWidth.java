package class11.practice02;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class implements two different algorithms to find the maximum width of a binary tree.
 * Width is defined as the maximum number of nodes at any level of the tree.
 */
public class TreeMaxWidth {

    /**
     * Binary tree node definition
     */
    public static class Node{
        public Node left, right ;  // Left and right child pointers
        public int value ;         // Node's value
        public Node(int val){
            this.value = val ;
        }
    }

    /**
     * Method 1: Calculate maximum width using HashMap to track node levels
     * Time Complexity: O(n) where n is number of nodes
     * Space Complexity: O(n) for the HashMap and queue
     * 
     * @param head Root of the binary tree
     * @return Maximum width (number of nodes at any level)
     */
    public static int maxWidthUseMap(Node head){
        if(head == null) return 0 ;  // Empty tree has width 0
        
        int max = 0;                 // Track maximum width found so far
        int currentLevel = 1 ;       // Current level being processed (starts at 1)
        int currentNodes = 0 ;       // Count of nodes at current level
        
        // HashMap to store each node's level number
        HashMap<Node, Integer> levelMap = new HashMap<>() ; 
        // Queue for level-order traversal (BFS)
        Queue<Node> queue = new LinkedList<>(); 
        
        // Initialize: add root to queue and mark it as level 1
        queue.add(head) ;
        levelMap.put(head, 1) ;

        // Process all nodes level by level
        while(!queue.isEmpty()){
            // Get next node from queue
            Node node = queue.poll();
            // Look up this node's level from our map
            int currentNodeLevel = levelMap.get(node) ;

            // Add left child to queue and mark its level
            if(node.left != null){
                queue.add(node.left) ;
                levelMap.put(node.left, currentNodeLevel +1) ;  // Child is one level deeper
            }
            // Add right child to queue and mark its level
            if(node.right != null){
                queue.add(node.right) ;
                levelMap.put(node.right, currentNodeLevel +1) ; // Child is one level deeper
            }

            // Check if this node belongs to the current level we're counting
            if(currentLevel == currentNodeLevel){
                currentNodes++ ;  // Increment count for current level
            }else{
                // We've moved to a new level
                max = Math.max(max, currentNodes) ;  // Update max with previous level's count
                currentLevel ++ ;                    // Move to next level
                currentNodes = 1 ;                   // Start counting nodes in new level (this node is first)
            } 
        } 
        
        // Don't forget to check the last level's count against max
        max = Math.max(max, currentNodes) ;
        return max ;
    }

    /**
     * Method 2: Calculate maximum width without HashMap using level boundary tracking
     * Time Complexity: O(n) where n is number of nodes  
     * Space Complexity: O(w) where w is maximum width (for queue only)
     * 
     * This approach tracks the last node of each level to know when a level ends.
     * 
     * @param head Root of the binary tree
     * @return Maximum width (number of nodes at any level)
     */
    public static int maxWidthNoMap(Node head){
        if(head == null) return 0 ;   // Empty tree has width 0
        
        Node currentEnd = head ;      // Last node of current level
        Node nextEnd = null ;         // Last node of next level (gets updated as we add children)
        int currentNodes = 0 ;        // Count of nodes processed in current level
        int max = 0 ;                 // Maximum width found so far
        
        // Queue for level-order traversal
        Queue<Node> queue = new LinkedList<Node>(); 
        queue.add(head) ;             // Start with root
        
        while(!queue.isEmpty()){
            // Process next node from queue
            Node current = queue.poll();
            
            // Add left child to queue
            if(current.left != null){
                queue.add(current.left) ;
                nextEnd = current.left ;      // Update next level's end marker
            }
            // Add right child to queue  
            if(current.right != null){
                queue.add(current.right) ;
                nextEnd = current.right ;     // Update next level's end marker (right child comes after left)
            } 
            
            currentNodes ++;                  // Count this node in current level
            
            // Check if we've reached the end of current level
            if(currentEnd == current){
                max = Math.max(currentNodes, max) ;  // Update maximum with current level's count
                currentNodes = 0 ;                   // Reset counter for next level
                currentEnd = nextEnd ;               // Move to next level's boundary
            }
        } 
        return max ;
    }

    /**
     * Generate a random binary tree for testing purposes
     * 
     * @param maxLevel Maximum depth of the tree
     * @param maxValue Maximum value for node values
     * @return Root of randomly generated tree
     */
    public static Node generateRandomBST(int maxLevel, int maxValue){
        return generate(1, maxLevel, maxValue) ;
    }
    
    /**
     * Recursive helper method to generate random tree
     * 
     * @param level Current level being generated
     * @param maxLevel Maximum allowed level
     * @param maxValue Maximum node value
     * @return Generated subtree root (or null)
     */
    private static Node generate(int level, int maxLevel, int maxValue){
        // Stop recursion if max level reached or random chance (20% chance to stop)
        if(level > maxLevel || Math.random() > 0.8){
            return null ;
        }

        // Create new node with random value (1 to maxValue)
        Node head = new Node((int)(Math.random()* maxValue) +1) ;
        // Recursively generate left and right subtrees
        head.left = generate(level +1 , maxLevel, maxValue) ;
        head.right = generate(level+1, maxLevel, maxValue) ;
        return head ;
    }

    /**
     * Test harness to verify both algorithms produce same results
     */
    public static void main(String[] args){
        int testTimes = 1000_000;     // Number of test cases
        int maxLevel = 8 ;            // Maximum tree depth
        int maxValue = 1000;          // Maximum node value
        
        System.out.println(">>>") ;
        
        // Run multiple test cases
        for(int i=0; i< testTimes ; i++){
            Node root = generateRandomBST(maxLevel, maxValue) ;  // Generate random tree
            int maxWithMap =  maxWidthUseMap(root) ;             // Test method 1
            int maxWithNoMap = maxWidthNoMap(root) ;             // Test method 2
            
            // Check if both methods give same result
            if( maxWithMap !=  maxWithNoMap){
                System.out.println("Opps: " + maxWithMap + ", " +  maxWithNoMap) ;
                break ;  // Stop testing if mismatch found
            }
        }
        System.out.println("<<<") ;
    }
}
