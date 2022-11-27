package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.entities.ScreeningEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.services.ScreeningService;
import com.epam.training.ticketservice.utility.DateParser;
import com.epam.training.ticketservice.utility.ScreeningTool;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreeningServiceImplTest {

    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ScreeningTool screeningTool;

    @InjectMocks
    ScreeningService screeningService = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository, screeningTool);

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    void addScreeningClear() {

        MovieEntity movie = new MovieEntity(null, "Spirited Away", "Animation", 120);
        when(movieRepository.findByTitle("Spirited Away")).thenReturn(movie);

        RoomEntity room = new RoomEntity(null, "Louie", 12, 12);
        when(roomRepository.findByName("Louie")).thenReturn(room);

        when(screeningRepository.findByRoom(room)).thenReturn(Collections.emptyList());

        ScreeningEntity screening = new ScreeningEntity(null, movie, room, "now", Collections.emptyList());

        screeningService.addScreening(new ScreeningDT("Spirited Away", "Louie", "now"));

        verify(screeningRepository).save(screening);
    }

    @Test
    void addScreeningClashing() {

        System.setOut(new PrintStream(outputStreamCaptor));

        MovieEntity movie = new MovieEntity(null, "Spirited Away", "Animation", 120);
        when(movieRepository.findByTitle("Spirited Away")).thenReturn(movie);
        RoomEntity room = new RoomEntity(null, "Louie", 12, 12);
        when(roomRepository.findByName("Louie")).thenReturn(room);
        ScreeningEntity screening = new ScreeningEntity(null, movie, room, "now", Collections.emptyList());
        when(screeningRepository.findByRoom(room)).thenReturn(List.of(screening));

        String expected;
        String actual;

        try(MockedStatic<DateParser> parser = Mockito.mockStatic(DateParser.class)){
            parser.when(() -> DateParser.isClashing("now","now",120,120))
                    .thenReturn(true);

            screeningService.addScreening(new ScreeningDT("Spirited Away", "Louie", "now"));

            expected = "There is an overlapping screening";
            actual = outputStreamCaptor.toString().trim();
        }
        assertEquals(actual, expected);
    }

    @Test
    void addScreeningBreakClash() {

        System.setOut(new PrintStream(outputStreamCaptor));

        MovieEntity movie = new MovieEntity(null, "Spirited Away", "Animation", 120);
        when(movieRepository.findByTitle("Spirited Away")).thenReturn(movie);
        RoomEntity room = new RoomEntity(null, "Louie", 12, 12);
        when(roomRepository.findByName("Louie")).thenReturn(room);
        ScreeningEntity screening = new ScreeningEntity(null, movie, room, "now", Collections.emptyList());
        when(screeningRepository.findByRoom(room)).thenReturn(List.of(screening));

        String expected;
        String actual;

        try(MockedStatic<DateParser> parser = Mockito.mockStatic(DateParser.class)){
            parser.when(() -> DateParser.isClashing("now","now",120,120))
                    .thenReturn(false);
            parser.when(() -> DateParser.isInBreak("now","now",120,120))
                    .thenReturn(true);

            screeningService.addScreening(new ScreeningDT("Spirited Away", "Louie", "now"));

            expected = "This would start in the break period after another screening in this room";
            actual = outputStreamCaptor.toString().trim();
        }
        assertEquals(actual, expected);
    }

    @Test
    void addScreeningPassAll() {

        System.setOut(new PrintStream(outputStreamCaptor));

        MovieEntity movie = new MovieEntity(null, "Spirited Away", "Animation", 120);
        when(movieRepository.findByTitle("Spirited Away")).thenReturn(movie);
        RoomEntity room = new RoomEntity(null, "Louie", 12, 12);
        when(roomRepository.findByName("Louie")).thenReturn(room);
        ScreeningEntity screening = new ScreeningEntity(null, movie, room, "now", Collections.emptyList());
        when(screeningRepository.findByRoom(room)).thenReturn(List.of(screening));

        try(MockedStatic<DateParser> parser = Mockito.mockStatic(DateParser.class)){
            parser.when(() -> DateParser.isClashing("now","now",120,120))
                    .thenReturn(false);
            parser.when(() -> DateParser.isInBreak("now","now",120,120))
                    .thenReturn(false);

            screeningService.addScreening(new ScreeningDT("Spirited Away", "Louie", "now"));
        }
        verify(screeningRepository).save(screening);
    }

    @Test
    void deleteScreening(){

        ScreeningDT dt = new ScreeningDT("Spirited Away", "Louie", "Now");

        ScreeningEntity entity = new ScreeningEntity();

        when(screeningTool.getScreening(dt)).thenReturn(entity);

        screeningService.deleteScreening(dt);

        verify(screeningRepository).delete(entity);
    }

    @Test
    void listScreeningsEmpty(){

        when(screeningRepository.findAll()).thenReturn(Collections.emptyList());

        String actual = screeningService.listScreenings();
        String expected = "There are no screenings";

        assertEquals(actual, expected);

    }

    @Test
    void listScreeningsNonEmpty(){

        MovieEntity movie = new MovieEntity(null, "Spirited Away", "Animation", 120);
        RoomEntity room = new RoomEntity(null, "Louie", 12, 12);
        ScreeningEntity screening = new ScreeningEntity(null,movie,room,"now",Collections.emptyList());

        when(screeningRepository.findAll()).thenReturn(List.of(screening));

        String actual = screeningService.listScreenings();
        String expected = "Spirited Away (Animation, 120 minutes), screened in room Louie, at now";

        assertEquals(actual, expected);

    }
}