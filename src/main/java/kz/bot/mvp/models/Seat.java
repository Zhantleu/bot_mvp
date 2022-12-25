package kz.bot.mvp.models;

import lombok.Data;

import java.awt.*;

@Data
public class Seat {
    private int number;
    private SeatStatus status;
    private Point start;

    public Seat(int number, SeatStatus status, Point start) {
        this.number = number;
        this.status = status;
        this.start = start;
    }

    public Color getColor() {
        if (status.equals(SeatStatus.BUSY)) {
            return new Color(94, 53, 177, 255);
        } else {
            return new Color(76, 175, 80, 255);
        }

    }
}
