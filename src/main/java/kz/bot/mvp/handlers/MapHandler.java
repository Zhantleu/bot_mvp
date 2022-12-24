package kz.bot.mvp.handlers;

import kz.bot.mvp.models.SeatStatus;
import kz.bot.mvp.storage.SeatStorage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapHandler implements Handler {

//    private final static
    private final SeatStorage seatStorage;

    @SneakyThrows
    public void init() {
        InputFile SEATS =
            new InputFile(TariffsHandler.class.getResourceAsStream("/seats.jpg"), "seats.jpg");
        BufferedImage img = ImageIO.read(SEATS.getNewMediaStream());
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, 100, 100);
        g2d.dispose();
        try {
            ImageIO.write(img, "png", SEATS.getNewMediaFile());
        } catch (Exception e) {
            System.out.println("[ERROR] Could not save image.");
        }
    }

    @Autowired
    public MapHandler(SeatStorage seatStorage) {
        this.seatStorage = seatStorage;
    }

    @SneakyThrows
    @Override
    public PartialBotApiMethod<Message> process(String chatId) {
        InputFile SEATS =
            new InputFile(TariffsHandler.class.getResourceAsStream("/seats.jpg"), "seats.jpg");
        BufferedImage img = ImageIO.read(SEATS.getNewMediaStream());
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, 100, 100);
        g2d.dispose();
        try {
            ImageIO.write(img, "png", SEATS.getNewMediaFile());
        } catch (Exception e) {
            System.out.println("[ERROR] Could not save image.");
        }
        return SendPhoto.builder()
            .chatId(chatId)
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .keyboard(
                        List.of(
                            new KeyboardRow(List.of(new KeyboardButton("Домой")))
                        )
                    ).build()
            )
            .photo(SEATS)
            .caption(getPretty())
            .build();
    }

    @Override
    public List<String> getUserCommands() {
        return List.of("Карта клуба");
    }

    private String getPretty() {
        final List<String> freeSeats =
            seatStorage.getSeats().entrySet().stream().filter(seat -> seat.getValue().equals(SeatStatus.FREE))
                .map(integerSeatStatusEntry -> integerSeatStatusEntry.getKey().toString()).collect(Collectors.toList());
        return "Свободные места: " + String.join(",", freeSeats);
    }
}