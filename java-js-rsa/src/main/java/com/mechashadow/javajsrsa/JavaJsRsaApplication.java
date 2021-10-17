package com.mechashadow.javajsrsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaJsRsaApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaJsRsaApplication.class, args);
        AppRSA.test();
    }
}
