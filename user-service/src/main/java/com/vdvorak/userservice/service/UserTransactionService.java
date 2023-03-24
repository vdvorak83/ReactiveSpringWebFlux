package com.vdvorak.userservice.service;

import com.vdvorak.userservice.dto.TransactionRequestDto;
import com.vdvorak.userservice.dto.TransactionResponseDto;
import com.vdvorak.userservice.dto.TransactionStatus;
import com.vdvorak.userservice.entity.UserTransactionEntity;
import com.vdvorak.userservice.repository.UserRepository;
import com.vdvorak.userservice.repository.UserTransactionRepository;
import com.vdvorak.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserTransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository transactionRepository;


    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto){
      return   this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
              .filter(Boolean::booleanValue)
              .map(b-> EntityDtoUtil.toEntity(requestDto))
              .flatMap(this.transactionRepository::save)
              .map(userTransactionEntity -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
              .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransactionEntity> getByUserId(int userId){
        return this.transactionRepository.findByUserId(userId);
    }

}
