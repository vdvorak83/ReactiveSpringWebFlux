package com.vdvorak.productservice.repository;

import com.vdvorak.productservice.model.ProductEntity;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {

    // >min &< max
    //  Flux<ProductEntity> findByPriceBetween(int min,int max) //not work
      Flux<ProductEntity> findByPriceBetween(Range<Integer> range);


}
