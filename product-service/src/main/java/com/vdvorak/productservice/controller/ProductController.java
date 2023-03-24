package com.vdvorak.productservice.controller;

import com.vdvorak.productservice.dto.ProductDto;
import com.vdvorak.productservice.service.ProductService;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("all")
    Flux<ProductDto> getAll(){
        return service.getAll();
    }

    @GetMapping("price-range")
     Flux<ProductDto> getProductByPriceRange(@RequestParam("min") int min, @RequestParam("max") int max){
        return service.getproductByPriceRange(min, max);
    }

    @GetMapping("{id}")
    Mono<ResponseEntity<ProductDto>> getProductByIdAll(@PathVariable String id){
        this.simulateRandomException();
        return service.getproductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PostMapping
    public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDtoMono){
        return this.service.insertProduct(productDtoMono);
    }


    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id , @RequestBody Mono<ProductDto> productDtoMono){
        return this.service.updateProduct(id,productDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
        return this.service.deleteProduct(id);
    }


    private void simulateRandomException(){
        int nextInt= ThreadLocalRandom.current().nextInt(1,10);
        if(nextInt > 5){
            throw new RuntimeException("something is wrong");
        }

    }


}
