package kz.bot.mvp.storage;

import kz.bot.mvp.models.Point;
import kz.bot.mvp.models.Seat;
import kz.bot.mvp.models.SeatDto;
import kz.bot.mvp.models.SeatStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;

@Component
public class SeatStorage {

    private final static Map<Integer, SeatStatus> STORAGE = new ConcurrentHashMap<>();
    private final Map<Integer, Point> coordinates = new HashMap<>() {{
        put(1, new Point(586, 205)); // 1
        put(2, new Point(457, 205)); // 2
        put(3, new Point(331, 205)); // 3
        put(4, new Point(202, 205)); // 4
        put(5, new Point(75, 205)); // 5
        put(6, new Point(74, 78)); // 6
        put(7, new Point(203, 78)); // 7
        put(8, new Point(331, 78)); // 8
        put(9, new Point(459, 78)); // 9
        put(10, new Point(587, 78)); // 10
        put(11, new Point(715, 78)); // 11
        put(12, new Point(971, 333)); // 12
        put(13, new Point(971, 206)); // 13
        put(14, new Point(971, 78)); // 14
        put(15, new Point(1099, 78)); // 15
        put(16, new Point(1099, 206)); // 16
        put(17, new Point(1355, 333)); // 17
        put(18, new Point(1483, 333)); // 18
        put(19, new Point(1611, 333)); // 19
        put(20, new Point(1739, 333)); // 20
        put(21, new Point(1867, 333)); // 21
        put(22, new Point(1355, 206)); // 22
        put(23, new Point(1483, 206)); // 23
        put(24, new Point(1611, 206)); // 24
        put(25, new Point(1739, 206)); // 25
        put(26, new Point(1867, 206)); // 26
    }};
    private LocalDateTime received;

    public void save(List<SeatDto> seats) {
        synchronized (this) {
            for (SeatDto seat : seats) {
                STORAGE.put(seat.number, SeatStatus.valueOf(seat.status.toUpperCase()));
            }
            received = LocalDateTime.now();
        }
    }

    public List<Seat> getSeats() {
        synchronized (this) {
            if (received == null || ChronoUnit.MINUTES.between(now(), received) > 10) {
                return new ArrayList<>();
            } else {
                return coordinates
                    .entrySet()
                    .stream()
                    .map(it -> new Seat(it.getKey(), STORAGE.get(it.getKey()), it.getValue()))
                    .collect(Collectors.toList());
            }
        }
    }
}