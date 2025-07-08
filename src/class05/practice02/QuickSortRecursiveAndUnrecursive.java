package class05.practice02;

import java.util.*;

public class QuickSortRecursiveAndUnrecursive {


    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l >= r)
            return;
        int[] res = netherlandsFlag(arr, l, r);
        process(arr, l, res[0] - 1);
        process(arr, res[1] + 1, r);
    }

    private static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    public static int[] netherlandsFlag(int[] arr, int l, int r) {
        if (l > r)
            return new int[]{-1, -1};
        if (l == r)
            return new int[]{l, l};

        int less = l - 1;
        int more = r;
        int index = l;

        swap(arr, l + new Random().nextInt(r - l + 1), r);
        while (index < more) {
            if (arr[index] < arr[r]) {
                swap(arr, index++, ++less);
            } else if (arr[index] > arr[r]) {
                swap(arr, index, --more);
            } else {
                index++;
            }
        }
        swap(arr, more, r);
        return new int[]{less + 1, more};
    }


    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        Stack<Op> s = new Stack<>();
        Op op = new Op(0, arr.length - 1);
        s.push(op);

        Random rand = new Random();
        while (!s.isEmpty()) {
            op = s.pop();
            if (op.right > op.left) {
                swap(arr, op.left + rand.nextInt(op.right - op.left + 1), op.right);
                int[] res = netherlandsFlag(arr, op.left, op.right);
                s.push(new Op(op.left, res[0] - 1));
                s.push(new Op(res[1] + 1, op.right));
            }
        }

    }

    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        Queue<Op> list = new LinkedList<>();
        Random rand = new Random();
        Op op = new Op(0, arr.length - 1);
        list.offer(op);
        while (!list.isEmpty()) {
            op = list.poll();
            if (op.left < op.right) {
                int index = op.left + rand.nextInt(op.right - op.left + 1);
                swap(arr, index, op.right);
                int[] res = netherlandsFlag(arr, op.left, op.right);
                list.offer(new Op(op.left, res[0] - 1));
                list.offer(new Op(res[1] + 1, op.right));
            }
        }
    }


    public static void main(String[] args) {
        int[] arr1 = new int[]{9, 3, 4, 5, 1, 7, 9, 0, 12, 13};
        int[] arr2 = new int[arr1.length];
        int[] arr3 = new int[arr1.length];

        System.arraycopy(arr1, 0, arr2, 0, arr1.length);
        System.arraycopy(arr1, 0, arr3, 0, arr1.length);

        quickSort1(arr1);
        quickSort2(arr2);
        quickSort2(arr3);
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        System.out.println(Arrays.toString(arr3));

    }


    public static class Op {
        int left, right;

        public Op(int l, int r) {
            left = l;
            right = r;
        }
    }


}
