package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.dtos.Seat;
import com.epam.training.ticketservice.entities.BookEntity;
import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.entities.ScreeningEntity;
import com.epam.training.ticketservice.repository.BookRepository;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private ScreeningRepository screeningRepository;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;

    @Override
    public void book(ScreeningDT screeningDT, String username, String seats) {

        List<Seat> seatList = Arrays.stream(seats.split(" ")).map(Seat::new
        ).collect(Collectors.toList());

        RoomEntity room = roomRepository.findByName(screeningDT.getRoomName());
        MovieEntity movie = movieRepository.findByTitle(screeningDT.getMovieName());
        ScreeningEntity screening = screeningRepository
                .findByMovieAndRoomAndScreeningStart(movie, room, screeningDT.getScreeningStart());

        List<BookEntity> books = bookRepository
                .findByScreening(screening);
        List<Seat> taken = books.stream()
                .map(book -> new Seat(book.getSeats()))
                .collect(Collectors.toList());
        for (Seat seat: seatList) {
            if (room.getChairColumns() < seat.getColumn() || room.getChairRows() < seat.getRow()) {
                System.out.println("Nonexistent seat");
                return;
            }
            for (Seat takenSeat: taken) {
                if (seat.equals(takenSeat)) {
                    System.out.printf("Seat %s is already taken\n",formatSeats(takenSeat.toString()));
                    return;
                }
            }
        }
        bookRepository.save(new BookEntity(null,screening,username,seats));
        String booked = formatSeats(seats);
        System.out.printf("Seats booked: %s; the price for this booking is %s HUF\n",
                booked, seats.split(" ").length * 1500);
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
                        1500 * book.getSeats().split(" ").length);
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
}
