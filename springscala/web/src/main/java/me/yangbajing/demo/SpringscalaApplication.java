package me.yangbajing.demo;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;
import me.yangbajing.demo.config.SpringscalaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringscalaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringscalaApplication.class, args);
    }

}
