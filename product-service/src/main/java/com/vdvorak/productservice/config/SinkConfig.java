package com.vdvorak.productservice.config;

import com.vdvorak.productservice.dto.ProductDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {



    @Bean
    //push items
    public Sinks.Many<ProductDto> sink(){
       return Sinks.many().replay().limit(1);
    }

    @Bean
    public Flux<ProductDto> productBroadcast(Sinks.Many<ProductDto> sink){
        return sink.asFlux();
    }
}
