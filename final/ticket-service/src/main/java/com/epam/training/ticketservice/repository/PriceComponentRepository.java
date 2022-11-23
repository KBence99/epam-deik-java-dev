package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entities.PriceComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceComponentRepository extends JpaRepository<PriceComponentEntity, Long> {
    PriceComponentEntity findByName(String priceName);
}
