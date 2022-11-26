package com.epam.training.ticketservice.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Boolean isClashing(String movie, String newMovie,
                                     Integer durationInMinutes, Integer newMovieDurationInMinutes) {
        try {
            LocalDateTime movieStart = LocalDateTime.parse(movie,format);
            LocalDateTime movieEnd = movieStart.plusMinutes(durationInMinutes);

            LocalDateTime newMovieStart = LocalDateTime.parse(newMovie,format);
            LocalDateTime newMovieEnd = newMovieStart.plusMinutes(newMovieDurationInMinutes);

            if (isBetween(movieStart, movieEnd, newMovieStart)) {
                return true;
            } else {
                if (isBetween(movieStart, movieEnd, newMovieEnd)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (DateTimeParseException e) {
            throw e;
        }
    }

    public static Boolean isInBreak(String movie, String newMovie, Integer duration, Integer newMovieDuration) {

        try {
            LocalDateTime movieStart = LocalDateTime.parse(movie, format);
            LocalDateTime breakStart = movieStart.plusMinutes(duration);
            LocalDateTime breakEnds = breakStart.plusMinutes(10);

            LocalDateTime newMovieStart = LocalDateTime.parse(newMovie, format);

            if (isBetween(breakStart, breakEnds, newMovieStart)) {
                return true;
            } else {
                return false;
            }
        } catch (DateTimeParseException e) {
            throw e;
        }
    }

    private static boolean isBetween(LocalDateTime start, LocalDateTime end, LocalDateTime newMovieStart) {
        return newMovieStart.isAfter(start) && newMovieStart.isBefore(end);
    }
}
