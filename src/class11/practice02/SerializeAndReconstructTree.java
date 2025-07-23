package class11.practice02;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SerializeAndReconstructTree {

    public static class Node{
        public int value ;
        public Node left, right ;
        public Node(int value){
            this.value = value ;
        }
    }

    public static Queue<String> preSerial(Node head){
         Queue<String> ans = new LinkedList<String>();
        if(head == null) return null ;
       
        pre(head, ans) ;
        return ans ;

    }

    private static void pre(Node head, Queue<String> queue){
        if(head == null) 
            queue.add(null);
        else{ 
            queue.add(String.valueOf(head.value));
            pre(head.left, queue) ;
            pre(head.right, queue) ;
        }
    }

    public static Queue<String> inSerial(Node head){
        Queue<String> ans = new LinkedList<>();
        in(head, ans) ;
        return ans ;
    }

    private static void in(Node head, Queue<String> queue){
        if(head == null){
            queue.add(null) ;
        }else{
            in(head.left, queue) ;
            queue.add(String.valueOf(head.value)) ;
            in(head.right, queue);
        }
    }

    public static Queue<String> posSerial(Node head){
        Queue<String> ans = new LinkedList<String>(); 
        pos(head, ans) ;
        return ans ;
    }

    private static void pos(Node head, Queue<String> queue){
        if(head == null){
            queue.add(null) ;
        }else{
            pos(head.left, queue) ;
            pos(head.right, queue) ;
            queue.add(String.valueOf(head.value)) ;
        }
    }

    public static Node buildByPreQueue(Queue<String> queue){
        if(queue == null || queue.isEmpty()){
            return null ;
        }
        return preb(queue) ;
    }

    private static Node preb(Queue<String> queue){
        String value = queue.poll();
        if(value == null)
            return null ;
        Node head = new Node(Integer.valueOf(value)) ;
        head.left = preb(queue) ;
        head.right = preb(queue) ;
        return head ;
    }


    public static Node buildByPosQueue(Queue<String> queue){
        if(queue == null || queue.isEmpty()) return null ;
        Stack<String> stk = new Stack<>(); 
        while(!queue.isEmpty()){
            stk.push(queue.poll()) ;
        }
        return posb(stk);
    }

    private static Node posb(Stack<String> stk){
        String s = stk.pop();
        if(s == null){
            return null ;
        }
        Node head = new Node(Integer.valueOf(s));
        Node right = posb(stk) ;
        Node left = posb(stk) ;
        head.left = left ;
        head.right = right ;
        return head ;
    }
    
    public static Queue<String> levelSerial(Node head){
        Queue<String> ans = new LinkedList<>();
         if(head == null){
            ans.add(null);
         }else{
            Queue<Node> queue = new LinkedList<>();
            Node current = head ; 
            queue.add(head);
            ans.add(String.valueOf(head.value));
            while(!queue.isEmpty()){
                current = queue.poll();
                if(current.left != null){
                    queue.add(current.left);
                    ans.add(String.valueOf(current.left.value)); 
                } else{
                    ans.add(null) ;
                }
                if(current.right != null){
                    queue.add(current.right);
                    ans.add(String.valueOf(current.right.value)) ;
                }else{
                    ans.add(null);
                }
            }
         }
         return ans ;
    }

    private static Node generateNode(String str){
        if(str == null) return null ;
        return new Node(Integer.valueOf(str)) ;
    }

    public static Node buildByLevelQueue(Queue<String> levelList){
        if(levelList == null || levelList.isEmpty()) return null ;

        Node head = generateNode(levelList.poll()) ;
        
        Queue<Node> queue = new LinkedList<>();
        if(head != null){
            queue.add(head) ;
        }

        Node current = null ;
        while(!queue.isEmpty()){
           current = queue.poll() ; 
           if(current != null){
                current.left = generateNode(levelList.poll());
                current.right =  generateNode(levelList.poll());
                if(current.left != null)
                    queue.add(current.left);
                if(current.right!= null)
                    queue.add(current.right);
           }
        }

        return head ;
    }

    public static  Node generateRandomBST(int maxLevel, int maxValue){
        if(maxLevel < 1) return null ;
        return generate(1, maxLevel, maxValue);
    }

    private static Node generate(int level, int maxLevel, int maxValue){
        if(level > maxLevel || Math.random() > 0.8) return null ;

        Node head = new Node((int)(Math.random() * maxValue)) ;
        head.left = generate(level+1, maxLevel, maxValue) ;
        head.right = generate(level+1, maxLevel, maxValue);
        return head ;
    }

    public static boolean isSameStructure(Node head1, Node head2){
        if(head1 == null && head2 == null) return true ;
        if(head1 == null && head2 != null) return false ;
        if(head1 != null && head2 == null) return false ;
        
        return head1.value == head2.value &&
        isSameStructure(head1.left, head2.left) &&
        isSameStructure(head1.right, head2.right) ;
    }

    public static void main(String[] args){
        int testTimes = 1000_000 ;
        int maxValue = 1000 ;
        int maxLevel = 6 ; 
        System.out.println("Begin >>") ;
        for(int i =0 ; i< testTimes ; i++){
            Node head = generateRandomBST(maxLevel, maxValue) ;
            Queue<String> pre = preSerial(head) ;
            Queue<String> pos = posSerial(head) ;
            Queue<String> level = levelSerial(head) ;

            Node preBuild = buildByPreQueue(pre) ;
            Node levelBuild = buildByLevelQueue(level) ;
            Node posBuild = buildByPosQueue(pos) ;

            if(!isSameStructure(preBuild, posBuild)){
                System.out.println("Opps preBuild, posBuild!") ;
                break ;
            }

            if(!isSameStructure(preBuild, levelBuild)){
                System.out.println("Opps preBuild, levelBuild") ;
                break ;
            }

            if(i == 100){
                printTree(preBuild) ;
                printTree(levelBuild) ;
                printTree(posBuild) ;
            }

        }
        System.out.println("End >>");

        
    }
 

    public static void printTree(Node head){
        System.out.println("Binary tree:") ;
        printInOrder(head, 0 , "H" , 15);
        System.out.println("===") ;
    }
    
    private static void printInOrder(Node head, int height, String symbol, int len){
        if(head == null) return ;
        printInOrder(head.right, height +1, "v", len) ;
        String s = symbol + String.valueOf(head.value) + symbol ; 
        int headLen = s.length() ;
        int mid = (len- headLen) / 2 ;
        String spaces = space(mid) ;
        System.out.println(spaces + space(len * height) +  s + spaces);
        printInOrder(head.left, height + 1, "^", len) ;
    }

    private static String space(int num){
        StringBuilder sb = new StringBuilder();
        while(num-- >0){
            sb.append(' ');
        }
        return sb.toString();
    }

}
