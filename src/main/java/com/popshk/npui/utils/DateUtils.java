package com.popshk.npui.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String PATTERN = "dd.MM.yyyy HH:mm:ss";

    public static String customDateFromForJson(boolean isPlusMonth) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDateTime lastMonthDateTime = isPlusMonth ? currentDateTime.plusMonths(1) : currentDateTime.minusMonths(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);

        return lastMonthDateTime.format(formatter);
    }
}