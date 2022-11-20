package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    MovieEntity findByTitle(String title);

    void deleteByTitle(String title);
}
