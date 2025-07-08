package class06.practice02;
import java.util.* ;

public class HeapSort03 {

    public static void heapSort(int[] arr){

        if(arr == null || arr.length<2)
            return;

        for(int i = arr.length-1 ;i >=0; i --){
            heapify(arr, i, arr.length) ;
        }

        int heapSize = arr.length ;
        swap(arr, 0, --heapSize);

        while(heapSize >0){
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        } 
    }

    private static void heapify(int[] arr, int index, int heapSize){
        int left = index * 2 +1 ;
        while(left < heapSize){
            int largerIndex = left +1 < heapSize && arr[left +1] > arr[left]? left +1 : left ;
            largerIndex = arr[index] > arr[largerIndex]? index : largerIndex ; 
            if(largerIndex == index) {
                break ; 
            }
            swap(arr, index, largerIndex);
            index = largerIndex ;
            left = index *2 + 1 ;
        }
    }

    private static void swap(int[] arr, int i, int j){
        int temp = arr[i] ;
        arr[i] = arr[j] ;
        arr[j] = temp ;
    }

    public static void main(String[] args){
        int[] arr = new int[]{7,3,9,2,0,3,6,7,8,10,4};
        heapSort(arr) ;
        System.out.println(Arrays.toString(arr) );
    }


}
