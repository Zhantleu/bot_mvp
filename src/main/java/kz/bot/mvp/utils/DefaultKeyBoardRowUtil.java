package kz.bot.mvp.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

@Component
public class DefaultKeyBoardRowUtil {
    public static final List<KeyboardRow> DEFAULT_KEYBOARD = List.of(
        new KeyboardRow(
            List.of(
                new KeyboardButton("Карта клуба")
            )
        ),
        new KeyboardRow(
            Arrays.asList(
                new KeyboardButton("Тарифы"),
                new KeyboardButton("Периферия")
            )
        )
    );

}
