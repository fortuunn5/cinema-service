package org.example.cinemaservice.service;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.RoleDto;
import org.example.cinemaservice.dto.UserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        RoleDto role = roleService.getRoleById(user.getRoleId());
        return User.builder()
                .username(user.getContactEmail())
                .password(user.getPassword())
                .authorities(role.getRole())
                .build();
    }
}
