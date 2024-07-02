package com.tim_ohagan.thumbnail;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64; 


@SpringBootApplication
@RestController
public class ThumbnailApplication {

    @GetMapping("/generate")
        public String generateThumbnail(@RequestParam String imageUrl, 
                                        @RequestParam int width, 
                                        @RequestParam int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(new URL(imageUrl));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(width, height)
                .outputFormat("png")
                .toOutputStream(baos);
        byte[] thumbnailBytes = baos.toByteArray();
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(thumbnailBytes);
    }

    public static void main(String[] args) {
        SpringApplication.run(ThumbnailApplication.class, args);
    }
}