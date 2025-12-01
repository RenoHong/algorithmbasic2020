package class01.practice03;

public class InsertionSort {
    public static void sort(int[] arr){
        if(arr == null || arr.length <2) return ;
        for(int i = 1 ; i < arr.length ; i ++){
            for(int j = i -1 ; j >=0 && arr[j] > arr[j+1]; j--){
                int temp = arr[j] ;
                arr[j] = arr[j+1];
                arr[j+1] = temp ;
            }
        }
    }

    public static void main(String[] args){
        int[] arr = {7,8,3,2,9,4,0,2,6,5} ;
        sort(arr) ;
        System.out.println(java.util.Arrays.toString(arr)) ;
    }
}
