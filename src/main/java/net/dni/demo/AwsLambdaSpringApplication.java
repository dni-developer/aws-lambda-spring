package net.dni.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class AwsLambdaSpringApplication {

    @Autowired
    private MyService myService;

    public static void main(String[] args) {
        SpringApplication.run(AwsLambdaSpringApplication.class, args);
    }

    @Bean
    public Supplier<String> sayHello() {
        return myService::sayHello;
    }

    @Bean
    public Consumer<String> logMessage() {
        return myService::logMessage;
    }

    @Bean
    public Function<String, String> reverse() {
        return myService::reverse;
    }

}
