package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.services.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    MovieRepository repository;

    @InjectMocks
    MovieService service = new MovieServiceImpl(repository);

    @Test
    void addMovie() {

        MovieEntity movie = new MovieEntity();

        service.addMovie(movie);

        verify(repository).save(movie);
    }

    @Test
    void updateMovie() {

        MovieEntity movie = mock(MovieEntity.class);
        when(repository.findByTitle("Spirited Away")).thenReturn(movie);

        service.updateMovie(new MovieEntity(null,"Spirited Away", "animation", 120));

        verify(movie).setGenre("animation");
        verify(movie).setLengthInMin(120);
    }

    @Test
    void deleteMovie() {

        service.deleteMovie("Spirited away");

        verify(repository).deleteByTitle("Spirited away");
    }

    @Test
    void listMoviesEmpty() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        String expected = "There are no movies at the moment";
        String actual = service.listMovies();

        assertEquals(expected, actual);
    }

    @Test
    void listMoviesNonEmpty() {

        when(repository.findAll()).thenReturn(List.of(new MovieEntity(null, "Spirited Away", "animation", 120)));

        String expected = "Spirited Away (animation, 120 minutes)";
        String actual = service.listMovies();

        assertEquals(expected, actual);
    }
}