package org.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cinemaservice.model.Reservation;
import org.example.cinemaservice.model.Role;
import org.example.cinemaservice.model.User;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String contactEmail;
    private String password;
    private Long roleId;
    private List<Long> reservationsId;

    public User toEntity() {
        User user = new User();
        user.setId(id);
        user.setContactEmail(contactEmail);
        user.setPassword(password);
        Role role = new Role(roleId);
        user.setRole(role);
        List<Reservation> reservations = new ArrayList<>();
        if (reservationsId != null) {
            for (Long id : reservationsId) {
                Reservation reservation = new Reservation(id);
                reservations.add(reservation);
            }
        }
        user.setReservations(reservations);
        return user;
    }

    public static UserDto ofEntity(User user) {
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setContactEmail(user.getContactEmail());
            userDto.setPassword(user.getPassword());
            userDto.setRoleId(user.getRole().getId());
            List<Long> reservationsId = new ArrayList<>();
            if (user.getReservations() != null) {
                for (Reservation reservation : user.getReservations()) {
                    reservationsId.add(reservation.getId());
                }
            }
            userDto.setReservationsId(reservationsId);
            return userDto;
        }
        return null;
    }
}
