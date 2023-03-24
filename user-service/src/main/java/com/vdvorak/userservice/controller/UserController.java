package com.vdvorak.userservice.controller;


import com.vdvorak.userservice.dto.UserDto;
import com.vdvorak.userservice.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("all")
    public Mono<ResponseEntity<List<UserDto>>> all() {
        return this.userService.findAll()
                .collectList()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable int id) {
        return this.userService.findUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Mono<ResponseEntity<UserDto>> createUser(@RequestBody Mono<UserDto> userDtoMono){
        return this.userService.createUse(userDtoMono).map(ResponseEntity::ok);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDto>> updateUser(@RequestBody Mono<UserDto> userDtoMono){
        return this.userService.createUse(userDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

   @DeleteMapping("{userId}")
    public Mono<Void> deleteUser(@PathVariable int userId){
       return this.userService.deleteUser(userId);
    }

}
