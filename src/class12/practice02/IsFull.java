package class12.practice02;

public class IsFull {

    public static class Node{
        public int value ;
        public Node left, right ;
        public Node(int val){
            this.value = val ;
        }
    }

    public static boolean isFull(Node head){
        if(head == null){
            return true ;
        }

        return isFull(head.left) && isFull(head.right) ;
    }

}
