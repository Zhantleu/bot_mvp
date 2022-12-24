package kz.bot.mvp.storage;

import kz.bot.mvp.models.SeatDto;
import kz.bot.mvp.models.SeatStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SeatStorage {
    private final static Map<Integer, SeatStatus> STORAGE = new ConcurrentHashMap<>();

    public void save(List<SeatDto> seats) {
        synchronized (this) {
            for (SeatDto seat : seats) {
                STORAGE.put(seat.number, SeatStatus.valueOf(seat.status.toUpperCase()));
            }
        }
    }

    public HashMap<Integer, SeatStatus> getSeats() {
        return new HashMap<>(STORAGE);
    }
}