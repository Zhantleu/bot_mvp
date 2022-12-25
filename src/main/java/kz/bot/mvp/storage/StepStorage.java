package kz.bot.mvp.storage;

import kz.bot.mvp.models.SeatStatus;
import kz.bot.mvp.models.StepStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StepStorage {
    private final static Map<Integer, StepStatus> STORAGE = new ConcurrentHashMap<>();

}
