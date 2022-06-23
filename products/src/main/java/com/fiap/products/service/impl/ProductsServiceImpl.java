package com.fiap.products.service.impl;

import com.fiap.products.helper.ProductsHelper;
import com.fiap.products.model.dto.ProductsDTO;
import com.fiap.products.model.entities.Products;
import com.fiap.products.repository.ProductsRepository;
import com.fiap.products.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.fiap.products.helper.ProductsHelper.builderProducts;
import static com.fiap.products.helper.ProductsHelper.builderProductsDTO;
import static com.fiap.products.helper.ProductsHelper.builderUpdateProductsDTO;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public List<ProductsDTO> listAll() {
        final List<Products> productsList = productsRepository.findAll();
        return productsList.stream()
                .map(ProductsHelper::builderProductsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductsDTO findProductById(Long id) {
        final Products products = productsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found"));
        return builderProductsDTO(products);
    }

    @Override
    public ProductsDTO create(ProductsDTO productsDTO) {
        productsRepository.save(builderProducts(productsDTO));
        return productsDTO;
    }

    @Override
    public ProductsDTO update(Long id, ProductsDTO productsDTO) {
        final Products products = productsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found"));
        final Products productsUpdated = builderUpdateProductsDTO(products , productsDTO);
        productsRepository.save(productsUpdated);
        return builderProductsDTO(productsUpdated);
    }

    @Override
    public void deleteProduct(Long id) {
        productsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found"));
        productsRepository.deleteById(id);
    }

}
