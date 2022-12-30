package kz.bot.mvp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Data
@ConfigurationProperties(prefix = "bot")
@ConfigurationPropertiesScan
public class BotProperty {
    private String token;
    private String username;

    private Long adminGroup;

    public BotProperty(String token, String username, Long adminGroup) {
        this.token = token;
        this.username = username;
        this.adminGroup = adminGroup;
    }
}
