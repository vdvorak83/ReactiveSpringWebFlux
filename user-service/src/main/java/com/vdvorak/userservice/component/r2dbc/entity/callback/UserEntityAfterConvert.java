package com.vdvorak.userservice.component.r2dbc.entity.callback;

import com.vdvorak.userservice.entity.UserEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class UserEntityAfterConvert implements BeforeConvertCallback<UserEntity> {

    private  final double discount = 0.2d;

    @Override
    public Publisher<UserEntity> onBeforeConvert(UserEntity entity, SqlIdentifier table) {
        Double price = (entity.getBalance().doubleValue() * (1-discount));
        System.out.println("Actual : " + entity.getBalance());
        System.out.println("Updated : " + price);
        entity.setBalance(price.intValue());
        return Mono.just(entity);
    }
}
