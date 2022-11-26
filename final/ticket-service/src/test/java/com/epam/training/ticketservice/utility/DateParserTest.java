package com.epam.training.ticketservice.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DateParserTest {

    @Test
    void formattingClashionException() {
        Assertions.assertThrows(DateTimeParseException.class, () ->
                DateParser.isClashing("a","b",1,1));
    }

    @Test
    void formattingBreakException() {
        Assertions.assertThrows(DateTimeParseException.class, () ->
                DateParser.isInBreak("a","b",1,1));
    }

    @Test
    void startsDuring() {
        Boolean isClashing = DateParser.isClashing("2021-01-01 11:00","2021-01-01 12:00",120,120);
        assertTrue(isClashing);
    }

    @Test
    void startsBefore() {
        Boolean isClashing = DateParser.isClashing("2021-01-01 11:00","2021-01-01 10:00",120,120);
        assertTrue(isClashing);
    }

    @Test
    void duringBreak() {
        Boolean isInBreak = DateParser.isInBreak("2021-01-01 10:00","2021-01-01 11:09",60,120);
        assertTrue(isInBreak);
    }

    @Test
    void notDuringBreak() {
        Boolean isInBreak = DateParser.isInBreak("2021-01-01 10:00","2021-01-01 18:00",60,120);
        assertFalse(isInBreak);
    }

    @Test
    void noClash() {
        Boolean isClashing = DateParser.isClashing("2021-01-01 10:00","2021-01-01 15:00",240,120);
        assertFalse(isClashing);
    }
}