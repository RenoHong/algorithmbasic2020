package class17.Practice02;
import java.util.*;
public class Hanoi {

    public static void hanoi1(int n){
        leftToRight(n);
    }

    private static void leftToMid(int n){
        if(n ==1){
            System.out.println("Moving " + n + " from left to mid");
        }else{
            leftToRight(n-1);
            System.out.println("Moving " + n + " from left to mid");
            rightToLeft(n-1);
        }
    }
    private static void rightToLeft(int n){
        if(n == 1){
            System.out.println("Moving " + n + " from right to left") ;
        }else{
            rightToMid(n-1);
            System.out.println("Moving " + n + " from right to left");
            midToRight(n-1);
        }
    }

    private static void leftToRight(int n){
        if(n == 1){
            System.out.println("Moving " + n + " from left to right") ;
        }else{
            leftToMid(n-1);
            System.out.println("Moving " + n + " from left to right");
            midToLeft(n-1);
        }
    }
    private static void midToRight(int n){
         if(n == 1){
            System.out.println("Moving " + n + " from mid to right") ;
        }else{
            midToLeft(n-1);
            System.out.println("Moving " + n + " from mid to right");
            leftToMid(n-1);
        }
    }
    private static void midToLeft(int n){
        if(n == 1){
            System.out.println("Moving " + n + " from mid to left") ;
        }else{
            midToRight(n-1);
            System.out.println("Moving " + n + " from mid to left");
            rightToMid(n-1);
        }
    }
    private static void rightToMid(int n){
        if(n == 1){
            System.out.println("Moving " + n + " from right to mid") ;
        }else{
            rightToLeft(n-1);
            System.out.println("Moving " + n + " from right to mid");
            leftToRight(n-1);
        }
    }

    private static void move(String from, String to, String other, int n){
        if(n ==1){
            System.out.println("Moving " + n + " from " + from + " to " + to);
            return ;
        }
        move(from, other, to, n-1) ;
        System.out.println("Moving " + n + " from " + from + " to " + to);
        move(other, to, from, n-1) ;
    }

    public static void hanoi2(int n){
        move("left", "right", "mid", n) ;
    }

    public static void main(String args[]){
        hanoi1(3);
        System.out.println("===========================") ;
        hanoi2(3);
        System.out.println("===========================") ;
        hanoi3(3);
    }

    public static void hanoi3(int n){
        if(n <= 1){
           return ;
        }
        Stack<Record> stk = new Stack<Record>();
        Set<Record> finishedLeft = new HashSet<>() ;

        stk.push(new Record("left", "right", "mid", n)); 
        while(!stk.isEmpty()){
            Record r = stk.pop() ; 
            if(r.level == 1){
                 System.out.println("Moving " + r.level + " " + r.from + " -> " + r.to) ;
            }else{
                if(!finishedLeft.contains(r)){
                    finishedLeft.add(r) ;
                    stk.push(r) ;
                    stk.push(new Record(r.from, r.other, r.to, r.level-1));
                }else{
                    System.out.println("Moving " +  r.level + " " + r.from + " -> " + r.to) ;
                    stk.push(new Record(r.to, r.other, r.from, r.level-1));
                }
            }
        }
    }


    public static class Record{
        String from, to, other ;
        int level ;
        public Record(String f, String t, String o, int l){
            from = f ;
            to = t ;
            other = o ;
            level =l ;
        }

    }


}
