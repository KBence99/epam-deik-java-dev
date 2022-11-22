package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entities.BookEntity;
import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.entities.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByScreening(ScreeningEntity screening);

    List<BookEntity> findByUsername(String userName);
}
