package class03.practice02;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Test {
    public static void main(String[] args) {

        LocalDateTime d1 = LocalDateTime.now();
        LocalDateTime d2 = d1.plusMinutes(2);
        long between = ChronoUnit.DAYS.between(d1, d2);
        System.out.println(between);
    }
}
