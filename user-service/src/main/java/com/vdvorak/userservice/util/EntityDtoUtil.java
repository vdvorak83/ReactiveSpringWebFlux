package com.vdvorak.userservice.util;

import com.vdvorak.userservice.dto.TransactionRequestDto;
import com.vdvorak.userservice.dto.TransactionResponseDto;
import com.vdvorak.userservice.dto.TransactionStatus;
import com.vdvorak.userservice.dto.UserDto;
import com.vdvorak.userservice.entity.UserEntity;
import com.vdvorak.userservice.entity.UserTransactionEntity;
import java.time.LocalDateTime;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static UserDto toDto(UserEntity user){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public static UserEntity toEntity(UserDto user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        return userEntity;
    }

    public static UserTransactionEntity toEntity(TransactionRequestDto responseDto){
        UserTransactionEntity userTransactionEntity = new UserTransactionEntity();
                userTransactionEntity.setUserId(responseDto.getUserId());
                userTransactionEntity.setAmount(responseDto.getAmount());
                userTransactionEntity.setTransactionDate(LocalDateTime.now());
                return userTransactionEntity;
    }

    public static TransactionResponseDto toDto(TransactionRequestDto requestDto, TransactionStatus status){
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setAmount(requestDto.getAmount());
        responseDto.setUserId(requestDto.getUserId());
        responseDto.setStatus(status);
        return responseDto;
    }
}
