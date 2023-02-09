package kz.bot.mvp.models;

import java.time.LocalDateTime;

public class MessageDto {
    private final String text;
    private final LocalDateTime createdAt;

    public MessageDto(String text, LocalDateTime createdAt) {
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
