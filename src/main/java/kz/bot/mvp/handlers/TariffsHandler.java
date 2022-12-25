package kz.bot.mvp.handlers;

import kz.bot.mvp.utils.DefaultKeyBoardRowUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

@Component
public class TariffsHandler implements Handler {

    @Override
    public PartialBotApiMethod<Message> process(String chatId) {
        final InputFile priceImg =
            new InputFile(TariffsHandler.class.getResourceAsStream("/price.png"), "price.png");

        return SendPhoto.builder()
            .chatId(chatId)
            .photo(priceImg)
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .keyboard(DefaultKeyBoardRowUtil.DEFAULT_KEYBOARD).build()
            ).build();
    }

    @Override
    public List<String> getUserCommands() {
        return List.of("Тарифы");
    }
}