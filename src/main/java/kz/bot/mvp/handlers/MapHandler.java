package kz.bot.mvp.handlers;

import kz.bot.mvp.models.Point;
import kz.bot.mvp.models.Seat;
import kz.bot.mvp.models.StepStatus;
import kz.bot.mvp.storage.SeatStorage;
import kz.bot.mvp.storage.StepStorage;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MapHandler implements Handler {

    private final SeatStorage seatStorage;
    private final ImageUtil imageUtil;
    private final Map<Integer, Point> coordinates = new HashMap<>() {{
        put(1, new Point(586, 205)); // 1
        put(2, new Point(457, 205)); // 2
        put(3, new Point(331, 205)); // 3
        put(4, new Point(202, 205)); // 4
        put(5, new Point(75, 205)); // 5
        put(6, new Point(74, 78)); // 6
        put(7, new Point(203, 78)); // 7
        put(8, new Point(331, 78)); // 8
        put(9, new Point(459, 78)); // 9
        put(10, new Point(587, 78)); // 10
        put(11, new Point(715, 78)); // 11
        put(12, new Point(971, 333)); // 12
        put(13, new Point(971, 206)); // 13
        put(14, new Point(971, 78)); // 14
        put(15, new Point(1099, 78)); // 15
        put(16, new Point(1099, 206)); // 16
        put(17, new Point(1355, 333)); // 17
        put(18, new Point(1483, 333)); // 18
        put(19, new Point(1611, 333)); // 19
        put(20, new Point(1739, 333)); // 20
        put(21, new Point(1867, 333)); // 21
        put(22, new Point(1355, 206)); // 22
        put(23, new Point(1483, 206)); // 23
        put(24, new Point(1611, 206)); // 24
        put(25, new Point(1739, 206)); // 25
        put(26, new Point(1867, 206)); // 26
    }};

    @Autowired
    public MapHandler(SeatStorage seatStorage, ImageUtil imageUtil) {
        this.seatStorage = seatStorage;
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
        final List<Seat> seats = getSeats();
        if (seats.isEmpty()) {
            return getErrorMessage(chatId);
        }
        final InputStream image = imageUtil.fillSeats(seats);
        return SendPhoto.builder()
            .chatId(chatId)
            .parseMode(ParseMode.HTML)
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .keyboard(
                        List.of(
                            new KeyboardRow(List.of(new KeyboardButton("Домой"))),
                            new KeyboardRow(List.of(new KeyboardButton("Бронирование")))
                        )
                    ).build()
            )
            .photo(new InputFile(image, "image.png"))
            .caption(getPretty())
            .build();
    }

    private List<Seat> getSeats() {
        return seatStorage.getSeats()
            .entrySet()
            .stream()
            .sorted(Comparator.comparingInt(Map.Entry::getKey))
            .map(it -> new Seat(it.getKey(), it.getValue(), coordinates.get(it.getKey())))
            .collect(Collectors.toList());
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
            "Чтобы узнать через сколько освободятся нужны вам места - пишите в Инстаграм <a href='https://www.instagram.com/mvp_kst/'>@mvp_kst</a>";
    }
}