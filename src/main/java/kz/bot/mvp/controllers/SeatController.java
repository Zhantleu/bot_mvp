package kz.bot.mvp.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/seats")
public class SeatController {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody Map<Integer, String> seatsToStatus) {
        System.out.println(seatsToStatus);
    }
}
