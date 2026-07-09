package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * it is a meta annotation - it does a lot of thing for you
 * 1: springboot configuration annotation  -  it tells Springboot "this class can itself define beans"
 * 2: enable auto configuration annotation - this is the real magic behind springboot, based one your classpath
 * (like your dependencies in pom.xml) spring boot will automatically configure beans for you
 * 3: component scan annotation - tells springboot to scan package containing this class and sub packages
 *looking for classes annotated with annotations like component annotaion/ service/ repostory..
 *
 *
 *
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
