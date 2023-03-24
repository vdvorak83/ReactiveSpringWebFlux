package com.vdvorak.orderservice.service;


import com.vdvorak.orderservice.client.ProductClient;
import com.vdvorak.orderservice.client.UserClient;
import com.vdvorak.orderservice.dto.PurchaseOrderRequestDto;
import com.vdvorak.orderservice.dto.PurchaseOrderResponseDto;
import com.vdvorak.orderservice.dto.RequestContext;
import com.vdvorak.orderservice.repositoty.PurchaseOrderRepository;
import com.vdvorak.orderservice.util.EntityDtoUtil;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

@Service
public class OrderFulfillmentService {


    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono){
       return requestDtoMono
                .map(dto -> new RequestContext(dto))
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
               //.publishOn(Schedulers.boundedElastic()) // 2 way
                .map(po -> this.purchaseOrderRepository.save(po)) //blocking not R2DBC
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
               .subscribeOn(Schedulers.boundedElastic()); //very  important // 1 way main use publisherOn 2 way
    }


    private Mono<RequestContext> productRequestResponse(RequestContext rc){
     return    this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(rc::setProductDto)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1))) // repeat 5
                .thenReturn(rc);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext requestContext){
        return this.userClient.authorizeTransaction(requestContext.getTransactionRequestDto())
                .doOnNext(requestContext::setTransactionResponseDto)
                .thenReturn(requestContext);
    }
}
