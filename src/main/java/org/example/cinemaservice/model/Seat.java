package org.example.cinemaservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueSeatAndHall", columnNames = {"row", "number", "hall_id"})})
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int row;

    @Column(nullable = false)
    private int number;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "hall_id")
    private Hall hall;

    public Seat(Long id) {
        this.id = id;
    }
}
