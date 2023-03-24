package com.vdvorak.userservice.service;

import com.vdvorak.userservice.dto.UserDto;
import com.vdvorak.userservice.repository.UserRepository;
import com.vdvorak.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Flux<UserDto> findAll() {
        return  this.userRepository.findAll()
                .map(EntityDtoUtil::toDto);

    }

    public Mono<UserDto> findUserById(final int userId){
        return  this.userRepository.findById(userId)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> createUse(Mono<UserDto> userDtoMono){
        return userDtoMono.map(EntityDtoUtil::toEntity)
                .flatMap(this.userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> updateUser(int userId , Mono<UserDto> userDtoMono){
        return
                this.userRepository.findById(userId)
                        .flatMap(userEntity -> userDtoMono
                                                            .map(EntityDtoUtil::toEntity)
                                                            .doOnNext(u ->u.setId(userId)))
                        .flatMap(this.userRepository::save)
                        .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteUser(int userId){
        return this.userRepository.deleteById(userId);
    }
}
