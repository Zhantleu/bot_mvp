package kz.bot.mvp.models;

import lombok.Data;

@Data
public class InstagramMessageDto {
    private String name;
    private Status status;

    public InstagramMessageDto() {
    }

    public InstagramMessageDto(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public enum Status {
        NOT_ANSWERED, ANSWERED
    }
}
