package class03.practice03;

public class DeleteGivenValue {

    public static Node removeValue(Node head, int num){
        while(head != null){
            if(head.value != num){
                break ;
            }
            head = head.next ;
        }

        Node current = head ;
        Node pre = head ;
        while(current != null){
            if(current.value == num){
                pre.next = current.next ;
            }else{
                pre = current ;
            }
            current = current.next ;
        }
        return head ;

    }

    public static class Node{
        int value ;
        Node next ;
        public Node(int value){
            this.value = value ;
        }
    }

}
