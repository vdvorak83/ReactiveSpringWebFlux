package com.vdvorak.userservice.controller;

import com.vdvorak.userservice.dto.TransactionRequestDto;
import com.vdvorak.userservice.dto.TransactionResponseDto;
import com.vdvorak.userservice.entity.UserTransactionEntity;
import com.vdvorak.userservice.service.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {


    @Autowired
    private UserTransactionService userTransactionService;



    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono ){
        return  requestDtoMono.flatMap(this.userTransactionService::createTransaction);
    }

   @GetMapping("")
    public Flux<UserTransactionEntity> getByUserId(@RequestParam("userId") int userId){
        return this.userTransactionService.getByUserId(userId);
   }


}
