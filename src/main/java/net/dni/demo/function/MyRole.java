package net.dni.demo.function;

import net.dni.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MyRole implements Function<Message<String>, Message<String>> {

    @Autowired
    private MyService myService;

    @Override
    public Message<String> apply(Message<String> message) {
        return MessageBuilder.withPayload(myService.myRole()).setHeader("statusCode", 200).build();
    }

}
