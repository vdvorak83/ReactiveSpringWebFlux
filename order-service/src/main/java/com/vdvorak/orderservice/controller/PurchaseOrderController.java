package com.vdvorak.orderservice.controller;


import com.vdvorak.orderservice.dto.PurchaseOrderRequestDto;
import com.vdvorak.orderservice.dto.PurchaseOrderResponseDto;
import com.vdvorak.orderservice.service.OrderFulfillmentService;
import com.vdvorak.orderservice.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private OrderQueryService orderQueryService;

    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDto>> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono){
        return this.orderFulfillmentService.processOrder(requestDtoMono)
                //add excption handler reactive
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class,ResponseEntity.badRequest().build()) //404 error
                .onErrorReturn(WebClientRequestException.class,ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("user/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable int userId){
        return orderQueryService.getProductsByUserId(userId);
    }
}
