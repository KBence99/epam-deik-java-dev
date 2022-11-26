package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.entities.PriceComponentEntity;
import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.entities.ScreeningEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.PriceComponentRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.services.PriceComponentService;
import com.epam.training.ticketservice.utility.ScreeningTool;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
@NoArgsConstructor
public class PriceComponentServiceImpl implements PriceComponentService {

    private PriceComponentRepository priceRepository;
    private RoomRepository roomRepository;
    private MovieRepository movieRepository;
    private ScreeningTool screeningTool;

    @Override
    public void createPrice(String name, Integer value) {
        priceRepository.save(new PriceComponentEntity(null, name, value));
    }

    @Override
    public void attachToRoom(String priceName, String roomName) {
        RoomEntity room = roomRepository.findByName(roomName);
        room.getPrices().add(priceRepository.findByName(priceName));
    }

    @Override
    public void attachToMovie(String priceName, String title) {
        MovieEntity movie = movieRepository.findByTitle(title);
        movie.getPrices().add(priceRepository.findByName(priceName));
    }

    @Override
    public void attachScreening(String priceName, ScreeningDT screeningDT) {
        ScreeningEntity screening = screeningTool.getScreening(screeningDT);
        screening.getPrices().add(priceRepository.findByName(priceName));
    }

    @Override
    public void setBasePrice(Integer price) {
        PriceComponentEntity basePrice = priceRepository.findByName("Base");
        basePrice.setPrice(price);
    }

    @Override
    public String showPrice(ScreeningDT screeningDT, String seats) {

        int seatsCount = seats.split(" ").length;
        int price = (screeningTool.getScreeningPrice(screeningDT) + priceRepository.findByName("Base").getPrice())
                * seatsCount;

        return String.format("The price for this booking would be %s HUF",price);
    }
}
