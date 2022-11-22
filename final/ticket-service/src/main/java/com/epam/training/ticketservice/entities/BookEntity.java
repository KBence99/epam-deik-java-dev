package com.epam.training.ticketservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Data
@AllArgsConstructor
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
}
