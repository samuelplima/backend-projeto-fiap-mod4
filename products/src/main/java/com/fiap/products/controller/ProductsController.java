package com.fiap.products.controller;

import com.fiap.products.model.dto.ProductsDTO;
import com.fiap.products.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@Transactional
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }


    @GetMapping(value = "/listAll")
    @ResponseBody
    public List<ProductsDTO> findAll() {
        return productsService.listAll();
    }


    @GetMapping("get/{id}")
    @ResponseBody
    public ProductsDTO getProductById(@PathVariable Long id) {
        return productsService.findProductById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ProductsDTO createProduct(@RequestBody ProductsDTO productsDTO) {
        return productsService.create(productsDTO);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ProductsDTO updateProduct(@PathVariable Long id, @RequestBody ProductsDTO productsDTO) {
        return productsService.update(id, productsDTO);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productsService.deleteProduct(id);
    }

}
