package class13.practice02;
import java.util.List;

public class MaxHappy {

    public static class Employee{
        public int happy ;
        public List<Employee> nexts ;
        public Employee(int happy){
            this.happy = happy ;
        }
    }

    public static class Info{
        public int yes;
        public int no;
        public Info(int yes, int no){
            this.yes = yes ;
            this.no = no ;
        }
    }

    public static int maxHappy2(Employee head){
        Info empInfo =  process(head);
        return Math.max(empInfo.yes, empInfo.no) ;
    }

    private static Info process(Employee head){
        if(head == null){
            return new Info(0,0) ; 
        }

        int yes = head.happy ;
        int no =0 ;
        
        for(Employee c: head.nexts){
            Info childInfo = process(c) ;
            yes += childInfo.no ;
            no +=  Math.max(childInfo.yes, childInfo.no);
        }

        return new Info(yes, no);

    }

}
