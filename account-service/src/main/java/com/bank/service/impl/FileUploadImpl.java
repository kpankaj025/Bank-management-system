package com.bank.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bank.dto.FileData;
import com.bank.service.FileUpload;

@Service
public class FileUploadImpl implements FileUpload {

    @Override
    public FileData uplaodFile(MultipartFile file, String path) throws Exception {

        if (path.isBlank()) {
            throw new RuntimeException("path is empty");
        }

        Path folderPath = Paths.get(path.substring(0, path.lastIndexOf("/")));
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        Path filePath = Paths.get(path);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String fileName = path.substring(path.lastIndexOf("/") + 1);
        FileData fileData = new FileData(fileName, path);
        return fileData;
    }

    @Override
    public Resource loadFile(String filePath) throws Exception {
        // TODO Auto-generated method stub
        Path path = Paths.get(filePath).normalize();
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists()) {
            throw new RuntimeException("data not found");

        }
        return resource;
    }

}
