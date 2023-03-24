package com.vdvorak.orderservice.repositoty;

import com.vdvorak.orderservice.entiry.PurchaseOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface  PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Integer> {
    List<PurchaseOrder> findByUserId(int userId);
}
