package class01.practice;

import util.Tester;

import java.util.Arrays;

public class BSAwesome {

    public static int smallest(int[] arr){
        int n = arr.length; ;
        if (arr == null || n == 0) return -1;
        if (n == 1 || arr[0] < arr[1] ) return 0;
        if ( arr[n -1] < arr[n-2]){
            return n-1 ;
        }

        int left = 0 ;
        int right = n -1 ;
        while (left < right -1) // right -1 because left and right cannot be overlapped.
        {
            //[ 70, 9, 84, 99, 47, 52 ]
            int mid = left + ((right - left) >>1);
            if (arr[mid] > arr[mid -1]) {
                right = mid-1 ;
            } else if (arr[mid] > arr[mid+1]) {
                left = mid +1 ;
            }
            else{
                return mid ;
            }
        }
        return arr[left] < arr[right]? left: right ;

    }
    public static void main(String[] args) {

        for (int i =0 ; i< Tester.testTimes; i++){
            int[] ints = Tester.generateRandomArray(false);
            int index = smallest(ints) ;
            if(!Tester.isIndexMinInArray(ints, index)) {
                System.out.println("Oops");
                Tester.printArray(ints);
            }
        }
        System.out.println("Test done!");



    }
}
