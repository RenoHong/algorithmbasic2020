package class12.practice02;
import java.util.LinkedList;
import java.util.Queue;

public class IsCBT {

    public static class Node{
        public int value  ;
        public Node left, right ;
        public Node(int val){
            value = val;
        }
    }

    public static boolean isCBT1(Node head){
        if(head == null) 
            return true ;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head) ;
        Node l = null, r = null ;
        boolean isLeaf = false ;

        while(!queue.isEmpty()){
            Node node = queue.poll() ;
            l = node.left;
            r = node.right ;

            if(isLeaf && (l != null || r != null) ||
                (l == null && r != null)
            ){
                return false ;
            }

            if(l != null ) queue.add(l) ;
            if(r != null ) queue.add(r) ;
            if(r == null || l == null){
                isLeaf = true ;
            }
        } 
        return true ;

    }

}
