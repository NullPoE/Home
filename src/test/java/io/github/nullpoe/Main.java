package io.github.nullpoe;


import java.time.LocalDate;
import java.time.YearMonth;

public class Main {

    public static void main(String[] args) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(currentYear, month);
            LocalDate lastDay = yearMonth.atEndOfMonth();

            System.out.println(currentYear + "년 " + month + "월의 마지막 날짜: " + lastDay.getDayOfMonth());
        }
    }
}
