package kz.bot.mvp.handlers;

import kz.bot.mvp.models.ChatEntity;
import kz.bot.mvp.repositories.ChatRepository;
import kz.bot.mvp.utils.DefaultKeyBoardRowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShowAllUsers implements Handler {
    private final ChatRepository chatRepository;

    @Autowired
    public ShowAllUsers(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public PartialBotApiMethod<Message> process(String chatId) {
        final List<ChatEntity> chats = chatRepository.findAll();
        return SendMessage.builder()
            .chatId(chatId)
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .keyboard(DefaultKeyBoardRowUtil.DEFAULT_KEYBOARD).build()
            ).text(
                "Пользователи:\n" + chats.stream().map(ChatEntity::getUsername).collect(Collectors.joining("\n"))
            )
            .build();

    }

    @Override
    public List<String> getUserCommands() {
        return List.of("/showallusers");
    }
    @Override
    public boolean isForAdmin() {
        return true;
    }
}