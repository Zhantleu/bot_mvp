package kz.bot.mvp.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

//@Data
@ConfigurationProperties(prefix = "bot")
@ConfigurationPropertiesScan
//@AllArgsConstructor
public class BotProperty {
    private String token;
    private String username;


    public BotProperty(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BotProperty() {
    }
}
