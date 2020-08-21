package com.example.springbootfileuploaddownload.service;

import com.example.springbootfileuploaddownload.exception.FileStorageException;
import com.example.springbootfileuploaddownload.exception.MyFileNotFoundException;
import com.example.springbootfileuploaddownload.fileConfiguration.FileStorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(FileStorageProperties fileStorageProperties) throws FileStorageException {

        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        }
        catch (Exception e){
            throw new FileStorageException("Unable to create Directory for storing uploaded file. Please try again.", e);
        }
    }

    public String uploadFile(MultipartFile file) throws FileStorageException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")){
                throw new FileStorageException("Filename contains invalid path.");
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;

        }
        catch(IOException e) {
            throw new FileStorageException("Unable to store file.");
        }

    }

    public Resource loadFileAsResource(String fileName) throws MyFileNotFoundException {
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            }
            else throw new MyFileNotFoundException("Requested file is not found.");
        }
        catch (MalformedURLException e) {
            throw new MyFileNotFoundException("File not found " + fileName, e);
        }
    }

}
