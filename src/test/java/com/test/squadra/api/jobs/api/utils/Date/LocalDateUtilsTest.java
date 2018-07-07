package com.test.squadra.api.jobs.api.utils.Date;

import com.test.squadra.api.jobs.api.ApplicationTests;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.test.squadra.api.jobs.api.utils.Date.LocalDateUtils.stringToLocalDate;
import static org.junit.jupiter.api.Assertions.*;

class LocalDateUtilsTest {

    @Test
    public void stringToLocalDateTest() {
        LocalDate localDate = LocalDate.now();
        LocalDate localDateCovete = stringToLocalDate(localDate.toString());
        assertEquals(localDate,localDateCovete);
    }
    @Test()
    public void stringToLocalDateNullTest() {
        LocalDate localDateCovete = stringToLocalDate("323-23");
        assertNull(localDateCovete);
    }
}