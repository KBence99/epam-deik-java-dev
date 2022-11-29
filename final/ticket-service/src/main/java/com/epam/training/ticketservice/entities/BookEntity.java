package com.epam.training.ticketservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "BookTable")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "screeningId", referencedColumnName = "id")
    private ScreeningEntity screening;

    private String username;

    private String seats;

    private int price;

    public BookEntity(Long id, ScreeningEntity screening, String username, String seats) {
        this.id = id;
        this.screening = screening;
        this.username = username;
        this.seats = seats;
    }
}
