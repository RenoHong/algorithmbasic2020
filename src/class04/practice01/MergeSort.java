package class04.practice01;

import util.Tester;

import java.util.Arrays;

public class MergeSort {
    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;
        process(nums, 0, nums.length - 1);
    }

    public static void process(int[] nums, int l, int r) {
        if (l == r) return;
        int m = l + ((r - l) >> 1);
        process(nums, l, m);
        process(nums, m + 1, r);
        merge(nums, l, m, r);
    }

    public static void sort2(int[] nums) {
        if (nums == null || nums.length < 2) return;

        int n = nums.length;
        int step = 1;
        while (step <= n) {
            int l = 0;
            while (l < n) {
                if (step >= n - l) break;

                int m = l + step - 1;
                int r = Math.min(n - 1, m + step);
                merge(nums, l, m, r);
                l = r + 1;
            }
            if (step > n / 2) break;
            step = step << 1;
        }
    }

    public static void merge(int[] nums, int l, int m, int r) {
        int[] t = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = m + 1;
        while (p1 <= m && p2 <= r) {
            t[i++] = nums[p1] < nums[p2] ? nums[p1++] : nums[p2++];
        }
        while (p2 <= r) {
            t[i++] = nums[p2++];
        }
        while (p1 <= m) {
            t[i++] = nums[p1++];
        }
        System.arraycopy(t, 0, nums, l, t.length);
    }

    public static void main(String[] args) {
        for (int i = 0; i < Tester.testTimes; i++) {
            int[] nums = Tester.generateRandomArray(true);
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
