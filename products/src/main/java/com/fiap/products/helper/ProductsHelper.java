package com.fiap.products.helper;


import com.fiap.products.model.dto.ProductsDTO;
import com.fiap.products.model.entities.Products;

public class ProductsHelper {

    public static Products builderProducts(final ProductsDTO productsDTO){
        return Products.builder()
                .productName(productsDTO.getProductName())
                .description(productsDTO.getDescription())
                .build();
    }

    public static ProductsDTO builderProductsDTO(final Products products){
        return ProductsDTO.builder()
                .id(products.getId())
                .productName(products.getProductName())
                .description(products.getDescription())
                .build();
    }

    public static Products builderUpdateProductsDTO(final Products products, final ProductsDTO productsDTO){
        return Products.builder()
                .id(products.getId())
                .productName(productsDTO.getProductName())
                .description(productsDTO.getDescription())
                .build();
    }



}
