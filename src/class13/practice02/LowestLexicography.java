package class13.practice02;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;
public class LowestLexicography {

    public static String lowestString1(String[] strs){
         if(strs == null || strs.length == 0){
            return "" ;
         }
         return process(strs).isEmpty() ? "" : process(strs).first() ;

    }

    private static TreeSet<String> process(String[] strs){
        TreeSet<String> ans = new TreeSet<>();
        if(strs.length ==0){
            ans.add("") ;
        }
        for(int i =0; i< strs.length ; i++){
            String first = strs[i] ; 
            String[] rest = removeIndexString(strs, i);

            TreeSet<String> children = process(rest) ;
            while(!children.isEmpty()){
                ans.add(first+ children.pollFirst()) ;
            } 
        }
        return ans ;
    }

    private static String[] removeIndexString(String[] strs, int i){
        if(strs == null || strs.length ==0){
            return null ;
        }
        String[] ans = new String[strs.length -1] ;
        int k =0 ;
        for(int j =0 ; j < strs.length ; j++){
            if(i != j){
                ans[k++] = strs[j] ;
            }
        }
        return ans ;
    }


    public static String lowestString2(String[] strs){
        if(strs == null || strs.length == 0){
            return "";
        }

        Arrays.sort(strs, (s1, s2)-> (s1+s2).compareTo(s2+s1) ) ;
        StringBuilder sb = new StringBuilder(); 
        for(String s: strs){
            sb.append(s) ;
        }
        return sb.toString();
    }

    private static String generateRandomString(int maxLen){
        Random rand = new Random() ;
        char[] chars = new char[rand.nextInt(maxLen)+1] ;
        for(int i =0 ; i< chars.length ; i++){
            int val = rand.nextInt(25) ;
            chars[i] = (char)(Math.random() > 0.5 ? 65 + val : 97 + val) ;
        }
        return String.valueOf(chars) ;
    }

    private static String[] generateRandomStringArray(int maxSize, int maxLen){
        int size = new Random().nextInt(maxSize) ;
        String[] ans = new String[size];
        
        for(int i =0 ;i < size ;i++){
            ans[i] = generateRandomString(maxLen);
        }
        return ans ;
    }

    public static void main(String[] args){
        int maxSize = 5 ;
        int maxLen = 5 ;
        int testTimes = 1000_000 ; 
        
        System.out.println(">>>") ;
        for(int i =0 ;i< testTimes; i++){
            String[] strs = generateRandomStringArray(maxSize, maxLen) ; 
            if( !lowestString1(strs).equals(lowestString2(strs)) ){
                System.out.println("Opps!") ;
                System.out.println(Arrays.toString(strs));
                System.out.println(lowestString1(strs));
                System.out.println(lowestString2(strs));
                break;
            }
        }
        System.out.println("<<<") ;
    }

}

