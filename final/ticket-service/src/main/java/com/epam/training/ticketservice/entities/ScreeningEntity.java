package com.epam.training.ticketservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ScreeningTable")
public class ScreeningEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "movieName", referencedColumnName = "title")
    private MovieEntity movie;

    @OneToOne()
    @JoinColumn(name = "roomName", referencedColumnName = "name")
    private RoomEntity room;
    private String screeningStart;

    @OneToMany
    private List<PriceComponentEntity> prices = new ArrayList<>();
}
