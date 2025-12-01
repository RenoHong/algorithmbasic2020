package class01.practice03;

public class BSNearLeft {

    public static int find(int[] arr, int num){
        if(arr == null || arr.length ==0 ) return -1 ; 
        int ret = -1, l =0 , r = arr.length -1 ;
        while(l <= r){
            int m = l +((r-l) >>1) ;
            if(arr[m] <= num){
                ret = m ;
                l = m + 1 ;
            }else{
                r = m -1 ;
            }
        }

        return ret ;
    }

    public static void main(String args[]){
        int ret = find(new int[]{1,2,3,14,15,16,17,18,19}, 5);
        System.out.println(ret) ;
     }

}
