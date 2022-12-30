package kz.bot.mvp.utils;

import kz.bot.mvp.polling.MvpBot;
import kz.bot.mvp.properties.BotProperty;
import kz.bot.mvp.storage.UserCountStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class CountUsersCron {

    private final MvpBot mvpBot;
    private final BotProperty botProperty;
    private final UserCountStorage userCountStorage;

    @Autowired
    public CountUsersCron(MvpBot mvpBot, BotProperty botProperty, UserCountStorage userCountStorage) {
        this.mvpBot = mvpBot;
        this.botProperty = botProperty;
        this.userCountStorage = userCountStorage;
    }

    @Scheduled(cron = "0 9 5 * * ?")
    void getUsers() throws TelegramApiException {
        mvpBot.execute(createAdminMessage());
        userCountStorage.reset();
    }

    private SendMessage createAdminMessage() {
        return SendMessage.builder()
            .chatId(botProperty.getAdminGroupId())
            .parseMode(ParseMode.HTML)
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .keyboard(
                        List.of(
                            new KeyboardRow(List.of(new KeyboardButton("Сосать цэ лежать")))
                        )
                    ).build()
            )
            .text(String.format("Количество пользователей использующих бота за 24 часа: %d",
                userCountStorage.getCount()))
            .build();

    }

}
