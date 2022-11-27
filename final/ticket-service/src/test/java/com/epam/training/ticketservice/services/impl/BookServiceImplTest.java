package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.entities.*;
import com.epam.training.ticketservice.repository.*;
import com.epam.training.ticketservice.services.BookService;
import com.epam.training.ticketservice.utility.ScreeningTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PriceComponentRepository priceComponentRepository;

    @Mock
    private ScreeningTool screeningTool;

    @InjectMocks
    private BookService bookService = new BookServiceImpl(bookRepository,priceComponentRepository, screeningTool);

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void initialize(){

    }

    @Test
    void getBookingEmpty() {
        when(bookRepository.findByUsername("Caesar")).thenReturn(Collections.emptyList());

        String bookings = bookService.getBooking("Caesar");

        assertEquals(bookings, "");
    }

    @Test
    void getBookingNonEmpty() {

        MovieEntity movie = new MovieEntity(null, "Spirited Away", "Animation", 12);
        RoomEntity room = new RoomEntity(null, "Louie",12,12);

        ScreeningEntity screening = new ScreeningEntity(null, movie,room,"sample-date",Collections.emptyList());

        BookEntity book = new BookEntity(null, screening, "caesar","2,2 3,3");

        List<BookEntity> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findByUsername("Caesar")).thenReturn(books);

        String expected = "Seats (2,2), (3,3) on Spirited Away in room Louie starting at sample-date for 0 HUF";
        String actual= bookService.getBooking("Caesar");

        assertEquals(expected, actual);
    }

    @Test
    void successfulBook() {

        RoomEntity room = new RoomEntity(null, "Louie", 12,12);
        MovieEntity movie = new MovieEntity(null, "Spirited Away","animation",120);

        ScreeningEntity screening = new ScreeningEntity(null,movie, room, "now", Collections.emptyList());
        ScreeningDT screeningDT = new ScreeningDT();

        ScreeningEntity screeningEntity = new ScreeningEntity();
        screeningEntity.setRoom(room);

        BookEntity booked = new BookEntity(null,screening,"brutus","3,3");
        List<BookEntity> bookedList = List.of(booked);

        when(screeningTool.getScreening(screeningDT)).thenReturn(screeningEntity);

        when(bookRepository.findByScreening(screeningEntity)).thenReturn(bookedList);

        when(priceComponentRepository.findByName("Base")).thenReturn(new PriceComponentEntity(null, "Base", 1500));

        bookService.book(screeningDT, "caesar","2,2 1,1");
    }

    @Test
    void occupieadSeat() {

        System.setOut(new PrintStream(outputStreamCaptor));

        RoomEntity room = new RoomEntity(null, "Louie", 12,12);
        MovieEntity movie = new MovieEntity(null, "Spirited Away","animation",120);

        ScreeningEntity screening = new ScreeningEntity(null,movie, room, "now", Collections.emptyList());
        ScreeningDT screeningDT = new ScreeningDT();

        ScreeningEntity screeningEntity = new ScreeningEntity();
        screeningEntity.setRoom(room);

        BookEntity booked = new BookEntity(null,screening,"brutus","3,3");
        List<BookEntity> bookedList = List.of(booked);

        when(screeningTool.getScreening(screeningDT)).thenReturn(screeningEntity);

        when(bookRepository.findByScreening(screeningEntity)).thenReturn(bookedList);

        bookService.book(screeningDT, "caesar","2,2 3,3");

        String expected = "Seat (3,3) is already taken";
        String actual = outputStreamCaptor.toString().trim();

        assertEquals(expected, actual);
    }

    @Test
    void nonExistentSeat() {

        System.setOut(new PrintStream(outputStreamCaptor));

        RoomEntity room = new RoomEntity(null, "Louie", 12,12);
        MovieEntity movie = new MovieEntity(null, "Spirited Away","animation",120);

        ScreeningEntity screening = new ScreeningEntity(null,movie, room, "now", Collections.emptyList());
        ScreeningDT screeningDT = new ScreeningDT();

        ScreeningEntity screeningEntity = new ScreeningEntity();
        screeningEntity.setRoom(room);

        BookEntity booked = new BookEntity(null,screening,"brutus","3,3");
        List<BookEntity> bookedList = List.of(booked);

        when(screeningTool.getScreening(screeningDT)).thenReturn(screeningEntity);

        when(bookRepository.findByScreening(screeningEntity)).thenReturn(bookedList);

        bookService.book(screeningDT, "caesar","1,13 3,3");

        bookService.book(screeningDT, "caesar","13,1 3,3");

        String expected = "Nonexistent seat\nNonexistent seat";
        String actual = outputStreamCaptor.toString().trim();

        assertEquals(expected, actual);
    }
}