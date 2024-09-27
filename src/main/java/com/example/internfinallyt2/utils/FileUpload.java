package com.example.internfinallyt2.utils;

import com.example.internfinallyt2.exception.customValidation.CustomIOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUpload {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file, String oldFilePath){
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (file != null && file.getSize() > 0) {
            UUID uuid = UUID.randomUUID();
            String newFileName = uuid + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, newFileName);
            try {
                if(oldFilePath == null) {
                    Files.write(path, file.getBytes());
                    return "/uploadImage/" + newFileName;
                } else {
                    if(deleteFile(oldFilePath)) {
                        Files.write(path, file.getBytes());
                        return "/uploadImage/" + newFileName;
                    } else {
                        return oldFilePath;
                    }
                }
            } catch (IOException e) {
                throw new CustomIOException(e);
            }
        }
        return oldFilePath;
    }


    public boolean deleteFile(String urlFile) {
        File file = new File( "." + urlFile);
        if (file.exists()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
