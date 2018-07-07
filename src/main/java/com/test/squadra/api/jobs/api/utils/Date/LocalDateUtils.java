package com.test.squadra.api.jobs.api.utils.Date;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateUtils {
    public static LocalDate stringToLocalDate(String data){
        try {
            return LocalDate.parse(data);
        }catch (DateTimeParseException e){
            return null;
        }

    }
}
