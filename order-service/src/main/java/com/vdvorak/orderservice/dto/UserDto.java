package com.vdvorak.orderservice.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private Integer balance;
}
