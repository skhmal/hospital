package com.khmal.hospital.controller;

import com.khmal.hospital.initializer.Mysql;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

@Sql("/db/testDB.sql")
@SpringBootTest
@ContextConfiguration(initializers = {
        Mysql.Initializer.class
})
@Transactional
public abstract class IntegrationTestBaseClass {

    @BeforeAll
    static void init(){
        Mysql.container.start();
    }
}
