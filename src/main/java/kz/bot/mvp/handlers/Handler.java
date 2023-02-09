package kz.bot.mvp.handlers;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface Handler {
    PartialBotApiMethod<Message> process(String chatId);

    List<String> getUserCommands();

    default boolean isSuitable(String text) {
        return getUserCommands().stream().anyMatch(it -> it.equalsIgnoreCase(text));
    }

    default boolean isForAdmin() {
        return false;
    }
}
