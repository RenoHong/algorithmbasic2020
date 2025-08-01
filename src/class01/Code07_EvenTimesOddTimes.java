package class01;

public class Code07_EvenTimesOddTimes {

    // arr中，只有一种数，出现奇数次
    public static void printOddTimesNum1(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        System.out.println(eor);
    }

    // arr中，有两种数，出现奇数次
    public static void printOddTimesNum2(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        // eor = a ^ b
        // eor != 0
        // eor必然有一个位置上是1。 因为是异或，既然是1就代表异或之前两个数那一位肯定不同所以异或的结果才会是1.
        //To explain the variables in the selected code:
        //
        //### rightOne
        //```java
        //int rightOne = eor & (~eor + 1); // 提取出最右的1
        //```
        //This operation extracts the rightmost bit that is set to 1 in the `eor` variable:
        //- `~eor` inverts all bits in `eor`
        //- `(~eor + 1)` creates the two's complement
        //- `eor & (~eor + 1)` isolates only the rightmost set bit
        //
        //Since `eor` contains the XOR of two odd-occurring numbers (a ^ b), this rightmost bit is a position where these two numbers differ (one has 0, one has 1).
        //
        //### onlyOne
        //```java
        //int onlyOne = 0; // eor'
        //```
        //This variable is initialized to collect one of the two odd-occurring numbers. The algorithm uses `rightOne` to separate all numbers into two groups:
        //1. Numbers with the identified bit set
        //2. Numbers with the identified bit not set
        //
        //The two odd-occurring numbers will fall into different groups. When XORing all numbers in one group, `onlyOne` will eventually contain exactly one of these two special numbers.
        //
        //The other odd-occurring number is then found using `eor ^ onlyOne`.
        int rightOne = eor & (~eor + 1); // 提取出最右的1
        int onlyOne = 0; // eor'
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & rightOne) != 0) {
                onlyOne ^= arr[i];
            }
        }
        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }

    public static void main(String[] args) {
        int a = 5;
        int b = 7;

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a);
        System.out.println(b);

        int[] arr1 = {3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1};
        printOddTimesNum1(arr1);

        int[] arr2 = {4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2};
        printOddTimesNum2(arr2);

    }

}
