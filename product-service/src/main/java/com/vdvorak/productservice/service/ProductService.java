package com.vdvorak.productservice.service;

import com.vdvorak.productservice.dto.ProductDto;
import com.vdvorak.productservice.repository.ProductRepository;
import com.vdvorak.productservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private Sinks.Many<ProductDto> sink;

    public Flux<ProductDto> getAll(){
        return this.repository.findAll()
                .map(EntityDtoUtil::toDto);
    }
    public Flux<ProductDto> getproductByPriceRange(int min,int max){
        return this.repository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }
    public Mono<ProductDto> getproductById(String id){
        return this.repository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> dtoMono) {

        return dtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.repository::insert)
                .map(EntityDtoUtil::toDto)
                .doOnNext(this.sink::tryEmitNext); //push items
    }

    public Mono<ProductDto> updateProduct(String id,Mono<ProductDto> productDtoMono) {

        return this.repository.findById(id)
                .flatMap(p->productDtoMono
                                  .map(EntityDtoUtil::toEntity))
                        .doOnNext(e->e.setId(id))
                        .flatMap(this.repository::save)
                .map(EntityDtoUtil::toDto);
    }


    public Mono<Void> deleteProduct(String id) {
        return this.repository.deleteById(id);

    }

}
