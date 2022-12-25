package kz.bot.mvp.utils;

import kz.bot.mvp.models.Seat;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;


@Component
public class ImageUtil {

    private final static int LINE_HEIGHT = 108;
    private final static int LINE_WIDTH = 8;
    private final static InputFile SEATS =
        new InputFile(ImageUtil.class.getResourceAsStream("seats.png"), "seats.png");

    @SneakyThrows
    public InputStream fillSeats(List<Seat> seats) {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
             ByteArrayOutputStream copyBytes = new ByteArrayOutputStream();
             ImageOutputStream output = new MemoryCacheImageOutputStream(bytes);
             ImageOutputStream copy = new MemoryCacheImageOutputStream(copyBytes)) {
            BufferedImage img = ImageIO.read(new BufferedInputStream(SEATS.getNewMediaStream()));
            ImageIO.write(img, "png", copy);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(copyBytes.toByteArray());
            BufferedImage imgCopy = ImageIO.read(new BufferedInputStream(byteArrayInputStream));
            Graphics2D graph = imgCopy.createGraphics();
            for (Seat seat : seats) {
                graph.setColor(seat.getColor());

                final int x = seat.getStart().getX();
                final int y = seat.getStart().getY();

                graph.fill(new Rectangle(x, y, LINE_WIDTH, LINE_HEIGHT));
                graph.fill(new Rectangle(x, y, LINE_HEIGHT, LINE_WIDTH));
                graph.fill(new Rectangle(x, y + LINE_HEIGHT - 5, LINE_HEIGHT, LINE_WIDTH));
                graph.fill(new Rectangle(x + LINE_HEIGHT - 5, y, LINE_WIDTH, LINE_HEIGHT + 3));
            }
            graph.dispose();
            ImageIO.write(imgCopy, "png", output);
            return new ByteArrayInputStream(bytes.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        throw new RuntimeException("Could not edit image!");
    }
}
