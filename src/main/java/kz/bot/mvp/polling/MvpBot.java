package kz.bot.mvp.polling;

import kz.bot.mvp.handlers.Handler;
import kz.bot.mvp.properties.BotProperty;
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
//    private final MessageRepository messageRepository;
//    private final ChatRepository chatRepository;


    @Autowired
    public MvpBot(List<Handler> handlers,
                  BotProperty botProperty
//                  MessageRepository messageRepository,
//                  ChatRepository chatRepository) {
    ) {
        this.handlers = handlers;
        this.botProperty = botProperty;
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
            final String userName = update.getMessage().getFrom().getUserName();
            final Long chatId = update.getMessage().getChatId();
            final String text = update.getMessage().getText();
//            updateChat(userName, chatId, text);
            Handler handler = handlers.stream().filter(it -> it.isSuitable(text)).findFirst().orElseThrow();
            if (!handler.isForAdmin()) {
                processMessage(update, handler);
            } else if (userName.equalsIgnoreCase("zxcoder")) {
                processMessage(update, handler);
            }
        }
    }

    void updateChat(String userName, Long chatId, String text) {
//        if (!Objects.equals(chatId, botProperty.getAdminGroupId())) {
//            ChatEntity chat = chatRepository.findByUsername(userName);
//
//            if (chat == null) {
//                chat = new ChatEntity();
//                chat.setUsername(userName);
//                chat.setId(UUID.randomUUID());
//                chat.setChatId(chatId);
//                chat = chatRepository.save(chat);
//            }
//
//            MessageEntity message = new MessageEntity();
//            message.setId(UUID.randomUUID());
//            message.setText(text);
//            message.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Almaty")));
//            message.setChat(chat);
//
//            messageRepository.save(message);
//        }
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