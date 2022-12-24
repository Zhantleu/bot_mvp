package kz.bot.mvp.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seats")
public class SeatController {
    @PostMapping
    @CrossOrigin(origins = "https://mvpkst.admin.enes.tech")
    public void save() {
        System.out.println("save");
    }
}
