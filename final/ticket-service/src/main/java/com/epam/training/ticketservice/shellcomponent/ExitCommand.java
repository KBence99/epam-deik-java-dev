package com.epam.training.ticketservice.shellcomponent;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class ExitCommand implements Quit.Command {

    @ShellMethod(value = "Exit the shell.", key = "exit")
    public void quit() {
        System.exit(0);
    }
}
