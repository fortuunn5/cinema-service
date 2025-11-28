package org.example.cinemaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//todo: добавить поле "доступен ли"
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    @Min(2)
    private int capacity;

    @OneToMany(mappedBy = "hall", fetch = FetchType.LAZY)
    private List<Seat> seats;

    public Hall(Long id) {
        this.id = id;
    }
}
