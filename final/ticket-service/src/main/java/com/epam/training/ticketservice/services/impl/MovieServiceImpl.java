package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.services.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional
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

        StringBuilder list = new StringBuilder();

        for (MovieEntity movie:movies) {
            list.append(String.format("%s (%s, %d minutes)",
                    movie.getTitle(), movie.getGenre(), movie.getLengthInMin()));
        }

        return list.toString().trim();
    }
}
