package com.epam.training.ticketservice.utility;

import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.entities.PriceComponentEntity;
import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.entities.ScreeningEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreeningToolTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    ScreeningTool screeningTool;

    private MovieEntity movie;
    private RoomEntity room;
    private ScreeningEntity screening;

    @BeforeEach
    private void setup(){
        PriceComponentEntity translation = new PriceComponentEntity(null, "Translation fee", 500);
        PriceComponentEntity airCooling = new PriceComponentEntity(null, "Air cooling", 1000);
        PriceComponentEntity threeD = new PriceComponentEntity(null, "3D glasses", 750);

        movie = new MovieEntity(null, "Spirited Away", "animation", 120, List.of(translation));
        room = new RoomEntity(null, "Louie", 12, 12, List.of(airCooling));
        screening = new ScreeningEntity(null,movie, room, "now", List.of(threeD));

        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(Optional.of(movie));
        when(roomRepository.findByName(room.getName())).thenReturn(Optional.of(room));
        when(screeningRepository.findByMovieAndRoomAndScreeningStart(movie, room, "now")).thenReturn(Optional.of(screening));
    }

    @Test
    void getScreening() {
        ScreeningEntity actual = screeningTool.getScreening(new ScreeningDT(movie.getTitle(), room.getName(), "now"));
        assertEquals(screening, actual);
    }

    @Test
    void getScreeningPrice() {

        Integer actual = screeningTool.getScreeningPrice(new ScreeningDT(movie.getTitle(), room.getName(), "now"));

        assertEquals(actual,2250);
    }
}