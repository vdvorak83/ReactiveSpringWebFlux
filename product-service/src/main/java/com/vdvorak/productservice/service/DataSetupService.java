package com.vdvorak.productservice.service;

import com.vdvorak.productservice.dto.ProductDto;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {
    @Autowired
    private ProductService service;

    public void run(String... arg){
        ProductDto p1 = new ProductDto("4k-tv",1000);
        ProductDto p2 = new ProductDto("slr-camer",750);
        ProductDto p3 = new ProductDto("phone",800);
        ProductDto p4 = new ProductDto("4headphne",200);

        Flux.just(p1,p2,p3,p4)
                .concatWith(newProducts())
                .flatMap(p->this.service.insertProduct(Mono.just(p))) //push items
                .subscribe(System.out::println);
    }

    private Flux<ProductDto> newProducts(){
       return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(2))
                .map(i -> new ProductDto("product : " +i, ThreadLocalRandom.current().nextInt(1, 100)));
    }
}
