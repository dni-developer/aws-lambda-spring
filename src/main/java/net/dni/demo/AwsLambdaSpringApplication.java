package net.dni.demo;

import net.dni.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AwsLambdaSpringApplication {

    @Autowired
    private MyService myService;

    @Value("${AWS_LAMBDA_FUNCTION_VERSION:Not Available}")
    private String awsLambdaFunctionVersion;

    @Value("${AWS_LAMBDA_INITIALIZATION_TYPE:Not Available}")
    private String awsLambdaInitializationType;

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
    public Supplier<String> getAwsLambdaFunctionVersion() {
        return () -> awsLambdaFunctionVersion;
    }

    @Bean
    public Supplier<String> getAwsLambdaInitializationType() {
        return () -> awsLambdaInitializationType;
    }

}
