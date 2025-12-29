package org.example.cinemaservice.utils;

import org.apache.commons.lang3.StringUtils;
import org.example.cinemaservice.dto.RoleDto;
import org.example.cinemaservice.dto.UserDto;
import org.example.cinemaservice.service.RoleService;
import org.example.cinemaservice.service.UserService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static boolean hasRole(String role) {
        if (StringUtils.isEmpty(role)) {
            throw new IllegalArgumentException("Role cannot be empty");
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Current user is not authenticated");
        }

        return authentication.getAuthorities().stream()
                .anyMatch(x -> x.getAuthority().equalsIgnoreCase(role));
    }

    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Current user is not authenticated");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public static Long getCurrentUserId(UserService userService) {
        UserDto user = getCurrentUser(userService);
        if (user != null) {
            return user.getId();
        }
        throw new IllegalArgumentException("User not found");
    }

    public static UserDto getCurrentUser(UserService userService) {
        return userService.getUserByContactEmail(getCurrentUserEmail());
    }

    public static RoleDto getCurrentRole(UserService userService, RoleService roleService) {
        UserDto user = getCurrentUser(userService);
        if (user != null) {
            return roleService.getRoleById(user.getRoleId());
        }
        throw new IllegalArgumentException("User not found");
    }
}
