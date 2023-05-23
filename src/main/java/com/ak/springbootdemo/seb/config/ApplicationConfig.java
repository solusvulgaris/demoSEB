package com.ak.springbootdemo.seb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring Boot Application Configuration
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages="com.ak.springbootdemo.seb")
@EnableAspectJAutoProxy
public class ApplicationConfig {

}
