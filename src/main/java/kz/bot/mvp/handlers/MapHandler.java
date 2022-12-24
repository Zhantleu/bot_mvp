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
public class MapHandler implements Handler {
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
                " \uD83D\uDDA5 Монитор ACER 1920*1080 FullHD 165 Гц\n" +
                    "                        ⌨️ Клавиатура A4Tech Bloody B500N\n" +
                    "                        \uD83D\uDDB1 Мышь ASUS P305 TUF Gaming M3\n" +
                    "                        \uD83C\uDFA7 Наушники HyperX Cloud Stinger Core"
            )
            .build();
    }

    @Override
    public List<String> getUserCommands() {
        return List.of("Карта клуба");
    }
}