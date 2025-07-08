package class05.practice01;

import util.Tester;

import java.util.Arrays;

public class QuickSort {

    public static void swap(int[] arr, int i, int j) {
        Tester.swap(arr, i, j);
    }

    public static int[] netherlandsFlag(int[] nums, int l, int r) {
        if (l > r) return new int[]{-1, -1};
        if (l == r) return new int[]{l, r};

        int smallArea = l - 1;
        int bigArea = r;
        int index = l;
        while (index < bigArea) {
            if (nums[index] < nums[r]) {
                swap(nums, index++, ++smallArea);
            } else if (nums[index] == nums[r]) {
                index++;
            } else {
                swap(nums, index, --bigArea);
            }
        }
        swap(nums, r, bigArea);
        return new int[]{smallArea + 1, bigArea};
    }

    public static void divide2(int[] nums, int l, int r) {
        if (l >= r) return;
        int[] res = netherlandsFlag(nums, l, r);
        divide2(nums, l, res[0] - 1);
        divide2(nums, res[1] + 1, r);
    }

    public static void sort2(int[] nums) {
        if (nums == null || nums.length < 2) return;
        divide2(nums, 0, nums.length - 1);
    }

    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;
        divide(nums, 0, nums.length - 1);
    }

    public static int partition(int[] nums, int l, int r) {
        if (l > r) return -1;
        if (l == r) return l;
        int left = l - 1;
        int index = l;
        while (index < r) {
            if (nums[index] <= nums[r]) {
                //Tester.swap(nums[++left], nums[index]);
                swap(nums, index, ++left);
            }
            index++;
        }
        //Tester.swap(nums[++left] , nums[r]);
        swap(nums, r, ++left);
        return left;
    }


    public static void divide(int[] nums, int l, int r) {
        if (l >= r) return;
        int m = partition(nums, l, r);
        divide(nums, l, m - 1);
        divide(nums, m + 1, r);
    }


    public static void main(String[] args) {
        for (int i = 0; i < Tester.testTimes; i++) {
            int[] nums = Tester.generateRandomArray();
            int[] copied = Arrays.copyOf(nums, nums.length);
            int[] s2 = Arrays.copyOf(nums, nums.length);
            Arrays.sort(copied);
            sort(nums);
            sort2(s2);
            if (!Tester.isArrayEquals(copied, nums) || !Tester.isArrayEquals(copied, s2)) {
                System.out.println("Oops!");
                Tester.printArray(nums);
                Tester.printArray(copied);
                Tester.printArray(s2);
                break;
            }
        }
        System.out.println("Test Completed");

    }
}
