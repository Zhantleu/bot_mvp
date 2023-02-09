package kz.bot.mvp.controllers;

import kz.bot.mvp.models.MessageEntity;
import kz.bot.mvp.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageRepository messageRepository;
    private final static String TOKEN = "20032008";

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<MessageEntity>> getAll(@PathVariable String username,
                                                      String token,
                                                      Pageable pageable) {
        if (!TOKEN.equalsIgnoreCase(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(messageRepository.findByChat_UsernameIgnoreCase(username, pageable));
    }
}
