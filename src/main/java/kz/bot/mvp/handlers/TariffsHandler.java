package kz.bot.mvp.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class TariffsHandler implements Handler {
    @Override
    public PartialBotApiMethod<Message> process(String chatId) {
        return SendPhoto.builder()
            .chatId(chatId)
            .photo(new InputFile(TariffsHandler.class.getResourceAsStream("/price.jpeg"), "price.jpeg"))
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .keyboard(
                        List.of(
                            new KeyboardRow(List.of(new KeyboardButton("Домой")))
                        )
                    ).build()
            ).build();
    }

    @Override
    public List<String> getUserCommands() {
        return List.of("Тарифы");
    }
}