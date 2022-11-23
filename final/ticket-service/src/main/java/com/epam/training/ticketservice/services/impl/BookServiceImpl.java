package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.dtos.Seat;
import com.epam.training.ticketservice.repository.BookRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.PriceComponentRepository;

import com.epam.training.ticketservice.services.BookService;

import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.entities.BookEntity;
import com.epam.training.ticketservice.entities.ScreeningEntity;

import com.epam.training.ticketservice.utility.ScreeningTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private PriceComponentRepository priceComponentRepository;
    private ScreeningTool screeningTool;

    @Override
    public void book(ScreeningDT screeningDT, String username, String seats) {

        List<Seat> seatList = Arrays
                .stream(seats.split(" ")).map(Seat::new).collect(Collectors.toList());

        ScreeningEntity screening = screeningTool.getScreening(screeningDT);

        if (isSeatTaken(seatList, screening)) {
            return;
        }

        int basePrice = priceComponentRepository.findByName("Base").getPrice();
        int seatsCount = seats.split(" ").length;

        BookEntity book = new BookEntity(null,screening,username,seats);
        book.setPrice((basePrice + screeningTool.getScreeningPrice(screeningDT)) * seatsCount);

        bookRepository.save(book);
        String booked = formatSeats(seats);
        System.out.printf("Seats booked: %s; the price for this booking is %s HUF\n",
                booked, book.getPrice());
    }

    @Override
    public String getBooking(String userName) {
        List<BookEntity> books = bookRepository.findByUsername(userName);

        if (books.size() != 0) {

            StringJoiner ticketJoiner = new StringJoiner("\n");

            for (BookEntity book: books) {

                ScreeningEntity screening = book.getScreening();
                MovieEntity movie = screening.getMovie();
                RoomEntity room = screening.getRoom();

                String seats = formatSeats(book.getSeats());
                String booking = String.format("Seats %s on %s in room %s starting at %s for %s HUF",
                        seats, movie.getTitle(), room.getName(), screening.getScreeningStart(),
                        book.getPrice());
                ticketJoiner.add(booking);
            }

            return ticketJoiner.toString();
        }
        return "";
    }

    private String formatSeats(String seats) {
        StringJoiner joiner = new StringJoiner(", ");
        for (String seat:seats.split(" ")) {
            joiner.add("(" + seat + ")");
        }
        return joiner.toString();
    }

    private boolean isSeatTaken(List<Seat> seatList, ScreeningEntity screening) {

        RoomEntity room = screening.getRoom();

        List<BookEntity> books = bookRepository
                .findByScreening(screening);
        List<Seat> taken = books.stream()
                .map(book -> new Seat(book.getSeats()))
                .collect(Collectors.toList());
        for (Seat seat: seatList) {
            if (room.getChairColumns() < seat.getColumn() || room.getChairRows() < seat.getRow()) {
                System.out.println("Nonexistent seat");
                return true;
            }
            for (Seat takenSeat: taken) {
                if (seat.equals(takenSeat)) {
                    System.out.printf("Seat %s is already taken\n",formatSeats(takenSeat.toString()));
                    return true;
                }
            }
        }
        return false;
    }
}
