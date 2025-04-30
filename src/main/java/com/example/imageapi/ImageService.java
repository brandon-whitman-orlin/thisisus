package com.example.imageapi;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private static final String DEFAULT_FOLDER = "src/main/resources/images/default";
    private static final String THREE_FOLDER = "src/main/resources/images/three";

    public String getRandomImage(String number) throws IOException {
        String folderPath;
    
        if (number == null || number.isEmpty()) {
            folderPath = DEFAULT_FOLDER;
        } else if ("3".equals(number)) {
            folderPath = THREE_FOLDER;
        } else {
            throw new IllegalArgumentException("Unsupported number parameter: " + number);
        }
    
        List<File> files = Files.walk(Path.of(folderPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
    
        if (files.isEmpty()) {
            throw new NoSuchFileException("No images found in folder: " + folderPath);
        }
    
        return files.get(new Random().nextInt(files.size())).getAbsolutePath();
    }
    
}
