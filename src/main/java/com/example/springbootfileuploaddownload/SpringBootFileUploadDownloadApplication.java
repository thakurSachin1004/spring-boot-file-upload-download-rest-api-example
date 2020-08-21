package com.example.springbootfileuploaddownload;

import com.example.springbootfileuploaddownload.fileConfiguration.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class SpringBootFileUploadDownloadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFileUploadDownloadApplication.class, args);
	}

}
