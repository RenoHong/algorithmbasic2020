package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Tester {

    public static int testTimes = 100000;
    public static int maxLen = 100;
    public static int maxValue = 100;

    /**
     * Generate a random integer array
     *
     * @param allowDuplicated allow duplicated values to be added to the array.
     * @return a random generated array.
     */
    public static int[] generateRandomArray(boolean allowDuplicated) {
        int len = (int) (Math.random() * (maxLen) + 1);
        int[] nums = new int[len];
        nums[0] = (int) (Math.random() * maxValue);
        for (int i = 1; i < len; i++) {
            if (!allowDuplicated) {
                do {
                    nums[i] = (int) (Math.random() * maxValue);
                } while (nums[i] == nums[i - 1]);
            } else
                nums[i] = (int) (Math.random() * maxValue);
        }
        return nums;
    }

    /**
     * Generate a random integer array
     *
     * @return the generated integer array.
     */
    public static int[] generateRandomArray() {
        return generateRandomArray(true);
    }

    /**
     * Verify if the local minimum value exists in the array.
     *
     * @param nums  the array to check.
     * @param index the index that indicates a local minimum value.
     * @return true exists and false non-exists.
     */
    public static boolean isIndexMinInArray(int[] nums, int index) {
        if (nums == null) return index == -1;
        if (nums.length == 1) return index == 0;
        if (nums.length == 2) {
            int n = nums[0] > nums[1] ? 1 : 0;
            return n == index;
        }
        if (index == 0) {
            return nums[1] > nums[0];
        }
        if (index == nums.length - 1) {
            return nums[nums.length - 2] > nums[nums.length - 1];
        }

        return nums[index - 1] > nums[index] && nums[index + 1] > nums[index];

    }


    /**
     * Print an integer array in order.
     *
     * @param nums the array to print.
     */
    public static void printArray(int[] nums) {
        System.out.println(Arrays.toString(nums));
    }

    public static void printArray(List<Integer> nums) {
        System.out.println(nums);
    }

    /**
     * Verify elements in 2 integer arrays are equals.
     *
     * @param ori the original array to compare.
     * @param dst the destination array to compare.
     * @return true if the 2 arrays are same. false if the 2 arrays are not same.
     */
    public static boolean isArrayEquals(int[] ori, int[] dst) {
        if (ori == null && dst == null) return true;
        if (ori == null && dst != null) return false;
        if (ori != null && dst == null) return false;
        if (ori.length != dst.length) return false;

        for (int i = 0; i < ori.length; i++) {
            if (ori[i] != dst[i])
                return false;
        }

        return true;
    }


    /**
     * Swap 2 integers values in an integer array based on their indexes.
     *
     * @param nums The array
     * @param i    The start index to swap value.
     * @param j    The destination index to swap value.
     */
    public static void swap(int[] nums, int i, int j) {
        if (i != j) {
            nums[i] = nums[i] ^ nums[j];
            nums[j] = nums[i] ^ nums[j];
            nums[i] = nums[i] ^ nums[j];
        }
    }

    /**
     * Verify if a value exists in an array.
     *
     * @param nums  the array which contains the value.
     * @param value the value to check
     * @return true if the value exists in the array. false if the value is not in the array.
     */
    public static boolean isValueExistInArray(int[] nums, int value) {
        if (nums == null || nums.length < 1) return false;
        for (int i : nums) {
            if (i == value) return true;
        }
        return false;
    }

    /**
     * Get the index of nearest left for an integer in the array.
     *
     * @param nums  the array to check.
     * @param value the value to check.
     * @return
     */
    public static int nearestLeft(int[] nums, int value) {
        if (nums == null || nums.length < 1) return -1;
        int index = -1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] >= value) {
                index = i;
            } else {
                break;
            }
        }

        return index;
    }

    public static int nearestRight(int[] nums, int value) {
        if (nums == null || nums.length < 1) return -1;
        int index = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= value)
                index = i;
            else
                break;
        }
        return index;
    }

    /**
     * Create a random linked list
     *
     * @return the created linked list.
     */
    public static Node generateRandomNodes() {
        int len = (int) (Math.random() * (maxLen) + 1);
        ;
        Node head = new Node((int) (Math.random() * maxValue));
        Node pre = head;
        for (int i = 1; i < len; i++) {
            head.next = new Node((int) (Math.random() * maxValue));
            head = head.next;
        }
        return pre;
    }

    public static int generateRandomInteger() {
        return (int) (Math.random() * maxValue);
    }

    public static boolean checkNodeReverse(List<Integer> list, Node head) {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).equals(head.value) == false) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * Verify if a node has been deleted in the linked nodes
     *
     * @param head head of the nodes
     * @param v    value should have been deleted.
     * @return true, the node has been deleted. false, the value still exists.
     */
    public static boolean isValueRemovedInNodes(Node head, int v) {
        if (head == null) return true;
        while (head != null) {
            if (head.value == v) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * Convert a linked list to a array list
     *
     * @param head head of the linked list.
     * @return the converted array list.
     */
    public static List<Integer> nodeToList(Node head) {
        List<Integer> nums = new ArrayList<>();
        while (head != null) {
            nums.add(head.value);
            head = head.next;
        }
        return nums;
    }

    /**
     * Get smaller sum value of an integer array.
     *
     * @param nums the array going to evaluate.
     * @return the smaller sum
     */
    public static int smallerSum(int[] nums) {
        int sum = 0;
        if (nums == null || nums.length < 2)
            return sum;
        else {
            for (int i = 1; i < nums.length; i++) {
                for (int j = 0; j < i; j++) {
                    sum += nums[j] < nums[i] ? nums[j] : 0;
                }
            }
        }
        return sum;
    }


    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.putIfAbsent("abcd", 1);
        Integer bac = map.compute("abcd", (key, value) -> value == null ? 10000 : value + 1);
        System.out.println(bac);
    }

}
