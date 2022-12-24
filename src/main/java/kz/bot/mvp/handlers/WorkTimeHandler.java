package kz.bot.mvp.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class WorkTimeHandler implements Handler {
    @Override
    public PartialBotApiMethod<Message> process(String chatId) {
        return SendMessage.builder()
            .chatId(chatId)
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .keyboard(
                        List.of(
                            new KeyboardRow(List.of(new KeyboardButton("Домой")))
                        )
                    ).build()
            ).text(
                "Работаем каждый день!\n" +
                    "                        С 15:00 до последнего клиента"
            )
            .build();
    }

    @Override
    public List<String> getUserCommands() {
        return List.of("Время работы");
    }
}
