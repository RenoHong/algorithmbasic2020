package class14.practice02;
import java.util.PriorityQueue;
import java.util.Random;

public class LessMoneySplitGold {

    public static int lessMoney1(int[] arr){
        int res =0 ;  
        PriorityQueue<Integer> q = new PriorityQueue<>() ;
        for(int i: arr){
            q.add(i) ;
        }
        while(q.size() > 1){
            int n = q.poll() + q.poll() ;
            res += n;
            q.add(n);
        }
        
         return res ;
    }

    public static int lessMoney2(int[] arr){
        if(arr == null || arr.length ==0)
            return 0;
        return process(arr, 0) ;
    }

    private static int process(int[] arr, int pre){
        if(arr.length == 1){
            return pre ;
        }
        int ans = Integer.MAX_VALUE ; 

        for(int i =0 ; i<arr.length -1 ; i++){
            for(int j = i+1 ; j< arr.length; j++){
                ans = Math.min(ans,process(merge(arr, i, j), pre+arr[i] + arr[j])) ;
            }
        }
        return ans ;
    }

    private static int[] merge(int[] arr, int i, int j){
        int[] res = new int[arr.length -1] ;
        int newIndex = 0 ;
        for(int index =0 ; index < arr.length ; index++){
            if(index != i && index != j){
                res[newIndex++] =  arr[index] ;
            }
        }
        res[res.length -1] =  arr[i] + arr[j] ;
        return res ; 
    }

    public static int[] generateRandomArray(int maxValue, int maxSize){
        Random rand = new Random() ;
        int size = rand.nextInt(maxSize) + 1;
        int[] res = new int[size] ;
        for(int i=0; i< res.length ; i++){
            res[i] = rand.nextInt(maxValue) +1 ;
        }
        return res ;
    }


    public static void main(String[] args){
         int testTimes =100_000;
         int maxValue =100;
         int maxSize = 6 ;
         System.out.println(">>>>") ; 
         for(int i=0;i< testTimes; i++){
            int[] arr = generateRandomArray(maxValue, maxSize);
            int[] copied = new int[arr.length] ;
            System.arraycopy(arr, 0, copied, 0, arr.length) ;
            int ret1 = lessMoney1(arr) ;
            int ret2 = lessMoney2(copied) ;
            if(ret1 != ret2){
                System.out.println("Oops! " + ret1 + " VS " + ret2) ;
                return ;
            }
         }
         System.out.println("Finish!") ;
    }
}
