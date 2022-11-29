package com.epam.training.ticketservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MovieTable")
public class MovieEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private Integer lengthInMin;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PriceComponentEntity> prices = new ArrayList<>();

    public MovieEntity(Long id, String title, String genre, Integer lengthInMin) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.lengthInMin = lengthInMin;
    }
}
