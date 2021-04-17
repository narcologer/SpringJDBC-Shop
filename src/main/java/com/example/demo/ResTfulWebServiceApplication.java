package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class ResTfulWebServiceApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ResTfulWebServiceApplication.class);

    public static void main(String args[]) {
        SpringApplication.run(ResTfulWebServiceApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {

        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id INT, login VARCHAR(255), balance NUMERIC(10,2) )");
        jdbcTemplate.execute("DROP TABLE payrecords IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE payrecords(" +
                "login VARCHAR(255), new_balance NUMERIC(10,2) )");
    }
}
