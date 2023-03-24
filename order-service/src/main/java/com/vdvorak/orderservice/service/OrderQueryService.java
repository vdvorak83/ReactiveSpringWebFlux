package com.vdvorak.orderservice.service;

import com.vdvorak.orderservice.dto.PurchaseOrderResponseDto;
import com.vdvorak.orderservice.entiry.PurchaseOrder;
import com.vdvorak.orderservice.repositoty.PurchaseOrderRepository;
import com.vdvorak.orderservice.util.EntityDtoUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userid) {
        List<PurchaseOrder> purchaseOrders = this.purchaseOrderRepository.findByUserId(userid);
        //convert to Flux
       return Flux.fromStream(() -> purchaseOrders.stream()) //blocking
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
               .subscribeOn(Schedulers.boundedElastic());//important
    }
}
