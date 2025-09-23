package net.dni.demo.aop;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AwsFunctionAop {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Around("execution(* net.dni.demo.function..*.*(..))")
    public Object aroundFunctions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("Before: {} {}", joinPoint.getSignature().getName(), joinPoint.getTarget());
            Object[] args = joinPoint.getArgs();
            if (args[0] instanceof Message message) {
                String authHeader = message.getHeaders().get("authorization", String.class);
                if (StringUtils.isBlank(authHeader)) {
                    log.info("No authorization header found");
                } else {
                    String authType = authHeader.split(" ")[0];
                    String authValue = authHeader.split(" ")[1];
                    if ("Basic".equalsIgnoreCase(authType)) {
                        log.info("Basic Authenticating");
                        Authentication authResult = authenticateWithBasicAuth(authValue);
                        SecurityContextHolder.getContext().setAuthentication(authResult);
                    } else if ("Bearer".equalsIgnoreCase(authType)) {
                        log.info("Bearer Authenticating is not supported yet");
                    } else {
                        log.info("Unsupported Authenticating type: {}", authType);
                    }
                }
            } else {
                log.info("First argument is not a Message");
            }

            return joinPoint.proceed();
        } catch (Exception e) {
            log.error("Exception caught in AOP:", e);
            return MessageBuilder.withPayload(e.getMessage()).setHeader("statusCode", 500).build();
        }
    }

    private Authentication authenticateWithBasicAuth(String encodedBasicAuth) {
        String decodedBasicAuth = new String(java.util.Base64.getDecoder().decode(encodedBasicAuth));
        String[] credentials = decodedBasicAuth.split(":");
        String username = credentials[0];
        String password = credentials[1];

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        return authenticationManager.authenticate(newAuthentication);
    }

}
