package class17.Practice02;
import java.util.HashSet;

public class Test {
    public static void main(String[] args){
        HashSet<Integer> set = new HashSet<>();
        set.add(1) ;
        set.add(2) ;
        set.add(1) ;
        set.add(1) ;
        System.out.println(set) ;
        System.out.println(set.size()) ;
    }
}
