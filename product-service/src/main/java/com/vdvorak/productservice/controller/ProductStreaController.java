package com.vdvorak.productservice.controller;

import com.vdvorak.productservice.dto.ProductDto;
import com.vdvorak.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductStreaController {

    @Autowired
    private Flux<ProductDto> flux;

    @Autowired
    private ProductService productService;

//http://localhost:8091/product/stream
   // @GetMapping() //not work if empty event stream
   /* @GetMapping(value = "stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<ProductDto> getProductUpdates(){
        return this.flux;
    }*/


    @GetMapping(value = "stream/{maxPrice}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<ProductDto> getProductUpdates(@PathVariable int maxPrice){
        return this.flux
                .filter(dto -> dto.getPrice() <= maxPrice);
    }
}
