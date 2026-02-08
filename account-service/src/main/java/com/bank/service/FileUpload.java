package com.bank.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.bank.dto.FileData;

public interface FileUpload {

    FileData uplaodFile(MultipartFile file, String path) throws Exception;

    Resource loadFile(String filePath) throws Exception;

}
