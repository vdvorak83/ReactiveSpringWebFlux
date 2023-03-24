package com.vdvorak.r2dbc;

import com.vdvorak.r2dbc.dto.DepositRequest;
import com.vdvorak.r2dbc.entity.Account;
import com.vdvorak.r2dbc.repository.AccountRepository;
import com.vdvorak.r2dbc.repository.MoneyDepositRepository;
import com.vdvorak.r2dbc.service.BankService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class R2dbcTransactionApplicayopnTests {

    @Value("classpath:init.sql")
    private Resource initSql;

    @Autowired
    private R2dbcEntityTemplate entityTemplate;

    @Autowired
    private BankService bankService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MoneyDepositRepository moneyDepositRepository;

    @BeforeAll
    public void initDB() throws IOException {
        String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
        System.out.println("QUERY : ");
        System.out.println(query);
        Mono<Void> momo = this.entityTemplate
                .getDatabaseClient()
                .sql(query)
                .then();

        StepVerifier.create(momo)
                .verifyComplete();
    }


    @Test
    @Order(1)
    public void transactionSuccess(){
        DepositRequest request = DepositRequest.create(500, 1);
        //Mono<Account> mono = this.bankService.deposit(request) //1
       // Mono<Account> mono = this.bankService.depositUsedAnnTrans(request) //2
        Mono<Account> mono = this.bankService.depositUsedTransactionOperator(request) //3
                .then(getAccountDetails(request));

        StepVerifier.create(mono)
                .expectNextMatches(ac->ac.getBalance() == 500)
                .verifyComplete();
    }


    @Test
    @Order(2)
    public void transactionFailure(){
        DepositRequest request = DepositRequest.create(99, 2);
        //Mono<Account> mono = this.bankService.deposit(request)  //1
       // Mono<Account> mono = this.bankService.depositUsedAnnTrans(request) //2
        Mono<Account> mono = this.bankService.depositUsedTransactionOperator(request) //3
                .then(getAccountDetails(request));

        StepVerifier.create(mono)
                .expectNextMatches(ac->ac.getBalance() == 100)
                .verifyComplete();
    }

    private Mono<Account> getAccountDetails(DepositRequest request) {
    return
            this.accountRepository.findById(request.getAccount())
                    .doOnNext(System.out::println);
    }
}
