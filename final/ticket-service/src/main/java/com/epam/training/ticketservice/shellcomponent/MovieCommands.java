package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.UserHandler;
import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@AllArgsConstructor
@ShellComponent
public class MovieCommands {

    private UserHandler account;
    private MovieService movieService;

    @ShellMethod(value = "Adds a movie",key = "create movie")
    public void addMovie(String title, String genre, Integer lengthInMin) {
        if (account.isAdmin()) {
            movieService.addMovie(new MovieEntity(null,title,genre,lengthInMin));
        }
    }

    @ShellMethod(value = "Updates a movie",key = "update movie")
    public void updateMovie(String title, String genre, Integer lengthInMin) {
        if (account.isAdmin()) {
            movieService.updateMovie(new MovieEntity(null,title,genre,lengthInMin));
        }
    }

    @ShellMethod(value = "Delete a movie",key = "delete movie")
    public void deleteMovie(String title) {
        if (account.isAdmin()) {
            movieService.deleteMovie(title);
        }
    }

    @ShellMethod(value = "Lists all movies",key = "list movies")
    public String listMovie() {
        return movieService.listMovies();
    }
}
