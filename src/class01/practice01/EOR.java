package class01.practice01;

public class EOR {

    public static void printOddValue(int[] nums) {
        int n = 0;
        for (int i = 0; i < nums.length; i++) {
            n ^= nums[i];
        }
        System.out.println(n);
    }

    public static void printOddValue2(int[] nums) {
        int n = 0;
        for (int i = 0; i < nums.length; i++) {
            n ^= nums[i];
        }

        // The result of n must be a^b, because all evens were undone to 0 by themselves.

        int rightOne = n & (~n + 1);
        int hasOne = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((nums[i] & rightOne) != 0) {
                hasOne ^= nums[i];
            }
        }

        System.out.println(hasOne + " " + (n ^ hasOne));
    }

    public static void main(String[] args) {
//        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
//        printOddValue(arr1);


        int[] arr2 = {4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2};
        printOddValue2(arr2);
    }
}
