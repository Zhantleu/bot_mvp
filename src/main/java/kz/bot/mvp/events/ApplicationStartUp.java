package kz.bot.mvp.events;

import kz.bot.mvp.polling.MvpBot;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class ApplicationStartUp implements ApplicationListener<ApplicationEvent> {
    private final MvpBot mvpBot;

    public ApplicationStartUp(MvpBot mvpBot) {
        this.mvpBot = mvpBot;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
//        try {
//
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
    }
}
