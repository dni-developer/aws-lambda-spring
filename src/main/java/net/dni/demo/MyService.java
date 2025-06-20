package net.dni.demo;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public String sayHello() {
        return "Hello World!";
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
