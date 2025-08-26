package class13.practice02;

public class IsCBT {

    public static class Node{
        public int value ;
        public Node right, left ;
        public Node(int val){
            value = val ;
        }
    }
    public static class Info{
        public boolean isFull ;
        public boolean isCBT ;
        public int height ;
        public Info(boolean isFull, boolean isCBT, int height){
            this.isCBT = isCBT ;
            this.isFull = isFull ;
            this.height = height ;
        }
    }
    

    public static boolean isCBT(Node head){
        return process(head).isCBT ;
    }

    private static Info process(Node head){
        if(head == null){
            return new Info(true, true, 0);
        }
        Info leftInfo = process(head.left) ;
        Info rightInfo = process(head.right) ;
        
        int height = Math.max(leftInfo.height, rightInfo.height) + 1 ; 

        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height ;
        boolean isCBT = isFull;
        if(leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height +1 ) {
            isCBT = true;
        }else if(leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height){
            isCBT = true ;
        }else if(leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1){
            isCBT = true ;
        }
        return new Info(isFull,isCBT, height) ;
    }

}
