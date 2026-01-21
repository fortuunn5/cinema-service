package org.example.cinemaservice.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPurchaseDto {
    private Long productId;
    private Long purchaseId;
    private Integer count;
    private Integer productPrice;
}
