package com.vdvorak.orderservice.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class TransactionRequestDto {
    private Integer userId;
    private Integer amount;
}
