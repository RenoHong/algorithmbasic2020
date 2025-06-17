package class01.practice02;

/**
 * Provides a method to find a local minimum index in an array.
 */
public class BSAwesome {

    /**
     * Finds an index of a local minimum in the array.
     *
     * @param arr The input array.
     * @return The index of a local minimum, or -1 if not found.
     */
    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length == 0) return -1;
        if (arr.length == 1 || arr[1] > arr[0])
            return 0;
        if (arr[arr.length - 2] > arr[arr.length - 1])
            return arr.length - 1;
        int l = 0;
        int r = arr.length - 1;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (arr[m] < arr[m + 1]) {
                if (arr[m] < arr[m - 1])
                    return m;
                else
                    r = m - 1;

            } else {
                l = m + 1;
            }
        }
        return l;
    }

    /**
     * Main method for testing getLessIndex.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        int[] t = {5, 3, 4, 5, 7, 2, 4, 9, 7, 9};
        System.out.println(getLessIndex(t));
    }
}