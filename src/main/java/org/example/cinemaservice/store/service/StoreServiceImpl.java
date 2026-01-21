package org.example.cinemaservice.store.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.cinemaservice.store.dto.CategoryDto;
import org.example.cinemaservice.store.dto.ProductDto;
import org.example.cinemaservice.store.dto.PurchaseDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final RestClient restClient;

    @Override
    public CategoryDto createCategory(CategoryDto newCategory) {
        return restClient.post()
                .uri("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .body(newCategory)
                .retrieve()
                .body(CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return restClient.get()
                .uri("/categories/{id}", id)
                .retrieve()
                .body(CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories(@Nullable String name) {
        return restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/categories");
                    if (!StringUtils.isBlank(name)) {
                        uriBuilder.queryParam("name", name);
                    }
                    return uriBuilder.build();
                })
                .retrieve()
                .body(List.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto upCategory) {
        return restClient.put()
                .uri("/categories")
                .body(upCategory)
                .retrieve()
                .body(CategoryDto.class);
    }

    @Override
    public void deleteCategoryById(Long id) {
        restClient.delete()
                .uri("/categories/{id}", id)
                .retrieve()
                .body(Void.class);
    }

    @Override
    public ProductDto createProduct(ProductDto newProduct) {
        return restClient.post()
                .uri("/products")
                .body(newProduct)
                .retrieve()
                .body(ProductDto.class);
    }

    @Override
    public ProductDto getProductById(Long id) {
        return restClient.get()
                .uri("products/{id}", id)
                .retrieve()
                .body(ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProducts(@Nullable String name, @Nullable Long categoryId, @Nullable Boolean idDeleted) {
        return restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/products");
                    if (!StringUtils.isBlank(name)) {
                        uriBuilder.queryParam("name", name);
                    }
                    if (categoryId != null) {
                        uriBuilder.queryParam("categoryId", categoryId);
                    }
                    if (idDeleted != null) {
                        uriBuilder.queryParam("idDeleted", idDeleted);
                    }
                    return uriBuilder.build();
                })
                .retrieve()
                .body(List.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto upProduct) {
        return restClient.put()
                .uri("/products")
                .body(upProduct)
                .retrieve()
                .body(ProductDto.class);
    }

    @Override
    public void deleteProductById(Long id) {
        restClient.delete()
                .uri("/products/{id}", id)
                .retrieve()
                .body(Void.class);
    }

    @Override
    public int archiveProdctsByIdList(List<Long> idList) {
        return restClient.put()
                .uri(uriBuilder -> {
                    return uriBuilder.path("/products/archive")
                            .queryParam("idList", idList)
                            .build();
                })
                .retrieve()
                .body(Integer.class);
    }

    @Override
    public int restoreProdctsByIdList(List<Long> idList) {
        return restClient.put()
                .uri(uriBuilder -> {
                    return uriBuilder.path("/products/restore")
                            .queryParam("idList", idList)
                            .build();
                })
                .retrieve()
                .body(Integer.class);
    }

    @Override
    public PurchaseDto createPurchase(PurchaseDto newPurchase) {
        return restClient.post()
                .uri("/purchases")
                .body(newPurchase)
                .retrieve()
                .body(PurchaseDto.class);
    }

    @Override
    public PurchaseDto getPurchaseById(Long id) {
        return restClient.get()
                .uri("/purchases/{id}", id)
                .retrieve()
                .body(PurchaseDto.class);
    }

    @Override
    public List<PurchaseDto> getAllPurchases(@Nullable Date fromPaymentDate,
                                             @Nullable Date toPaymentDate,
                                             @Nullable Integer fromTotalPrice,
                                             @Nullable Integer toTotalPrice,
                                             @Nullable List<Long> productIds) {
        return restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/purchases");
                    if (fromPaymentDate != null) {
                        uriBuilder.queryParam("fromPaymentDate", fromPaymentDate);
                    }
                    if (toPaymentDate != null) {
                        uriBuilder.queryParam("toPaymentDate", toPaymentDate);
                    }
                    if (fromTotalPrice != null) {
                        uriBuilder.queryParam("fromTotalPrice", fromTotalPrice);
                    }
                    if (toTotalPrice != null) {
                        uriBuilder.queryParam("toTotalPrice", toTotalPrice);
                    }
                    if (productIds != null) {
                        uriBuilder.queryParam("productIds", productIds);
                    }
                    return uriBuilder.build();
                })
                .retrieve()
                .body(List.class);

    }

    @Override
    public PurchaseDto updatePurchase(PurchaseDto upPurchase) {
        return restClient.put()
                .uri("purchases")
                .body(upPurchase)
                .retrieve()
                .body(PurchaseDto.class);
    }

    @Override
    public void deletePurchaseById(Long id) {
        restClient.delete()
                .uri("/purchases/{id}", id)
                .retrieve()
                .body(Void.class);
    }
}
