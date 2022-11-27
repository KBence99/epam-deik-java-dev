package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.UserHandler;
import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.services.MovieService;
import com.epam.training.ticketservice.shellcomponent.configurations.CustomCommandClass;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@AllArgsConstructor
@ShellComponent
public class MovieCommands extends CustomCommandClass {

    private MovieService movieService;

    @ShellMethod(value = "Adds a movie",key = "create movie")
    @ShellMethodAvailability(value = "isAdmin")
    public void addMovie(String title, String genre, Integer lengthInMin) {
        movieService.addMovie(new MovieEntity(null,title,genre,lengthInMin));
    }

    @ShellMethod(value = "Updates a movie",key = "update movie")
    @ShellMethodAvailability(value = "isAdmin")
    public void updateMovie(String title, String genre, Integer lengthInMin) {
        movieService.updateMovie(new MovieEntity(null,title,genre,lengthInMin));
    }

    @ShellMethod(value = "Delete a movie",key = "delete movie")
    @ShellMethodAvailability(value = "isAdmin")
    public void deleteMovie(String title) {
        movieService.deleteMovie(title);
    }

    @ShellMethod(value = "Lists all movies",key = "list movies")
    public String listMovie() {
        return movieService.listMovies();
    }
}
