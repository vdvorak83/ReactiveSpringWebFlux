package com.vdvorak.userservice.dataservice;

import com.vdvorak.userservice.entity.UserEntity;
import com.vdvorak.userservice.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Service
public class DataSetupService implements CommandLineRunner {

    @Value("classpath:h2/init.sql")
    private Resource initSql;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private R2dbcEntityTemplate entityTemplate;


    @Override
    public void run(String... args) throws Exception {

        final String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);

        System.out.println(query);
        this.entityTemplate.getDatabaseClient()
                .sql(query)
                .then()
                .subscribe(System.out::println);

        String userName = "new- -user/d&&u#c%t";
        UserEntity user = new UserEntity();
        user.setName(userName);
        user.setBalance(100);

// insert
        this.userRepository.save(user)
                .subscribe(userEntity -> System.out.println("After inserting UserEntity:   " + userEntity));








    }
}
