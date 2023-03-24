package com.vdvorak.r2dbc.service;


import com.vdvorak.r2dbc.dto.DepositRequest;
import com.vdvorak.r2dbc.entity.Account;
import com.vdvorak.r2dbc.entity.MoneyDepositEvent;
import com.vdvorak.r2dbc.repository.AccountRepository;
import com.vdvorak.r2dbc.repository.MoneyDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
public class BankService {



    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MoneyDepositRepository depositRepository;


    public Mono<Void> deposit (DepositRequest request){
        return this.accountRepository.findById(request.getAccount())
                .doOnNext(account ->account.setBalance(account.getBalance()+request.getAmount()))
                .flatMap(this.accountRepository::save)
                .thenReturn(toEvent(request))
                .flatMap(this.depositRepository::save)
                .doOnError(System.out::println)
                .then();

    }


    @Transactional
    public Mono<Void> depositUsedAnnTrans (DepositRequest request){
        return this.accountRepository.findById(request.getAccount())
                .doOnNext(account ->account.setBalance(account.getBalance()+request.getAmount()))
                .flatMap(this.accountRepository::save)
                .thenReturn(toEvent(request))
                .flatMap(this.depositRepository::save)
                .doOnError(System.out::println)
                .then();

    }


    @Autowired
    private TransactionalOperator operator;
    public Mono<Void> depositUsedTransactionOperator (DepositRequest request){
        return this.accountRepository.findById(request.getAccount())
                .doOnNext(account ->account.setBalance(account.getBalance()+request.getAmount()))
                .flatMap(this.accountRepository::save)
                .thenReturn(toEvent(request))
                .flatMap(this.depositRepository::save)
                .doOnError(System.out::println)
                .as(operator::transactional) //add this
                .then();

    }

    private MoneyDepositEvent toEvent(DepositRequest request){
        return
                MoneyDepositEvent.create(null, request.getAccount(), request.getAmount());
    }
}
