package class01.practice03;

public class BubbleSort {
    
    public static void sort(int[] arr){
        if(arr == null || arr.length <2){
            return ;
        }

        for(int i=0; i<arr.length -1;i++){
            for(int j = i +1 ;j< arr.length ;j++){
                if(arr[i]> arr[j]){
                    int t = arr[i] ;
                    arr[i] = arr[j];
                    arr[j] = t ;
                }
            }
        }
    }

    public static void main(String[] args){
        int[] arr = {4,2,5,8,2,1,4,3,9} ;
        sort(arr) ;
        System.out.println(java.util.Arrays.toString(arr));
    }
}
