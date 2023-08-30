package kz.bot.mvp.utils;

import kz.bot.mvp.models.ChatEntity;
import kz.bot.mvp.polling.MvpBot;
import kz.bot.mvp.properties.BotProperty;
import kz.bot.mvp.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountUsersCron {

    private final MvpBot mvpBot;
    private final BotProperty botProperty;
    private final ChatRepository chatRepository;

    @Autowired
    public CountUsersCron(MvpBot mvpBot,
                          BotProperty botProperty,
                          ChatRepository chatRepository) {
        this.mvpBot = mvpBot;
        this.botProperty = botProperty;
        this.chatRepository = chatRepository;
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void getUsers() throws TelegramApiException {
        mvpBot.execute(createAdminMessage());
    }

    private SendMessage createAdminMessage() {
        final LocalDateTime end = LocalDateTime.now(ZoneId.of("Asia/Almaty"));
        final LocalDateTime start = end.minus(1, ChronoUnit.DAYS);
        final List<String> chats = chatRepository.findAllByMessages_CreatedAtBetween(start, end)
            .stream()
            .map(ChatEntity::getUsername)
            .collect(Collectors.toList());
        return SendMessage.builder()
            .replyMarkup(InlineKeyboardMarkup.builder()
                .clearKeyboard()
                .build())
            .chatId(botProperty.getAdminGroupId())
            .text(String.format("Количество пользователей использующих бота за 24 часа: %d\n%s",
                chats.size(),
                String.join("\n", chats)))
            .build();
    }

}
