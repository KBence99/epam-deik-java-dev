package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.entities.PriceComponentEntity;
import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.entities.ScreeningEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.PriceComponentRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.services.PriceComponentService;
import com.epam.training.ticketservice.utility.ScreeningTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
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
        RoomEntity room = roomRepository.findByName(roomName).orElseThrow(() -> {
            throw new RuntimeException("No room by this name found");
        });
        PriceComponentEntity priceComponent = priceRepository.findByName(priceName).orElseThrow(() -> {
            throw new RuntimeException("No price component by this name");
        });
        room.getPrices().add(priceComponent);
    }

    @Override
    public void attachToMovie(String priceName, String title) {
        MovieEntity movie = movieRepository.findByTitle(title).orElseThrow(() -> {
            throw new RuntimeException("No movie by this title");
        });
        PriceComponentEntity priceComponent = priceRepository.findByName(priceName).orElseThrow(() -> {
            throw new RuntimeException("No price component by this name");
        });
        movie.getPrices().add(priceComponent);
    }

    @Override
    public void attachScreening(String priceName, ScreeningDT screeningDT) {
        ScreeningEntity screening = screeningTool.getScreening(screeningDT);
        PriceComponentEntity priceComponent = priceRepository.findByName(priceName).orElseThrow(() -> {
            throw new RuntimeException("No price component by this name");
        });
        screening.getPrices().add(priceComponent);
    }

    @Override
    public void setBasePrice(Integer price) {
        PriceComponentEntity basePrice = priceRepository.findByName("Base").orElseThrow(() -> {
            throw new RuntimeException("No price component by this name");
        });
        basePrice.setPrice(price);
    }

    @Override
    public String showPrice(ScreeningDT screeningDT, String seats) {

        int seatsCount = seats.split(" ").length;
        PriceComponentEntity basePriceComponent = priceRepository.findByName("Base").orElseThrow(() -> {
            throw new RuntimeException("No price component by this name found");
        });
        int price = (screeningTool.getScreeningPrice(screeningDT) + basePriceComponent.getPrice())
                * seatsCount;
        return String.format("The price for this booking would be %s HUF",price);
    }
}
