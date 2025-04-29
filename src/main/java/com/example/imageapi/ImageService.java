package com.example.imageapi;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private static final String DEFAULT_FOLDER = "src/main/resources/images/default";
    private static final String THREE_FOLDER = "src/main/resources/images/three";

    public String getRandomImage(String number) throws IOException {
        // If no folder number is provided, use the default folder
        if (number == null || number.isEmpty()) {
            number = "default";
        }

        // Set folder path based on the folder parameter
        String folderPath = switch (number) {
            case "3" -> THREE_FOLDER;  // Mapping number "3" to the "three" folder
            default -> DEFAULT_FOLDER; // Default folder for any other number
        };

        // Get all files from the folder
        List<File> files = Files.walk(Path.of(folderPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        if (files.isEmpty()) {
            return "No images found in folder: " + folderPath;
        }

        // Select a random file
        Random random = new Random();
        File randomImage = files.get(random.nextInt(files.size()));

        // Return the path of the random image
        return randomImage.getAbsolutePath();
    }
}
