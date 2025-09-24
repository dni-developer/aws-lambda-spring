package net.dni.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
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
        if (input == null || input.isBlank() || "null".equalsIgnoreCase(input)) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        StringBuilder res = new StringBuilder(input);
        res.reverse();
        return res.toString();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String myRole() {
        log.info("myRole");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication.getAuthorities().toString();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {
        return "You are an admin";
    }

}
