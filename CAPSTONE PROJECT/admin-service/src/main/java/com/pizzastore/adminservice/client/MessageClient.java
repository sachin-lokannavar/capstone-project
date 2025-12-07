package com.pizzastore.adminservice.client;

import com.pizzastore.adminservice.dto.MessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "message-service")
public interface MessageClient {

    
    @GetMapping("/message/api/all")
    List<MessageDto> getAllMessages();
    @PostMapping("/message/api/reply/{id}")
    void replyToMessage(@PathVariable("id") Long id, @RequestParam("reply") String reply);
}