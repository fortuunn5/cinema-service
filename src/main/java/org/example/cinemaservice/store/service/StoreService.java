package org.example.cinemaservice.store.service;

import jakarta.annotation.Nullable;
import org.example.cinemaservice.store.dto.CategoryDto;
import org.example.cinemaservice.store.dto.ProductDto;
import org.example.cinemaservice.store.dto.PurchaseDto;

import java.util.Date;
import java.util.List;

public interface StoreService {
    CategoryDto createCategory(CategoryDto newCategory);

    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getAllCategories(@Nullable String name);

    CategoryDto updateCategory(CategoryDto upCategory);

    void deleteCategoryById(Long id);


    ProductDto createProduct(ProductDto newProduct);

    ProductDto getProductById(Long id);

    List<ProductDto> getAllProducts(@Nullable String name, @Nullable Long categoryId, @Nullable Boolean idDeleted);

    ProductDto updateProduct(ProductDto upProduct);

    void deleteProductById(Long id);

    int archiveProdctsByIdList(List<Long> idList);

    int restoreProdctsByIdList(List<Long> idList);

    PurchaseDto createPurchase(PurchaseDto newPurchase);

    PurchaseDto getPurchaseById(Long id);

    List<PurchaseDto> getAllPurchases(@Nullable Date fromPaymentDate,
                                      @Nullable Date toPaymentDate,
                                      @Nullable Integer fromTotalPrice,
                                      @Nullable Integer toTotalPrice,
                                      @Nullable List<Long> productIds);

    PurchaseDto updatePurchase(PurchaseDto upPurchase);

    void deletePurchaseById(Long id);
}
