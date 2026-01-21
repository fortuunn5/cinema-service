package org.example.cinemaservice.store.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.store.dto.CategoryDto;
import org.example.cinemaservice.store.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getCategoryById(id));
    }
}
