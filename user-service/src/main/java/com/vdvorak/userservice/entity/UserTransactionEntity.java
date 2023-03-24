package com.vdvorak.userservice.entity;


import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@NoArgsConstructor
@Table("user_transaction")
public class UserTransactionEntity {
    @Id
    private Integer id;
    private Integer userId;
    private Integer amount;

    private LocalDateTime transactionDate;
}
