package kz.bot.mvp.handlers;

import kz.bot.mvp.models.Seat;
import kz.bot.mvp.storage.SeatStorage;
import kz.bot.mvp.storage.UserCountStorage;
import kz.bot.mvp.utils.DefaultKeyBoardRowUtil;
import kz.bot.mvp.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.io.InputStream;
import java.util.List;

@Component
public class MapHandler implements Handler {

    private final SeatStorage seatStorage;
    private final UserCountStorage userCountStorage;
    private final ImageUtil imageUtil;

    @Autowired
    public MapHandler(SeatStorage seatStorage, UserCountStorage userCountStorage, ImageUtil imageUtil) {
        this.seatStorage = seatStorage;
        this.userCountStorage = userCountStorage;
        this.imageUtil = imageUtil;
    }

    private static SendMessage getErrorMessage(String chatId) {
        return SendMessage.builder()
            .chatId(chatId)
            .text("Извините. На данный момент сервис не доступен!")
            .replyMarkup(ReplyKeyboardMarkup.builder().keyboard(DefaultKeyBoardRowUtil.DEFAULT_KEYBOARD).build())
            .build();
    }

    @Override
    public PartialBotApiMethod<Message> process(String chatId) {
        final List<Seat> seats = seatStorage.getSeats();
        if (seats.isEmpty()) {
            return getErrorMessage(chatId);
        }
        final InputStream image = imageUtil.fillSeats(seats);
        userCountStorage.update(chatId);
        return SendPhoto.builder()
            .chatId(chatId)
            .parseMode(ParseMode.HTML)
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .keyboard(DefaultKeyBoardRowUtil.DEFAULT_KEYBOARD).build()
            )
            .photo(new InputFile(image, "image.png"))
            .caption(getPretty())
            .build();
    }

    @Override
    public List<String> getUserCommands() {
        return List.of("Карта клуба");
    }

    private String getPretty() {
        return "\uD83D\uDFE9 - СВОБОДЕН\n" +
            "\uD83D\uDFEA - ЗАНЯТ\n" +
            "\n" +
            "\uD83D\uDDA5️2 общих зала, 21 место (1660 ti/super)\n" +
            "⭐️1 VIP зал, 5 мест (RTX 3060)\n" +
            "\n" +
            "Уточняйте информацию у администратора!\n" +
            "\n" +
            "Чтобы узнать через сколько освободятся нужные вам места - пишите в Инстаграм <a href='https://www.instagram.com/mvp_kst/'>@mvp_kst</a>";
    }
}