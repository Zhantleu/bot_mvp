package kz.bot.mvp.handlers;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface Handler {
   default PartialBotApiMethod<Message> process(String chatId) {
       return null;
   }

    default PartialBotApiMethod<Message> process(String chatId, String text) {
        return null;
    }

    List<String> getUserCommands();

    default boolean isSuitable(String text) {
        return getUserCommands().stream().anyMatch(it -> text.toLowerCase().contains(it.toLowerCase()));
    }

    default boolean isForAdmin() {
        return false;
    }
}
