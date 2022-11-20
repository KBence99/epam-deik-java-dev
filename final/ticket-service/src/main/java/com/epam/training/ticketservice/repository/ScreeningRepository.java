package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.entities.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<ScreeningEntity,Long> {

    ScreeningEntity findByMovieAndRoomAndScreeningStart(MovieEntity movie, RoomEntity room, String screeningStart);

    List<ScreeningEntity> findByRoom(RoomEntity room);

}
