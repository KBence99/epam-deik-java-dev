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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieService service = new MovieServiceImpl(movieRepository);

    @Test
    void addMovie() {

        MovieEntity movie = new MovieEntity();

        service.addMovie(movie);

        verify(movieRepository).save(movie);
    }

    @Test
    void updateMovie() {

        MovieEntity movie = mock(MovieEntity.class);
        when(movieRepository.findByTitle("Spirited Away")).thenReturn(Optional.of(movie));

        service.updateMovie(new MovieEntity(null,"Spirited Away", "animation", 120));

        verify(movie).setGenre("animation");
        verify(movie).setLengthInMin(120);
    }

    @Test
    void deleteMovie() {

        service.deleteMovie("Spirited away");

        verify(movieRepository).deleteByTitle("Spirited away");
    }

    @Test
    void listMoviesEmpty() {

        when(movieRepository.findAll()).thenReturn(Collections.emptyList());

        String expected = "There are no movies at the moment";
        String actual = service.listMovies();

        assertEquals(expected, actual);
    }

    @Test
    void listMoviesNonEmpty() {

        when(movieRepository.findAll()).thenReturn(List.of(new MovieEntity(null, "Spirited Away", "animation", 120)));

        String expected = "Spirited Away (animation, 120 minutes)";
        String actual = service.listMovies();

        assertEquals(expected, actual);
    }
}