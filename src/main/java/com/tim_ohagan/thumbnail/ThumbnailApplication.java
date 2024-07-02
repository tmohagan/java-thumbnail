package com.tim_ohagan.thumbnail;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@SpringBootApplication
@RestController
public class ThumbnailApplication {

    private static final Logger logger = LoggerFactory.getLogger(ThumbnailApplication.class);

    @GetMapping("/generate")
    public ResponseEntity<String> generateThumbnail(@RequestParam String imageUrl,
                                                    @RequestParam int width,
                                                    @RequestParam int height) {
        logger.info("Received request for image: {}", imageUrl);
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            
            if (responseCode != HttpURLConnection.HTTP_OK) {
                logger.error("Failed to access image URL. Response code: {}", responseCode);
                return ResponseEntity.badRequest().body("Failed to access image URL");
            }

            BufferedImage originalImage = ImageIO.read(url);
            if (originalImage == null) {
                logger.error("Failed to read image from URL: {}", imageUrl);
                return ResponseEntity.badRequest().body("Failed to read image from URL");
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Thumbnails.of(originalImage)
                    .size(width, height)
                    .outputFormat("png")
                    .toOutputStream(baos);
            byte[] thumbnailBytes = baos.toByteArray();
            String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(thumbnailBytes);
            
            return ResponseEntity.ok(base64Image);
        } catch (IOException e) {
            logger.error("Error processing image", e);
            return ResponseEntity.internalServerError().body("Error processing image: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ThumbnailApplication.class, args);
    }
}