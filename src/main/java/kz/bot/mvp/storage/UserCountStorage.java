package kz.bot.mvp.storage;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class UserCountStorage {
    private final Set<String> usernames = ConcurrentHashMap.newKeySet();

    public void update(String username) {
        usernames.add(username);
    }

    public void reset() {
        usernames.clear();
    }
}
