package com.pizzastore.messageservice.controller;

import com.pizzastore.messageservice.beans.Message;
import com.pizzastore.messageservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/all")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @PostMapping("/send")
    public Message sendMessage(@RequestBody Message message) {
        message.setSentAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

   
    @PostMapping("/reply/{id}")
    public Message replyToMessage(@PathVariable Long id, @RequestParam String reply) {
        Message msg = messageRepository.findById(id).orElse(null);
        if (msg != null) {
            msg.setAdminReply(reply);
            return messageRepository.save(msg);
        }
        return null;
    }

    
    @GetMapping("/user/{userId}")
    public List<Message> getMessagesByUser(@PathVariable Long userId) {
        return messageRepository.findByUserId(userId);
    }
}