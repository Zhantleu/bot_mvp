package kz.bot.mvp.handlers;

import kz.bot.mvp.utils.DefaultKeyBoardRowUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

@Component
public class PeripheryHandler implements Handler {
    @Override
    public PartialBotApiMethod<Message> process(String chatId) {
        return SendMessage.builder()
            .chatId(chatId)
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .keyboard(DefaultKeyBoardRowUtil.DEFAULT_KEYBOARD).build()
            ).text(
                "" +
                    "   Общий зал\n" +
                    "Монитор ACER 1920*1080 FullHD 165 Гц\n" +
                    "⌨️ Клавиатура A4Tech Bloody B500N\n" +
                    "\uD83D\uDDB1 Мышь ASUS P305 TUF Gaming M3\n" +
                    "\uD83C\uDFA7 Наушники HyperX Cloud Stinger Core\n" +
                    "\n" +
                    "Вип зал\n" +
                    "\uD83D\uDDA5 Монитор ACER 1920*1080 FullHD 165 Гц\n" +
                    "⌨️ Клавиатура Hyperx Alloy Origins Core\n" +
                    "\uD83D\uDDB1 HyperX Pulsefire Surge\n" +
                    "\uD83C\uDFA7 Наушники Razer Blackshark V2 X \n"
            )
            .build();
    }

    @Override
    public List<String> getUserCommands() {
        return List.of("Периферия");
    }
}
