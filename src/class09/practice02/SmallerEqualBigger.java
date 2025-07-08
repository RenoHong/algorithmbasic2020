package class09.practice02;

public class SmallerEqualBigger {

    public static class Node{
        public int value ;
        public Node next ;
        public Node(int value){
            this.value = value ;
        }
    }

    public static Node listPartition1(Node head, int pivot){
        int length = 0 ;
        Node current = head ;
        while(current!=null){
            length ++;
            current = current.next;
        }

        Node[] nodes = new Node[length] ;
        current = head ;
        for(int i = 0; i<length ; i++){
            nodes[i] =current ;
            current = current.next ;
        }

        arrayPartition(nodes, pivot) ;

        for (int i = 0; i < length-1; i++) {
            nodes[i].next = nodes[i+1];
        }
        nodes[length-1].next = null;

        return nodes[0];
    }

    public static void arrayPartition(Node[] nodes, int pivot){
        int small = -1 ;
        int big = nodes.length ;
        int index = 0 ;
        while(index < big){
            if (nodes[index].value < pivot){
                swap(nodes, ++small, index++);
            }else if(nodes[index].value == pivot){
                index++;
            }else{
                swap(nodes, --big, index);
            }
        }
    }

    public static Node listPartition2(Node head, int pivot){
        Node sH, sT, mH, mT, bH, bT ;
        sH = sT = mH = mT = bH = bT = null ;
        Node current = head ;
        while(current !=null ){
            Node next = current.next ;
            current.next = null ;
            if(current.value <  pivot){
                if(sH == null){
                    sH = sT = current ;
                }else{
                    sT.next = current;
                    sT = current ;
                }
            }else if(current.value == pivot){
                if(mH == null){
                    mH = mT = current ;
                }else{
                    mT.next = current ;
                    mT = current ;
                }
            }else{
                if(bH == null){
                    bH = bT = current ;
                }else{
                    bT.next = current ;
                    bT = current ;
                }
            } 
            current = next ;
        }

        if(sT != null){
            sT.next = mH;
            mT = mT == null? sT: mT ;
        }

        if(mT != null){
            mT.next = bH ;
        }

        return sH != null? sH: mH != null ? mH : bH ;

    }

    private static void swap(Node[] nodes, int x, int y){
        Node temp = nodes[x] ;
        nodes[x] = nodes[y] ;
        nodes[y] = temp ;
    }

    public static void main(String[] args) {
        // 构建测试链表：7->9->1->8->5->2->5
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);

        Node res = listPartition1(head1, 5);
        while(res!=null){
            System.out.printf("%d->", res.value) ;
            res = res.next ;
        }
        System.out.println("=====================") ;

        head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        
        Node res2 = listPartition2(head1, 5);
        while(res2!=null){
            System.out.printf("%d->", res2.value) ;
            res2 = res2.next ;
        }

    }
}
