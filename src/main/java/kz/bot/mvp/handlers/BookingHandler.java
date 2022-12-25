package kz.bot.mvp.handlers;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
public class BookingHandler implements Handler {

    @Override
    public PartialBotApiMethod<Message> process(String chatId) {
        return null;
    }

    @Override
    public List<String> getUserCommands() {
        return List.of() ;
    }
}
