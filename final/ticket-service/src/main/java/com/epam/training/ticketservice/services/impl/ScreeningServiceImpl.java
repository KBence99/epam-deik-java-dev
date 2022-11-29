package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.entities.ScreeningEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.services.ScreeningService;
import com.epam.training.ticketservice.utility.DateParser;
import com.epam.training.ticketservice.utility.ScreeningTool;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringJoiner;

@Component
@AllArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private ScreeningRepository screeningRepository;

    private MovieRepository movieRepository;

    private RoomRepository roomRepository;

    private ScreeningTool screeningTool;

    @Override
    public void addScreening(ScreeningDT screeningDT) {
        ScreeningEntity entity = new ScreeningEntity();
        MovieEntity movie = movieRepository.findByTitle(screeningDT.getMovieName()).orElseThrow(() -> {
            throw new RuntimeException("No movie by this title");
        });
        RoomEntity room = roomRepository.findByName(screeningDT.getRoomName()).orElseThrow(() -> {
            throw new RuntimeException("No room by this name found");
        });;

        entity.setScreeningStart(screeningDT.getScreeningStart());
        entity.setMovie(movie);
        entity.setRoom(room);

        List<ScreeningEntity> potentialCrashes = screeningRepository.findByRoom(room);

        for (ScreeningEntity screening: potentialCrashes) {
            if (DateParser.isClashing(screening.getScreeningStart(),
                    entity.getScreeningStart(), screening.getMovie().getLengthInMin(), movie.getLengthInMin())) {
                System.out.println("There is an overlapping screening");
                return;
            } else {
                if (DateParser.isInBreak(screening.getScreeningStart(),
                        entity.getScreeningStart(), screening.getMovie().getLengthInMin(), movie.getLengthInMin())) {
                    System.out.println("This would start in the break period after another screening in this room");
                    return;
                }
            }
        }

        screeningRepository.save(entity);
    }

    @Override
    public void deleteScreening(ScreeningDT screeningDT) {
        ScreeningEntity screening = screeningTool.getScreening(screeningDT);
        screeningRepository.delete(screening);
    }

    @Override
    public String listScreenings() {
        List<ScreeningEntity> screenings = screeningRepository.findAll();

        if (screenings.size() == 0) {
            return "There are no screenings";
        }
        StringJoiner joiner = new StringJoiner("\n");

        for (ScreeningEntity screening:screenings) {
            MovieEntity movie = screening.getMovie();
            RoomEntity room = screening.getRoom();
            joiner.add(String.format("%s (%s, %d minutes), screened in room %s, at %s",
                    movie.getTitle(), movie.getGenre(), movie.getLengthInMin(),
                    room.getName(), screening.getScreeningStart()));
        }

        return joiner.toString();
    }
}
