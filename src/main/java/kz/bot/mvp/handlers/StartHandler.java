package kz.bot.mvp.handlers;

import kz.bot.mvp.utils.DefaultKeyBoardRowUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

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
                    .keyboard(DefaultKeyBoardRowUtil.DEFAULT_KEYBOARD).build()
            ).build();
    }

    @Override
    public List<String> getUserCommands() {
        return List.of("/start", "Домой");
    }
}
