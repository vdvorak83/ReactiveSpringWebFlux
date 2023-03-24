package com.vdvorak.userservice.repository;

import com.vdvorak.userservice.entity.UserEntity;
import com.vdvorak.userservice.entity.UserTransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransactionEntity,Integer> {

    Flux<UserTransactionEntity> findByUserId(int userId);

}
