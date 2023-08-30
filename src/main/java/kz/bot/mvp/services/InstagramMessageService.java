package kz.bot.mvp.services;

import kz.bot.mvp.models.InstagramMessageDto;

import java.util.List;

public interface InstagramMessageService {
    void process(List<InstagramMessageDto> messageDtos);
}
