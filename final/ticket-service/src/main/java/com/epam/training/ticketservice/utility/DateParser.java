package com.epam.training.ticketservice.utility;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateParser {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Boolean isClashing(String movie, String newMovie, Integer duration, Integer newMovieDuration) {

        try {
            LocalDateTime movieStart = LocalDateTime.parse(movie,format);
            LocalDateTime movieEnd = movieStart.plusMinutes(duration);

            LocalDateTime newMovieStart = LocalDateTime.parse(newMovie,format);
            LocalDateTime newMovieEnd = newMovieStart.plusMinutes(newMovieDuration);

            if (isBetween(movieStart, movieEnd, newMovieStart)) {
                return true;
            } else {
                if (isBetween(movieStart, movieEnd, newMovieEnd)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            return true;
        }
    }

    public static Boolean isInBreak(String movie, String newMovie, Integer duration, Integer newMovieDuration) {

        try {
            LocalDateTime movieStart = LocalDateTime.parse(movie,format);
            LocalDateTime breakStart = movieStart.plusMinutes(duration);
            LocalDateTime breakEnds = breakStart.plusMinutes(10);

            LocalDateTime newMovieStart = LocalDateTime.parse(newMovie,format);
            LocalDateTime newMovieEnd = newMovieStart.plusMinutes(newMovieDuration);

            if (isBetween(breakStart, breakEnds, newMovieStart)) {
                return true;
            } else {
                if (isBetween(breakStart, breakEnds, newMovieEnd)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            return true;
        }
    }

    private static boolean isBetween(LocalDateTime breakStart, LocalDateTime breakEnds, LocalDateTime newMovieStart) {
        return newMovieStart.isAfter(breakStart) && newMovieStart.isBefore(breakEnds);
    }
}
