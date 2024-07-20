package com.stock.control.app.rest.controller;

import com.stock.control.app.domain.service.ProductService;
import com.stock.control.app.rest.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        try {
            productService.createProduct(productDto);
            return new ResponseEntity<>(productDto, HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(productDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        try {
            productService.updateProduct(productDto);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(productDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductDto> getProduct(@RequestParam String sku) {
        try {
            ProductDto productDtoReturned = productService.getProductBySku(sku);
            return new ResponseEntity<>(productDtoReturned, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<ProductDto> deleteProduct(@RequestParam String sku) {
        try {
            productService.deleteProductBySku(sku);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
