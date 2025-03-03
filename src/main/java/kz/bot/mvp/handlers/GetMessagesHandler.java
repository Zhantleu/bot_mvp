package kz.bot.mvp.handlers;

import kz.bot.mvp.models.MessageEntity;
import kz.bot.mvp.services.MessageService;
import kz.bot.mvp.utils.DefaultKeyBoardRowUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GetMessagesHandler implements Handler {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm:ss", new Locale("ru"));

    private final MessageService messageService;

    @Override
    public PartialBotApiMethod<Message> process(String chatId, String text) {
        String[] content = text.split(" ");

        Page<MessageEntity> page = messageService.getAll(content[1],
                PageRequest.of(Integer.parseInt(content[2]), Integer.parseInt(content[3])));

        AtomicInteger count = new AtomicInteger(1);
        return SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(
                        ReplyKeyboardMarkup.builder()
                                .keyboard(DefaultKeyBoardRowUtil.DEFAULT_KEYBOARD).build()
                ).text(
                        "Сообщения от *" + content[1] + "*:\n```\n" +
                                page.getContent().stream()
                                        .map(it -> String.format("%-4s %-20s %s", count.getAndIncrement(), it.getText(), "⏳ " + formatter.format(it.getCreatedAt())))
                                        .collect(Collectors.joining("\n")) +
                                "\n```"
                )
                .parseMode("MarkdownV2")
                .build();
    }

    @Override
    public List<String> getUserCommands() {
        return List.of("messages", "sms", "смс");
    }

    @Override
    public boolean isForAdmin() {
        return true;
    }
}
