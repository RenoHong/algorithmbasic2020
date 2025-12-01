package class03.practice03;

public class ReverseList {

    public static Node reverse(Node head){ 
        Node pre = null ;
       while(head != null){
            Node next = head.next ; 
            head.next = pre ;
            pre = head;
            head = next ;
       }
       return pre ;
    }

    public static DoubleNode reverse(DoubleNode head){
        DoubleNode pre = null ;
        while(head != null){
            DoubleNode next = head.next ;
            head.next = pre ;
            head.prev = next ;
            pre = head ;
            head = next ;
        }
        return pre ;
    }



    public static class Node{
        int data ; 
        Node next ;
        public Node(int value){
            data = value ;
        }
    }    

    public static class DoubleNode{
        int data ;
        DoubleNode next, prev ;
        public DoubleNode(int value, DoubleNode prev, DoubleNode next){
            this.next = next ;
            this.prev = prev ;
            data = value ;
        }
    }
}




