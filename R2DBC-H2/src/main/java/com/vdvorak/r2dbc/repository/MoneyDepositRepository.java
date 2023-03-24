package com.vdvorak.r2dbc.repository;


import com.vdvorak.r2dbc.entity.MoneyDepositEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  MoneyDepositRepository extends ReactiveCrudRepository<MoneyDepositEvent,Integer> {

}
