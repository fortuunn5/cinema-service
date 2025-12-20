package org.example.cinemaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DisplayFormat displayFormat;

    @Column(nullable = false)
    @Min(0)
    private int price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Hall hall;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Date endDate;

    public Session(Long id) {
        this.id = id;
    }
}
