package class04.practice;

import util.Tester;

import java.util.Arrays;

public class MergeSort2 {

    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;
        divide(nums, 0, nums.length - 1);
    }

    public static void divide(int[] nums, int l, int r) {
        if (l == r) return;
        int m = l + ((r - l) >> 1);
        divide(nums, l, m);
        divide(nums, m + 1, r);
        merge(nums, l, m, r);
    }

    public static void sort2(int[] nums) {
        if (nums == null || nums.length < 2) return;
        int step = 1;

        int n = nums.length;
        while (step <= n) {
            int l = 0;
            while (l + step <= n) {
                int m = l + step - 1;
                int r = Math.min(n - 1, m + step);
                merge(nums, l, m, r);
                l = r + 1;
            }
            if (step > (n >> 1)) break;
            step <<= 1;
        }
    }


    public static void merge(int[] nums, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int p1 = l;
        int p2 = m + 1;
        int i = 0;
        while (p1 <= m && p2 <= r) {
            temp[i++] = nums[p1] < nums[p2] ? nums[p1++] : nums[p2++];
        }
        while (p1 <= m) {
            temp[i++] = nums[p1++];
        }
        while (p2 <= r) {
            temp[i++] = nums[p2++];
        }
        System.arraycopy(temp, 0, nums, l, temp.length);
    }

    public static void main(String[] args) {
        for (int i = 0; i < Tester.testTimes; i++) {
            int[] nums = Tester.generateRandomArray();
            int[] copy = Arrays.copyOf(nums, nums.length);
            Arrays.sort(copy);
            sort2(nums);
            if (!Tester.isArrayEquals(nums, copy)) {
                System.out.println("Oops!");
                Tester.printArray(nums);
                Tester.printArray(copy);
                break;
            }

        }
        System.out.println("Nice");
    }
}
