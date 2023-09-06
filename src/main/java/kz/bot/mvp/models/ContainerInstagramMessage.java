package kz.bot.mvp.models;

import lombok.Data;

import java.util.List;

@Data
public class ContainerInstagramMessage {
    private List<InstagramMessageDto> data;
}
