package com.fiap.products.service;

import com.fiap.products.model.dto.ProductsDTO;

import java.util.List;

public interface ProductsService {

    List<ProductsDTO> listAll();

    ProductsDTO findProductById(Long id);

    ProductsDTO create(ProductsDTO productsDTO);

    ProductsDTO update(Long id, ProductsDTO productsDTO);

    void deleteProduct(Long id);

}
