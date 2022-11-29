package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.entities.MovieEntity;

public interface MovieService {

    public void addMovie(MovieEntity entity);

    public void updateMovie(MovieEntity entity);

    public void deleteMovie(String name);

    public String listMovies();
}
