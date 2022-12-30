package kz.bot.mvp.storage;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserCountStorage {
    private final Set<String> chatIds = ConcurrentHashMap.newKeySet();


    public void update(String chatId) {
        chatIds.add(chatId);
    }

    public int getCount() {
        return chatIds.size();
    }

    public void reset() {
        chatIds.clear();
    }
}
