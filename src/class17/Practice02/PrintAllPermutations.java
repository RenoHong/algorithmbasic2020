package class17.Practice02;
import java.util.*;

public class PrintAllPermutations {

    public static List<String> permutation1(String s){
        List<String> ans = new ArrayList<>();
        String path = "" ;
        List<Character> chars = new ArrayList<>();
        for(Character c: s.toCharArray()){
            chars.add(c) ;
        }
        process1(chars, ans, path);
        
        return ans ;
    }

    private static void process1(List<Character> chars, List<String> ans, String path){
        if(chars.isEmpty()){
            ans.add(path) ;
        }else{ 
            for(int i =0; i< chars.size(); i++){
                Character current = chars.get(i) ; 
                chars.remove(i) ;
                process1(chars, ans, path + current) ;
                chars.add(i, current) ;
            }
        }
    }

    public static List<String> permutation2(String s){

        List<String> ans = new ArrayList<>(s.length());
        if(s == null || s.length() ==0){
            return ans;
        }
        char[] chars = s.toCharArray(); 
        process2(chars, 0, ans) ;
        return ans ;
        
    }

    private static void process2(char[] chars, int index, List<String> ans){
        if(index == chars.length){
            ans.add(String.valueOf(chars)) ;
        }else{
        // process2(['a','b','c'], 0)
        // │
        // ├─ Loop i=0: swap(0,0) → ['a','b','c']  ← KEEPS 'a' at position 0
        // │  │
        // │  └─ process2(['a','b','c'], 1)
        // │     │
        // │     ├─ Loop i=1: swap(1,1) → ['a','b','c']  ← KEEPS 'b' at position 1
        // │     │  │
        // │     │  └─ process2(['a','b','c'], 2)
        // │     │     │
        // │     │     ├─ Loop i=2: swap(2,2) → ['a','b','c']  ← KEEPS 'c' at position 2
        // │     │     │  └─ process2(['a','b','c'], 3) → BASE CASE → Add "abc" ✓
        // │     │     │  swap(2,2) back
        // │     │
        // │     ├─ Loop i=2: swap(1,2) → ['a','c','b']  ← SWAPS 'b' and 'c'
        // │     │  │
        // │     │  └─ process2(['a','c','b'], 2)
        // │     │     └─ Loop i=2: swap(2,2) → ['a','c','b']
        // │     │        └─ process2(['a','c','b'], 3) → BASE CASE → Add "acb" ✓
        // │     │        swap(2,2) back
        // │     │  swap(1,2) back → ['a','b','c']
        // │
        // ├─ Loop i=1: swap(0,1) → ['b','a','c']  ← SWAPS 'a' and 'b'
        // │  │
        // │  └─ process2(['b','a','c'], 1)
        // │     ├─ swap(1,1) → ['b','a','c']
        // │     │  └─ ... → Add "bac" ✓
        // │     ├─ swap(1,2) → ['b','c','a']
        // │     │  └─ ... → Add "bca" ✓
        // │  swap(0,1) back → ['a','b','c']
        // │
        // └─ Loop i=2: swap(0,2) → ['c','b','a']  ← SWAPS 'a' and 'c'
        //    │
        //    └─ process2(['c','b','a'], 1)
        //       ├─ swap(1,1) → ['c','b','a']
        //       │  └─ ... → Add "cba" ✓
        //       ├─ swap(1,2) → ['c','a','b']
        //       │  └─ ... → Add "cab" ✓
        //    swap(0,2) back → ['a','b','c']           
            for(int i = index ; i < chars.length ; i++){
                swap(chars, index, i) ;
                process2(chars, index +1, ans);
                swap(chars, index, i) ;
            }
        }
    }

    public static List<String> permutation3(String s){

        List<String> ans = new ArrayList<>();
        if(s == null || s.length() == 0){
            return ans ;
        }

        char[] chars = s.toCharArray() ;
        process3(chars, 0 , ans) ;
        return ans ;
    }

    private static void process3(char[] chars, int index, List<String> ans){
        if(index == chars.length){
            ans.add(String.valueOf(chars));
        }else{
            boolean[] visited = new boolean[256] ;
   
            for(int i = index ; i< chars.length; i++){
                if(!visited[chars[i]]){
                    visited[chars[i]] = true ;
                    swap(chars, i, index) ;
                    process3(chars, index +1, ans) ;
                    swap(chars, i, index) ;
                }
            }
             
        }
    }

    public static void main(String[] args){
        String s ="acc" ; 
        System.out.println(permutation1(s));
        System.out.println("====================") ;
        System.out.println(permutation2(s));
        System.out.println("====================") ;
        System.out.println(permutation3(s));        
    }

    private static void swap(char[] chars , int x, int y){
        char temp = chars[x] ; 
        chars[x] = chars[y];
        chars[y] = temp ;
    }

}
