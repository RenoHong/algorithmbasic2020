package class01.practice;

import java.util.Arrays;

public class InsertionSort2 {

    public static void sort(int[] arr){
        if (arr == null || arr.length <2) return ;
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i -1;
            while(j >=0 && key < arr[j-1]){
                arr[j+1] = arr[j] ;
                j--;
            }
            arr[j+1] = key ;
        }

    }

    public static int[] generateRandomArray(int maxLen, int maxValue){
        int len = (int)(Math.random() * (maxLen+1) );
        int[] arr = new int[len] ;

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*maxValue);
        }
        return arr;
    }

    public static boolean isArrayEquals(int[] ori, int[] dest){
        if (ori == null && dest != null)
            return false ;
        if (ori != null && dest == null)
            return false ;
        if (ori == null && dest == null)
            return true ;
        if(ori.length != dest.length)
            return false ;
        for (int i = 0; i < ori.length; i++) {
            if (ori[i] !=  dest[i])
                return false ;
        }
        return true ;
    }

    public static void main(String[] args) {

        int testTimes = 1000000;
        int maxLen = 50 ;
        int maxValue = 100;

        for (int i = 0; i < testTimes; i++) {
            int[] test1 = generateRandomArray(maxLen, maxValue);
            int[] test2 = Arrays.copyOf(test1, test1.length);
            int[] ori = Arrays.copyOf(test1, test1.length) ;

            if (! isArrayEquals(test1, test2)){
                System.out.println("Oops!");
                System.out.println("Original array:" + ori);
                System.out.println("test1:"+ test1);
                System.out.println("test2:" + test2);
            }

        }

        System.out.println("Test completed.");

    }
}
