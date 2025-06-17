package class01.practice02;

public class Test {

    public static void main(String[] args) {
        System.out.println(1 << 31);
        System.out.println(1 << 31 | 1 << 30);

        int i = (int) (4294967294L);
        System.out.println(i);
    }
}
