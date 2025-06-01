package class01.practice;

import util.Tester;

import java.util.Arrays;

public class BS {

    public static boolean exist(int [] nums, int value){
        if (nums == null || nums.length <1) return false ;
        int left = 0 ;
        int right = nums.length -1;

        while(left < right){
            int mid = left + ((right - left) >>1);
            if (nums[mid] > value){
                right = mid -1 ;
            } else if (nums[mid] < value) {
                left = mid + 1 ;
            }
            else{
                return true ;
            }
        }
        return nums[left] == value ;
    }

    public static int nearestRight(int[] nums, int value){
        if(nums == null || nums.length <1 ) return -1;
        int left = 0;
        int right = nums.length -1;
        int index =-1;
        while (left <= right){
            int mid = left + ((right -left) >> 1) ;
            if (nums[mid] <= value){
                index = mid ;
                left = mid +1 ;
            } else {
                right = mid -1 ;
            }
        }
     return index ;
    }

    public static int nearestLeft(int[] nums, int value){
        int index = -1;
        if (nums == null || nums.length <1)  return index;
        int left = 0 ;
        int right = nums.length -1 ;
        while (left <= right){
            int mid = left + ((right-left) >>1) ;
            if (nums[mid] >= value){
                index = mid ;
                right =  mid -1 ;
            } else if (nums[mid] < value) {
                left = mid+1 ;
            }
        }

        return index ;
    }


    public static void main(String[] args) {


        for (int i = 0; i < Tester.testTimes; i++) {
            int[] nums = Tester.generateRandomArray(true);
            Arrays.sort(nums) ;
            int n =(int)Math.random() * Tester.maxValue ;
//            if ( Tester.isValueExistInArray(nums, n) != exist(nums,n) ){
//                System.out.println("Oops!");
//                Tester.printArray(nums);
//            }
//            int tester = Tester.nearestLeft(nums, n) ;
//            int coder = nearestLeft(nums, n) ;
//            if ( tester != coder ){
//                System.out.println("Oops!");
//                System.out.println("Value to check " + n + " tester: " + tester + " coder:"+ coder);
//                Tester.printArray(nums);
//                break;
//            }

            int tester = Tester.nearestRight(nums, n) ;
            int coder = nearestRight(nums, n) ;
            if ( tester != coder ){
                System.out.println("Oops!");
                System.out.println("Value to check " + n + " tester: " + tester + " coder:"+ coder);
                Tester.printArray(nums);
                break;
            }
        }
        System.out.println("Nice!");
    }
}
