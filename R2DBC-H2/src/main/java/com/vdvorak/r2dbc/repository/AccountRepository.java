package com.vdvorak.r2dbc.repository;


import com.vdvorak.r2dbc.entity.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  AccountRepository extends ReactiveCrudRepository<Account,Integer> {

}
