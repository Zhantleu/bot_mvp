package kz.bot.mvp.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

@Component
public class StartHandler implements Handler {
    private static final String GREETING_STR = "Вас приветствует Бот компьютерного клуба 'MVP'";

    @Override
    public PartialBotApiMethod<Message> process(String chatId) {
        return SendMessage.builder()
            .text(GREETING_STR)
            .chatId(chatId)
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .keyboard(
                        List.of(
                            new KeyboardRow(
                                List.of(
                                    new KeyboardButton("Карта клуба")
                                )
                            ),
                            new KeyboardRow(
                                Arrays.asList(
                                    new KeyboardButton("Тарифы"),
                                    new KeyboardButton("Периферия"),
                                    new KeyboardButton("Время работы")
                                )
                            )
                        )
                    ).build()
            ).build();
    }

    @Override
    public List<String> getUserCommands() {
        return List.of("/start", "Домой");
    }
}
