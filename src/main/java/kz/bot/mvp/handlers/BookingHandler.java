package kz.bot.mvp.handlers;

import kz.bot.mvp.models.Seat;
import kz.bot.mvp.storage.StepStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.InputStream;
import java.util.List;

@Service
public class BookingHandler implements Handler {
    private final StepStorage stepStorage;

    @Autowired
    public BookingHandler(StepStorage stepStorage) {
        this.stepStorage = stepStorage;
    }

    @Override
    public PartialBotApiMethod<Message> process(String chatId) {
        return SendMessage.builder()
            .chatId(chatId)
            .text("Скоро будет реализовано)")
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .keyboard(
                        List.of(
                            new KeyboardRow(List.of(new KeyboardButton("Домой"))),
                            new KeyboardRow(List.of(new KeyboardButton("Бронирование")))
                        )
                    ).build()
            )
            .build();

    }

    @Override
    public List<String> getUserCommands() {
        return List.of("Бронирование") ;
    }
}
