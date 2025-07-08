package class09.practice02;
import java.util.Stack;
public class IsPalindromeList {

    public static class Node{
        int value  ;
        Node next ;
        public Node(int value){
            this.value = value ;
        }
    }


    public static boolean isPalindrome1(Node head){

        if(head == null || head.next == null) 
            return true ;

        Stack<Node> stk = new Stack<>();
        Node current = head ;
        while(current!=null){
            stk.push(current) ;
            current = current.next ;
        }
        
        current = head ;
        while(current!= null){
            if(current.value !=stk.pop().value){
                return false ;
            }
            current = current.next ;
        }
        return true ;
    }

    public static boolean isPalindrome2(Node head){
        if(head == null||head.next == null)
            return true ;
        
        Node slow = head ;
        Node fast = head ;
        Stack<Node> stack = new Stack<>();
        while(fast!=null && fast.next != null && fast.next.next != null){
            fast = fast.next.next ;
            slow = slow.next ;
        }
        while(slow != null){
            stack.push(slow);
            slow = slow.next ;
        }
        Node current = head ;
        while(!stack.isEmpty()){
            if(current.value != stack.pop().value)
                return false ;
            current = current.next ;
        }
        return true ;
    }

    public static boolean isPalindrome3(Node head){
        if(head == null || head.next == null)
            return true ;
        
        Node slow = head ;
        Node fast = head ;
        while(fast != null && fast.next != null && fast.next.next !=null){
            fast = fast.next.next ;
            slow = slow.next ;
        }
        Node mid = slow ;
        Node pre = slow ;  
        while(slow!=null){
            Node next = slow.next ;
            slow.next = pre ;
            pre = slow ;
            slow = next ;
        } 

        Node current = head ;
        while(current != mid ){
            if(current.value != pre.value){
                return false ;
            }
            current = current.next ;
            pre = pre.next ;
        }
 
        Node next = fast.next ;
        fast.next = null ;
        while(next !=mid ){
             pre = fast ;
             fast = next ;
             next = next.next ;
        }

        return true ;
    }

    public static void main(String[] args){
        Node head = null ;
        head = new Node(0);
        head.next = new Node(1);
        head.next.next = new Node(2);
        head.next.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        head.next.next.next.next.next = new Node(0);

        System.out.println(isPalindrome3(head));
    }

}
