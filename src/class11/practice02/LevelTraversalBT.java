package class11.practice02;
import java.util.LinkedList;
import java.util.Queue;

public class LevelTraversalBT {

    public static class Node{
        public Node left, right ;
        public int value ; 
        public Node(int value){
            this.value = value;
        }
    }

    public static void level(Node root){
        if(root == null)
            return ;

        Queue<Node> queue = new LinkedList<>() ;
        queue.offer(root) ;
        while(!queue.isEmpty()){
            Node n = queue.poll(); 
            System.out.println(n.value) ;
            if(n.left != null){
                queue.offer(n.left) ;
            }
            if(n.right != null){
                queue.offer(n.right) ;
            }
        }
    
    }


    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        level(head);
        System.out.println("========");
    }

}
