package org.example.cinemaservice.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
    private Long id;
    private String paymentType;
    private Date paymentDate;
    private Integer totalPrice;
    private List<ProductPurchaseDto> productPurchaseList = new ArrayList<>();
}
