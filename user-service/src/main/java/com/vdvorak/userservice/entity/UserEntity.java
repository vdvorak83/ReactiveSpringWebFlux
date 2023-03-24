package com.vdvorak.userservice.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Table("users")
public class UserEntity {

    @Id
    private Integer id;
    private String name;
    private Integer balance;
}
