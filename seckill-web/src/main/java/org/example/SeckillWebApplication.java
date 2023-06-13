package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("org.example.mapper")
@ServletComponentScan("org.example.security")
public class SeckillWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillWebApplication.class, args);
    }
}
