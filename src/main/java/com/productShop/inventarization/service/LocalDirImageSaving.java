package com.productShop.inventarization.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalDirImageSaving implements ImageSavingStrategy {
    @Value("${image.basepath}")
    private String DIR_PATH;

    @Override
    public String saveImage(MultipartFile image) {
        if (image.isEmpty()) {
            throw new IllegalStateException("Cannot save an empty file.");
        }

        // Ensure the directory exists
        File directory = new File(DIR_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate a unique file name
        String fileName = image.getOriginalFilename();  // Get original file name
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String savedFileName = UUID.randomUUID() + fileExtension; // Append file extension
        File targetFile = new File(DIR_PATH, savedFileName);

        try {
            // Copy file to the target location using NIO Files class
            Path path = targetFile.toPath();
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + savedFileName, e);
        }
    }
}
