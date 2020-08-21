package com.example.springbootfileuploaddownload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<FileStorageErrorResponse> handleException(FileStorageException fileStorageException) {
        FileStorageErrorResponse fileStorageErrorResponse = new FileStorageErrorResponse();
        fileStorageErrorResponse.setMessage(fileStorageException.getMessage());
        fileStorageErrorResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());

        return new ResponseEntity<>(fileStorageErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<FileStorageErrorResponse> handleException(MyFileNotFoundException myFileNotFoundException) {
        FileStorageErrorResponse fileStorageErrorResponse = new FileStorageErrorResponse();
        fileStorageErrorResponse.setMessage(myFileNotFoundException.getMessage());
        fileStorageErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(fileStorageErrorResponse, HttpStatus.NOT_FOUND);
    }
}
