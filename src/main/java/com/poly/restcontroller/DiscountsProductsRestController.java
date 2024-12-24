package com.poly.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.DiscountsProducts;
import com.poly.service.DiscountsProductsService;


@RestController
@RequestMapping("/api")
public class DiscountsProductsRestController {

    @Autowired
    private DiscountsProductsService discountsProductsService;

    @GetMapping("/discount/product/{id}")
    public ResponseEntity<DiscountsProducts> getDiscountProductById(@PathVariable String id) {
        try {
        	DiscountsProducts discountProduct = discountsProductsService.getDiscountsProductsById(id);
            if (discountProduct != null) {
                return ResponseEntity.ok(discountProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
