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
@Table(name = "RoomTable")
public class RoomEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer chairRows;
    private Integer chairColumns;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PriceComponentEntity> prices = new ArrayList<>();

    public RoomEntity(Long id, String name, Integer chairRows, Integer chairColumns) {
        this.id = id;
        this.name = name;
        this.chairRows = chairRows;
        this.chairColumns = chairColumns;
    }
}
