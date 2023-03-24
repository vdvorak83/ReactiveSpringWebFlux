package com.vdvorak.userservice.component.r2dbc.entity.callback;

import com.vdvorak.userservice.entity.UserEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.r2dbc.core.Parameter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public class UserBeforeSave implements BeforeSaveCallback<UserEntity> {
    @Override
    public Publisher<UserEntity> onBeforeSave(UserEntity userEntity, OutboundRow outboundRow, SqlIdentifier sqlIdentifier) {
        outboundRow.put(SqlIdentifier.unquoted("created_by"), Parameter.from("vdvorak"));
        return Mono.just(userEntity);
    }
}
