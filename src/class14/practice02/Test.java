package class14.practice02;
import java.util.*;
public class Test {
    // public static void main(String[] args) {
    //     int[] arr = {0, 1, 2, 3, 4, 5};
    //     for (int i = 0; i < arr.length; i++) {
    //         for (int j = i+1; j < arr.length; j++) {
    //             System.out.println("i: " + arr[i] + ", j: " + arr[j]);
    //         }
    //     }
    // }

    // 1.	子序列
	// •	打印一个字符串的所有子序列
	// •	例：输入 "abc"，输出 ["", "a", "b", "c", "ab", "ac", "bc", "abc"]

    public static void main(String[] args){
        String s ="abc" ;
        List<String> ans = new ArrayList<String>() ;
        process(s.toCharArray(), 0, "", ans) ;
        System.out.println(ans) ;
    }

    private static void process(char[] s, int index, String path, List<String> ans){
        if(index == s.length){
            ans.add(path);
            return ;
        }
         
        process(s,index+1, path, ans);
        process(s,index+1, path + s[index], ans) ;
    }

    // private static List<String> process(String s){
    //     List<String> ans = new ArrayList<>();
    //     if(s == null || s.length() ==0){
    //         ans.add("") ;
    //     }else{
    //         char[] chars = s.toCharArray() ;
    //         for(int i =0; i< chars.length; i++){
    //             char c = chars[i];
    //             ans.add(String.valueOf(c)); 
    //             if(i != chars.length -1){
    //                 List<String> nexts = process(s.substring(i+1));
    //                 for(int j =0 ; j < nexts.size(); j++){
    //                     ans.add(c+ nexts.get(j)) ;
    //                 }
    //             }
    //         }
    //     } 
    //     return ans ;
    // }
}
