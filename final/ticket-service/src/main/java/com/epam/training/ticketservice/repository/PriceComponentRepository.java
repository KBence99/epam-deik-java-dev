package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entities.PriceComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceComponentRepository extends JpaRepository<PriceComponentEntity, Long> {
    Optional<PriceComponentEntity> findByName(String priceName);
}
