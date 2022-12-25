package kz.bot.mvp.storage;

import kz.bot.mvp.models.SeatDto;
import kz.bot.mvp.models.SeatStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SeatStorage {
    private final static Map<Integer, SeatStatus> STORAGE = new ConcurrentHashMap<>();
    private LocalDateTime received;

    public void save(List<SeatDto> seats) {
        synchronized (this) {
            for (SeatDto seat : seats) {
                STORAGE.put(seat.number, SeatStatus.valueOf(seat.status.toUpperCase()));
            }
            received = LocalDateTime.now();
        }
    }

    public HashMap<Integer, SeatStatus> getSeats() {
        if (received == null || received.isBefore(received.plus(10, ChronoUnit.MINUTES))) {
            return new HashMap<>();
        } else {
            return new HashMap<>(STORAGE);
        }
    }
}