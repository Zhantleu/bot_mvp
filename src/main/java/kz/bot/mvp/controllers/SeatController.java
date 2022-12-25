package kz.bot.mvp.controllers;

import kz.bot.mvp.models.SeatDto;
import kz.bot.mvp.storage.SeatStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatStorage seatStorage;

    @Autowired
    public SeatController(SeatStorage seatStorage) {
        this.seatStorage = seatStorage;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody List<SeatDto> seats) {
        System.out.println(seats);
        seatStorage.save(seats);
    }
}
