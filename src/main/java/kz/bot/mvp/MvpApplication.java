package kz.bot.mvp;

import kz.bot.mvp.polling.MvpBot;
import kz.bot.mvp.properties.BotProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@ConfigurationPropertiesScan("kz.bot.mvp.properties")
@EnableConfigurationProperties(BotProperty.class)
@EnableScheduling
public class MvpApplication {

    public static void main(String[] args) throws TelegramApiException {
        ConfigurableApplicationContext appContext = SpringApplication.run(MvpApplication.class, args);
        MvpBot bot = appContext.getBean(MvpBot.class);
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

}
