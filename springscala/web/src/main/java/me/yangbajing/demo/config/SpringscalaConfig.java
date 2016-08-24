package me.yangbajing.demo.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yangbajing on 16-8-24.
 */
@Configuration
public class SpringscalaConfig {

    @Bean
    public Module jacksonModuleScala() {
        return new DefaultScalaModule();
    }

}
