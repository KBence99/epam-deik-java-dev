package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.services.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@AllArgsConstructor
@ShellComponent
public class MovieCommands {

    private Account account;

    private MovieService movieService;

    @ShellMethod(value = "Adds a movie",key = "create movie")
    public void addMovie(String title, String genre, Integer lengthInMin) {
        if (!account.isLoggedIn()) {
            return;
        }
        MovieEntity movie = new MovieEntity(null,title,genre,lengthInMin);
        movieService.addMovie(movie);
    }

    @ShellMethod(value = "Updates a movie",key = "update movie")
    public void updateMovie(String title, String genre, Integer lengthInMin) {
        if (!account.isLoggedIn()) {
            return;
        }
        movieService.updateMovie(new MovieEntity(null,title,genre,lengthInMin));
    }

    @ShellMethod(value = "Delete a movie",key = "delete movie")
    public void deleteMovie(String title) {
        if (!account.isLoggedIn()) {
            return;
        }
        movieService.deleteMovie(title);
    }

    @ShellMethod(value = "Lists all movies",key = "list movies")
    public String listMovie() {
        return movieService.listMovies();
    }
}
