/*
Bubble sorting is always seeking the largest number -> the right most index.
Selection sort is always seeking the smallest number -> the left most index.
 */

package class01.practice;

import java.util.Arrays;

public class BubbleSort {

    public static void sort(int[] arr){
        if (arr == null || arr.length <2) return ;
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j <arr.length-i-1; j++) {
                if (arr[j] > arr[j+1]){
                    int t = arr[j] ;
                    arr[j] = arr[j+1] ;
                    arr[j+1] = t ;
                }
            }
        }
    }

    public static int[] generateRandomArray(int maxLen, int maxValue){
        int len = (int)(Math.random() * (maxLen+1));
        int[] arr = new int[len] ;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * maxValue) ;
        }
        return arr ;
    }

    public static boolean isArrayEquals(int[] ori, int[] dest){
        if (ori == null && dest != null) return false ;
        if (ori != null && dest == null) return false ;
        if (ori == null && dest == null) return true ;
        if (ori.length != dest.length) return false ;
        for (int i =0; i< ori.length ; i++){
            if (ori[i] != dest[i])
                return false ;
        }
        return true ;
    }

    public static void main(String[] args) {
        int testTimes = 100000;
        int maxLen = 100 ;
        int maxValue = 100;

        for (int i = 0; i < testTimes; i++) {
            int [] arr = generateRandomArray(maxLen, maxValue) ;
            int [] copied = Arrays.copyOf(arr, arr.length );
            sort(arr);
            Arrays.sort(copied) ;
            if (! isArrayEquals(arr, copied)){
                System.out.println("Oops!");
            }
        }
        System.out.println("Test completed");
    }
}
