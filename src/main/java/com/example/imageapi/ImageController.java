package com.example.imageapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/image")
    public ResponseEntity<InputStreamResource> getImage(@RequestParam(required = false) Integer number) throws IOException {
        // Get the random image file path using the number parameter
        String imagePath = imageService.getRandomImage(String.valueOf(number));  // Convert number to String for the service method

        // Create the file object for the image
        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            return ResponseEntity.notFound().build(); // If image not found, return 404
        }

        // Get the image content type (MIME type)
        String contentType = Files.probeContentType(imageFile.toPath());

        // Create InputStreamResource to send the image in the response
        InputStreamResource resource = new InputStreamResource(new FileInputStream(imageFile));

        // Return the image as a response entity
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageFile.getName() + "\"")
                .contentType(contentType != null ? MediaType.parseMediaType(contentType) : MediaType.IMAGE_JPEG)
                .contentLength(imageFile.length())
                .body(resource);
    }
}
