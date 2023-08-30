package kz.bot.mvp.services.impl;

import kz.bot.mvp.models.InstagramMessageDto;
import kz.bot.mvp.polling.MvpBot;
import kz.bot.mvp.properties.BotProperty;
import kz.bot.mvp.services.InstagramMessageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstagramMessageServiceImpl implements InstagramMessageService {
    private final MvpBot mvpBot;
    private final BotProperty botProperty;

    @Autowired
    public InstagramMessageServiceImpl(MvpBot mvpBot, BotProperty botProperty) {
        this.mvpBot = mvpBot;
        this.botProperty = botProperty;
    }

    @SneakyThrows
    @Override
    public void process(List<InstagramMessageDto> messageDtos) {
        List<String> unreadMessages = new ArrayList<>();
        for (InstagramMessageDto messageDto : messageDtos) {
            if (messageDto.isNotAnswered()
                    && !messageDto.getName().equalsIgnoreCase("Erik")
                    && !messageDto.getName().startsWith("Салта")) {
                unreadMessages.add(messageDto.getName());
            }
        }
        if (!unreadMessages.isEmpty()) {
            mvpBot.execute(createUnreadMessage(unreadMessages));
        }
    }

    private SendMessage createUnreadMessage(List<String> unreadMessages) {
        return SendMessage.builder()
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .clearKeyboard()
                        .build())
                .chatId(botProperty.getAdminGroupId())
                .text(String.format("Ждут ответа @BIG @SaVasAge04 @slimshadyer88 \n %s",
                        String.join("\n", unreadMessages)))
                .build();
    }
}