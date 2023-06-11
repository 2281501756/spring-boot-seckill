package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("org.example.mapper")
public class SeckillWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillWebApplication.class, args);
    }
}
