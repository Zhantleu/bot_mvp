package kz.bot.mvp.polling;

import kz.bot.mvp.handlers.Handler;
import kz.bot.mvp.models.StepStatus;
import kz.bot.mvp.properties.BotProperty;
import kz.bot.mvp.storage.StepStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class MvpBot extends TelegramLongPollingBot {
    private final List<Handler> handlers;
    private final BotProperty botProperty;
    private final StepStorage stepStorage;

    private final Handler bookingHandler;

    @Autowired
    public MvpBot(List<Handler> handlers,
                  BotProperty botProperty,
                  StepStorage stepStorage,
                  Handler bookingHandler) {
        this.handlers = handlers;
        this.botProperty = botProperty;
        this.stepStorage = stepStorage;
        this.bookingHandler = bookingHandler;
    }

    @Override
    public String getBotUsername() {
        return botProperty.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperty.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
//            final StepStatus step = stepStorage.getStep(update.getMessage().getChat().getId());
            Handler handler;
//            if (step != null) {
//                handler = bookingHandler;
//            } else {
                handler = handlers.stream().filter(it -> it.isSuitable(update.getMessage().getText())).findFirst()
                    .orElseThrow();
//            }
            processMessage(update, handler);
        }
    }

    private void processMessage(Update update, Handler it) {
        final String chatId = update.getMessage().getFrom().getId().toString();
        final PartialBotApiMethod<Message> message = it.process(chatId);
        try {
            executeMessage(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeMessage(PartialBotApiMethod<Message> partialBotApiMethod) throws TelegramApiException {
        if (partialBotApiMethod != null) {
            if (partialBotApiMethod instanceof SendPhoto) {
                execute((SendPhoto) partialBotApiMethod);
            } else if (partialBotApiMethod instanceof SendMessage) {
                execute((SendMessage) partialBotApiMethod);
            } else {
                throw new IllegalArgumentException("Can not find type: $it");
            }
        }
    }
}