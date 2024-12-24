package com.poly.serviceimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TestImageService {
   
    
    @Value("${upload.dir}")
    private String uploadDir;
    public void uploadImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFilename = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(originalFilename);

        if (!Files.exists(filePath)) {
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath);
            }
        } else {
        }
    }
}

