package class18.practice02;

public class RobotWalk {

     public static int ways1(int N, int start, int aim, int K) {
        if(start < 1 || start > N || K < 1 || aim > N || aim < 0){
            return -1;
        } 
         return process1(start, aim, K, N);
     }

     private static int process1(int current, int aim, int remain, int N){
        if(remain == 0){
            return current == aim ?1:0 ; 
        }
        if(current == 1){
            return process1(2, aim, remain-1, N) ;
        }
        if(current == N){
            return process1(current -1, aim, remain-1, N) ;
        }else{
            return process1(current -1, aim, remain-1, N) + process1(current +1, aim, remain-1, N) ;
        }
     }

    public static int ways2(int N, int start, int aim, int K) {
        if(start < 1 || start > N || K < 1 || aim > N || aim < 0){
            return 0;
        } 
        int[][] dp = new int[N+1][K+1];
        for(int i =0 ; i < dp.length; i++){
            for(int j=0; j< dp[i].length; j++){
                dp[i][j] = -1;
            }
        }
         return process2(start, aim, K, N, dp);
     }

     private static int process2(int current, int aim, int rest, int N, int[][] dp){
        int ret = dp[current][rest] ;
        if(ret != -1){
            return ret ; 
        }
        if(rest == 0){
            ret = current == aim? 1 :0 ;
        }else if(current == 1){
            ret = process2(2, aim, rest -1 , N, dp) ;
        }else if(current == N){
            ret = process2(N-1, aim, rest-1, N, dp) ;
        }else{
            ret = process2(current -1, aim, rest -1, N, dp) + process2(current+1, aim, rest-1, N, dp) ;
        }
        dp[current][rest] = ret ;
        return ret ;
        
     }
        
    public static int ways3(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        int[][] dp = new int[N+1][K+1];
        dp[aim][0] = 1 ; 
        for(int column = 1; column <= K; column++){
            dp[1][column] = dp[2][column-1];
            for(int row = 2; row < N; row++){
                dp[row][column] = dp[row -1][column -1] + dp[row+1][column-1];
            }
            dp[N][column] = dp[N-1][column-1];
        }
   
        return dp[start][K] ;
    }
     public static void main(String[] args){ 
        System.out.println(ways1(5, 2, 4, 6)) ;
        System.out.println(ways2(5, 2, 4, 6)) ;
        System.out.println(ways3(5, 2, 4, 6)) ;
     }

}
