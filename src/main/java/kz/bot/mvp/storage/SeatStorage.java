package kz.bot.mvp.storage;

import kz.bot.mvp.models.Point;
import kz.bot.mvp.models.Seat;
import kz.bot.mvp.models.SeatDto;
import kz.bot.mvp.models.SeatStatus;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class SeatStorage {

    private final static Map<Integer, SeatStatus> STORAGE = new ConcurrentHashMap<>();
    private final Map<Integer, Point> coordinates = new HashMap<>() {{
        put(1, new Point(110, 100)); // 1
        put(2, new Point(110, 223)); // 2
        put(3, new Point(360, 223)); // 3
        put(4, new Point(360, 100)); // 4
        put(6, new Point(1120, 100)); // 6
        put(7, new Point(1245, 100)); // 7
        put(8, new Point(1370, 100)); // 8
        put(9, new Point(1495, 100)); // 9
        put(10, new Point(1620,100)); // 10
        put(11, new Point(1745,100)); // 11
        put(12, new Point(740, 350)); // 12
        put(13, new Point(740, 223)); // 13
        put(14, new Point(740, 100)); // 14
        put(15, new Point(865, 100)); // 15
        put(16, new Point(865, 223)); // 16
        put(17, new Point(1245, 350)); // 17
        put(18, new Point(1370, 350)); // 18
        put(19, new Point(1495, 350)); // 19
        put(20, new Point(1620, 350)); // 20
        put(21, new Point(1745, 350)); // 21
        put(22, new Point(1245, 223)); // 22
        put(23, new Point(1370, 223)); // 23
        put(24, new Point(1495, 223)); // 24
        put(25, new Point(1620, 223)); // 25
        put(26, new Point(1745, 223)); // 26
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