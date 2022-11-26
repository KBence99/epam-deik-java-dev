package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.services.MovieService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.StringJoiner;

@Component
@AllArgsConstructor
@Transactional
@NoArgsConstructor
public class MovieServiceImpl implements MovieService {

    private MovieRepository repository;

    @Override
    public void addMovie(MovieEntity entity) {
        repository.save(entity);
    }

    @Override
    public void updateMovie(MovieEntity entity) {
        MovieEntity movie = repository.findByTitle(entity.getTitle());
        movie.setGenre(entity.getGenre());
        movie.setLengthInMin(entity.getLengthInMin());
    }

    @Override
    public void deleteMovie(String name) {
        repository.deleteByTitle(name);
    }

    @Override
    public String listMovies() {
        List<MovieEntity> movies = repository.findAll();

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
