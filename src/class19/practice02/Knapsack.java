package class19.practice02;

public class Knapsack {

    public static int maxValue(int[] w, int[] v, int bag){
        if(w == null || v == null || bag <0 || w.length != v.length){
            return 0;
        }
        return process(w, v, 0, bag) ;
    }

    private static int process(int[] w, int[] v, int index, int bag){
        if(bag < 0) return 0; 
        if(index == w.length) return 0 ;

        int p1 = process(w, v, index+1, bag) ;
        // int p2 = process(w, v, index + 1, bag - w[index]) ;
        // p2 = p2 == -1? 0: v[index] + p2 ;        
        int p2 = bag - w[index] < 0? 0: v[index] + process(w, v, index +1, bag-w[index]) ;

        return Math.max(p1, p2) ;
    }

    public static int dpWay(int[] w, int[] v, int bag){
        if(w == null || v == null || bag <0 || w.length != v.length){
            return 0;
        } 
        int N = w.length ;
        int[][] dp = new int[N+1][bag+1] ;

        for(int index = N -1 ;index >=0; index--){
            for(int b = 0; b <= bag; b++ ){
                int p1 = dp[index + 1][b] ;
                int p2 = 0;
                if(b - w[index] >= 0){
                    p2 = v[index] + dp[index + 1][b-w[index]];
                }
                dp[index][b] = Math.max(p1, p2) ;
            }
        }
        return dp[0][bag] ;
    }

    public static void main(String[] args){
        int[] weights = {3, 2, 4, 7, 3, 1, 7};
        int[] values = {5, 6, 3, 19, 12, 4, 2};
        int bag = 15;

        System.out.println(maxValue(weights, values, bag));
        System.out.println(dpWay(weights, values, bag)) ;
    }

}
