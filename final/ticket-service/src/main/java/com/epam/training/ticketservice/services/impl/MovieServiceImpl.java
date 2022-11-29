package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.StringJoiner;

@Component
@AllArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    @Override
    public void addMovie(MovieEntity entity) {
        movieRepository.save(entity);
    }

    @Override
    public void updateMovie(MovieEntity entity) {
        MovieEntity movie = movieRepository.findByTitle(entity.getTitle()).orElseThrow(() -> {
            throw new RuntimeException("No movie by title");
        });
        movie.setGenre(entity.getGenre());
        movie.setLengthInMin(entity.getLengthInMin());
    }

    @Override
    public void deleteMovie(String name) {
        movieRepository.deleteByTitle(name);
    }

    @Override
    public String listMovies() {
        List<MovieEntity> movies = movieRepository.findAll();

        if (movies.size() == 0) {
            return "There are no movies at the moment";
        }

        StringJoiner joiner = new StringJoiner("\n");

        for (MovieEntity movie:movies) {
            joiner.add(String.format("%s (%s, %d minutes)",
                    movie.getTitle(), movie.getGenre(), movie.getLengthInMin()));
        }

        return joiner.toString();
    }
}
