package com.discordee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApplicationInit{

    public static void main(String[] args) {
        SpringApplication.run(ApplicationInit.class);
    }

}
