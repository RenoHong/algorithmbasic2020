package class17.Practice02;
import java.util.*;

public class PrintAllSubsquences {

    public static List<String> sub(String s){
        List<String> ans = new ArrayList<>() ; 
        String path = "" ; 
        process1(s.toCharArray(), 0, ans, path);
        return ans ;
    }

    public static Set<String> subNoRepeat(String s){
        List<String> ans = sub(s);
        return new HashSet<>(ans);
    }

    private static void process1(char[] s, int index, List<String> ans, String path){
        if(index == s.length){
            ans.add(path) ;
            return ;
        }

        process1(s, index +1, ans, path) ;
        process1(s, index +1 , ans, path+s[index]);
    }

    public static void main(String args[]){
        String s = "accccc" ;
        System.out.println(sub(s)) ;
        System.out.println("===================");
        System.out.println(subNoRepeat(s)) ;
    }

}
