package class19.practice02;

public class ConvertToLetterString {

    public static int number(String str){
        if(str == null || str.length() == 0){
            return 0; 
        }
        return process(str.toCharArray(), 0) ;
    }

    private static int process(char[] chars, int index) {
        if(index == chars.length) return 1 ;
        if(chars[index] == '0') return 0 ; 

        int p = process(chars, index +1) ; 
        if(index +1 < chars.length && (chars[index] - '0') * 10  + chars[index] -'0' <=26){
            p += process(chars, index + 2) ;
        }
        return p ;
    }

    public static int dp1(String str){
        if(str == null || str.length() == 0){
            return  0; 
        } 
        int len = str.length() ; 
        char[] chars = str.toCharArray() ;
        int[] dp = new int[len+1] ;
        chars[len] = 1; 
        for(int i = len -1 ; i >=0 ; i++){
            if(chars[i] == '0'){
                dp[i] = 0 ; 
            }else{
                int p = dp[i + 1] ;
                if(i + 1 < len && ( chars[i] - '0' ) * 10 + chars[i+1] <=26){
                    p += dp[i+2] ;
                }
                dp[i] = p ;
            }
        }
        return dp[0];
    }

    public static void main(String[] args){
        System.out.println(number("11111222"));
        System.out.println(number("11111222"));
    }
    
}
