package kz.bot.mvp.controllers;

import kz.bot.mvp.models.ContainerInstagramMessage;
import kz.bot.mvp.services.InstagramMessageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instagram-messages")
public class InstagramMessages {
    private final InstagramMessageService instagramMessageService;

    public InstagramMessages(InstagramMessageService instagramMessageService) {
        this.instagramMessageService = instagramMessageService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody ContainerInstagramMessage payload) {
        instagramMessageService.process(payload.getData());
    }
}