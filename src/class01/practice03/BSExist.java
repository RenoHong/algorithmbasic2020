package class01.practice03;

public class BSExist {
    public static boolean exists(int[] arr, int num){
        if(arr == null) return false ;
        int l = 0 , r = arr.length -1 ;
        while(l <= r){
            int m = l + ((r -l) /2) ;
            if(arr[m] == num){
                return true ;
            }else if(arr[m] > num){
                r = m -1;
            }else{
                l = m + 1 ;
            }
        }
        return false ;
    }

    public static void main(String[] args){
        int[] arr ={1,2,3,4,5,6,7,8,9,10} ;
        System.out.println(exists(arr, 3));
        System.out.println(exists(arr, 4));
        System.out.println(exists(arr, 33)); 
    }
}
