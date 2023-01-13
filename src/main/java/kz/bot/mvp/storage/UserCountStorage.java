package kz.bot.mvp.storage;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserCountStorage {
    private final Set<String> usernames = ConcurrentHashMap.newKeySet();


    public void update(String username) {
        usernames.add(username);
    }

    public Set<String> getUsernames() {
        return usernames;
    }

    public void reset() {
        usernames.clear();
    }
}
