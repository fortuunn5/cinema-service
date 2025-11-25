package org.example.cinemaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Email(message = "Email not correct")
    private String contactEmail;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Min(0)
    private int price;

//    @Column(nullable = false)
//    @Min(1)
//    @Max(5)
//    private int seatCount;

    @OneToMany
    @Column(nullable = false)
    private List<Seat> seats;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Session session;

    public int getSeatsCount() {
        return seats.size();
    }
}
