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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceComponentServiceImplTest {

    @Mock
    private PriceComponentRepository priceRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private ScreeningTool screeningTool;

    @InjectMocks
    private PriceComponentService priceComponentService = new PriceComponentServiceImpl(priceRepository,roomRepository,movieRepository,screeningTool);

    @Test
    void createPrice() {
        PriceComponentEntity price = new PriceComponentEntity(null, "Cooling", 1000);
        priceComponentService.createPrice("Cooling",1000);

        verify(priceRepository).save(price);
    }

    @Test
    void attachToRoom() {
        PriceComponentEntity price = new PriceComponentEntity(null, "cooling",1000);
        when(priceRepository.findByName("cooling")).thenReturn(Optional.of(price));

        RoomEntity room = mock(RoomEntity.class);
        when(roomRepository.findByName("Louie")).thenReturn(Optional.of(room));

        List<PriceComponentEntity> list = mock(ArrayList.class);
        when(room.getPrices()).thenReturn(list);

        priceComponentService.attachToRoom("cooling","Louie");
        verify(room.getPrices()).add(price);
    }

    @Test
    void attachToMovie() {
        PriceComponentEntity price = new PriceComponentEntity(null, "cooling",1000);
        when(priceRepository.findByName("cooling")).thenReturn(Optional.of(price));

        MovieEntity movie  = mock(MovieEntity.class);
        when(movieRepository.findByTitle("Spirited Away")).thenReturn(Optional.of(movie));

        List<PriceComponentEntity> list = mock(ArrayList.class);
        when(movie.getPrices()).thenReturn(list);

        priceComponentService.attachToMovie("cooling","Spirited Away");
        verify(movie.getPrices()).add(price);
    }

    @Test
    void attachScreening() {
        PriceComponentEntity price = new PriceComponentEntity(null, "cooling",1000);
        when(priceRepository.findByName("cooling")).thenReturn(Optional.of(price));

        ScreeningDT dt = new ScreeningDT();

        ScreeningEntity screening = mock(ScreeningEntity.class);
        when(screeningTool.getScreening(dt)).thenReturn(screening);

        List<PriceComponentEntity> list = mock(ArrayList.class);
        when(screening.getPrices()).thenReturn(list);

        priceComponentService.attachScreening("cooling",dt);
        verify(screening.getPrices()).add(price);
    }

    @Test
    void setBasePrice() {
        PriceComponentEntity base = mock(PriceComponentEntity.class);
        when(priceRepository.findByName("Base")).thenReturn(Optional.of(base));

        priceComponentService.setBasePrice(1200);
        verify(base).setPrice(1200);
    }

    @Test
    void showPrice() {
        ScreeningDT dt = new ScreeningDT("Spirited Away", "Louie", "now");

        when(screeningTool.getScreeningPrice(dt)).thenReturn(3500);

        when(priceRepository.findByName("Base")).thenReturn(Optional.of(new PriceComponentEntity(null,"Base",1500)));

        String actual = priceComponentService.showPrice(dt,"2,2");
        String expected = "The price for this booking would be 5000 HUF";

        assertEquals(expected, actual);
    }
}