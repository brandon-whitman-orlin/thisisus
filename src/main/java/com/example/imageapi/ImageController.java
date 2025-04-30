package com.example.imageapi;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/image")
    public ResponseEntity<?> getImage(@RequestParam(required = false) Integer number) {
        try {
            String imagePath = imageService.getRandomImage(number != null ? String.valueOf(number) : null);
            File imageFile = new File(imagePath);

            if (!imageFile.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(imageFile.toPath());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(imageFile));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageFile.getName() + "\"")
                    .contentType(contentType != null
                            ? MediaType.parseMediaType(contentType)
                            : MediaType.IMAGE_JPEG)
                    .contentLength(imageFile.length())
                    .body(resource);

        } catch (NoSuchFileException e) {
            return ResponseEntity.status(404).body("No images found for the specified folder.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("An error occurred while loading the image.");
        }
    }
}
