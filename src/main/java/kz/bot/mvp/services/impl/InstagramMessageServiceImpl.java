package kz.bot.mvp.services.impl;

import kz.bot.mvp.models.InstagramMessageDto;
import kz.bot.mvp.polling.MvpBot;
import kz.bot.mvp.properties.BotProperty;
import kz.bot.mvp.services.InstagramMessageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstagramMessageServiceImpl implements InstagramMessageService {
    private final static String TEXT = "<b>ЖДУТ ОТВЕТА В ИНСТАГРАМ</b>";

    private Integer messageId;
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
            if (messageDto != null) {
                if (messageDto.getStatus().equals(InstagramMessageDto.Status.NOT_ANSWERED)
                        && !messageDto.getName().equalsIgnoreCase("Erik")
                        && !messageDto.getName().startsWith("Салта")) {
                    unreadMessages.add(messageDto.getName());
                }
            }
        }
        if (!unreadMessages.isEmpty()) {
            if (messageId != null) {
                mvpBot.execute(deletePrevMessage());
            }
            final SendMessage unreadMessage = createUnreadMessage();
            unreadMessage.enableHtml(true);
            final Message execute = mvpBot.execute(unreadMessage);
            messageId = execute.getMessageId();
        }
    }

    private DeleteMessage deletePrevMessage() {
        return DeleteMessage.builder()
                .chatId(botProperty.getAdminGroupId())
                .messageId(messageId)
                .build();
    }

    private SendMessage createUnreadMessage() {
        return SendMessage.builder()
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .clearKeyboard()
                        .build())
                .chatId(botProperty.getAdminGroupId())
                .text(InstagramMessageServiceImpl.TEXT)
                .build();
    }
}