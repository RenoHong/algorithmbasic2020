package class04.practice;

import util.Tester;

import java.util.Arrays;

public class SmallerSum {

    public static int sum(int[] nums) {
        if (nums == null || nums.length < 2) return 0;
        return divide(nums, 0, nums.length - 1);
    }

    public static int divide(int[] nums, int l, int r) {
        if (l == r) return 0;
        int m = l + ((r - l) >> 1);
        return divide(nums, l, m)
                + divide(nums, m + 1, r)
                + merge(nums, l, m, r);
    }

    public static int merge(int[] nums, int l, int m, int r) {
        int[] t = new int[r - l + 1];
        int p1 = l;
        int p2 = m + 1;
        int i = 0;
        int sum = 0;

        while (p1 <= m && p2 <= r) {
            sum += nums[p1] < nums[p2] ? nums[p1] * (r - p2 + 1) : 0;
            t[i++] = nums[p1] < nums[p2] ? nums[p1++] : nums[p2++];
        }
        while (p1 <= m)
            t[i++] = nums[p1++];
        while (p2 <= r)
            t[i++] = nums[p2++];

        System.arraycopy(t, 0, nums, l, t.length);
        return sum;
    }

    public static void main(String[] args) {
        for (int i = 0; i < Tester.testTimes; i++) {
            int[] nums = Tester.generateRandomArray();
            int[] copied = Arrays.copyOf(nums, nums.length);
            int sum = sum(nums);
            int smallerSum = Tester.smallerSum(copied);
            if (sum != smallerSum) {
                System.out.println("Oops!");
                Tester.printArray(nums);
                Tester.printArray(copied);
                System.out.println("sum : " + sum(nums) + " smaller sum:" + smallerSum);
                break;
            }
        }

        System.out.println("Testing completed!");
    }

}
