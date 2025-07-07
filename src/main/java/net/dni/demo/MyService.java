package net.dni.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Value("${HELLO_MESSAGE:missing HELLO_MESSAGE in environment variable}")
    private String helloMessage;

    public String sayHello() {
        return helloMessage;
    }

    public void logMessage(String input) {
        System.out.println(input);
    }

    public String reverse(String input) {
        StringBuilder res = new StringBuilder(input);
        res.reverse();
        return res.toString();
    }

}
