package com.example.jobs.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormat {

    public static final DateTimeFormatter BASIC_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String formatBasic(LocalDate date) {
        if (date == null) {
            return "present";
        }

        return BASIC_FORMAT.format(date);
    }

    public static String dateformat(LocalDate date) {

        return FORMAT.format(date);
    }

    public static LocalDate convertToLocalDate(String dateString) {
        if ("present".equals(dateString)) {
            return null;
        }
        return LocalDate.parse(dateString, BASIC_FORMAT);
    }
}
