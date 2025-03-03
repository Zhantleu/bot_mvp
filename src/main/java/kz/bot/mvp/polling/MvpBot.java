package kz.bot.mvp.polling;

import kz.bot.mvp.handlers.Handler;
import kz.bot.mvp.models.ChatEntity;
import kz.bot.mvp.models.MessageEntity;
import kz.bot.mvp.properties.BotProperty;
import kz.bot.mvp.repositories.ChatRepository;
import kz.bot.mvp.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class MvpBot extends TelegramLongPollingBot {
    private final List<Handler> handlers;
    private final BotProperty botProperty;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;


    @Override
    public String getBotUsername() {
        return botProperty.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperty.getToken();
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final String userName = update.getMessage().getFrom().getUserName();
            final Long chatId = update.getMessage().getChatId();
            final String text = update.getMessage().getText();
            updateChat(userName, chatId, text);
            Handler handler = handlers.stream().filter(it -> it.isSuitable(text)).findFirst().orElseThrow();
            if (!handler.isForAdmin()) {
                processMessage(update, handler, text);
            } else if (userName.equalsIgnoreCase("zxcoder")) {
                processMessage(update, handler, text);
            }
        }
    }

    void updateChat(String userName, Long chatId, String text) {
        if (!Objects.equals(chatId, botProperty.getAdminGroupId())) {
            ChatEntity chat = chatRepository.findByUsername(userName);

            if (chat == null) {
                chat = new ChatEntity();
                chat.setUsername(userName);
                chat.setId(UUID.randomUUID());
                chat.setChatId(chatId);
                chat = chatRepository.save(chat);
            }

            MessageEntity message = new MessageEntity();
            message.setId(UUID.randomUUID());
            message.setText(text);
            message.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Almaty")));
            message.setChat(chat);

            messageRepository.save(message);
        }
    }

    private void processMessage(Update update, Handler it, String text) {
        final String chatId = update.getMessage().getFrom().getId().toString();
        PartialBotApiMethod<Message> message = it.process(chatId);
        if (message == null) {
            message = it.process(chatId, text);
        }
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