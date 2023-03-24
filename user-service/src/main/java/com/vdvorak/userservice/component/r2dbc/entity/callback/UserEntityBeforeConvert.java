package com.vdvorak.userservice.component.r2dbc.entity.callback;

import com.vdvorak.userservice.entity.UserEntity;
import org.reactivestreams.Publisher;
import org.springframework.core.Ordered;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class UserEntityBeforeConvert implements BeforeConvertCallback<UserEntity> {

    private static final String PATTERN = "[^a-zA-Z ]";

    @Override
    public Publisher<UserEntity> onBeforeConvert(UserEntity entity, SqlIdentifier table) {
        var updatedName = entity.getName().replaceAll(PATTERN, "");
        System.out.println("Actual : " + entity.getName());
        System.out.println("Updated : " + updatedName);
        entity.setName(updatedName);
        return Mono.just(entity);
    }

}
