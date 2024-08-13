package com.stock.control.app.rest.controller;

import com.stock.control.app.domain.service.ProductService;
import com.stock.control.app.rest.dto.ProductRequest;
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
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            productService.createProduct(productRequest);
            return new ResponseEntity<>("Created.", HttpStatus.CREATED);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>("Not created.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequest productRequest) {
        try {
            productService.updateProduct(productRequest);
            return new ResponseEntity<>("Updated.", HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>("Not updated.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductRequest> getProduct(@RequestParam String sku) {
        try {
            return new ResponseEntity<>(productService.getProductBySku(sku), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<String> deleteProduct(@RequestParam String sku) {
        try {
            productService.deleteProductBySku(sku);
            return new ResponseEntity<>("Deleted.",HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>("Not deleted.", HttpStatus.BAD_REQUEST);
        }
    }
}
