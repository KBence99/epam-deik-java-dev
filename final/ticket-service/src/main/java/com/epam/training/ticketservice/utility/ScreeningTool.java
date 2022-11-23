package com.epam.training.ticketservice.utility;

import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.entities.PriceComponentEntity;
import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.entities.ScreeningEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ScreeningTool {

    private MovieRepository movieRepository;
    private RoomRepository roomRepository;

    private ScreeningRepository screeningRepository;

    public ScreeningEntity getScreening(ScreeningDT screeningDT) {

        MovieEntity movie = movieRepository.findByTitle(screeningDT.getMovieName());
        RoomEntity room = roomRepository.findByName(screeningDT.getRoomName());

        ScreeningEntity screening = screeningRepository
                .findByMovieAndRoomAndScreeningStart(movie, room, screeningDT.getScreeningStart());

        return screening;
    }

    public Integer getScreeningPrice(ScreeningDT screeningDT) {

        ScreeningEntity screening = this.getScreening(screeningDT);

        int price = 0;

        MovieEntity movie = screening.getMovie();
        RoomEntity room = screening.getRoom();

        for (PriceComponentEntity priceComponentEntity: screening.getPrices()) {
            price += priceComponentEntity.getPrice();
        }

        for (PriceComponentEntity priceComponentEntity: movie.getPrices()) {
            price += priceComponentEntity.getPrice();
        }

        for (PriceComponentEntity priceComponentEntity: room.getPrices()) {
            price += priceComponentEntity.getPrice();
        }

        return price;

    }
}
