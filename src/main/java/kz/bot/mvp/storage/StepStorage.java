package kz.bot.mvp.storage;

import kz.bot.mvp.models.StepStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StepStorage {
    private final static Map<Long, StepStatus> STORAGE = new ConcurrentHashMap<>();

    public StepStatus getStep(Long chatId) {
        return STORAGE.get(chatId);
    }

    public void saveStep(Long chatId, StepStatus stepStatus) {
        STORAGE.put(chatId, stepStatus);
    }
}
