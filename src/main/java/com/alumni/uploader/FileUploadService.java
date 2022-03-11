package com.alumni.uploader;

import com.alumni.exception.BadFileException;
import com.alumni.processor.FileProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-11
 *
 */

@Slf4j
@Service
public class FileUploadService {

    @Autowired
    private FileProcessorService fileProcessorService;
    public void uploadFile(MultipartFile uploadedFile){
        if(Objects.isNull(uploadedFile)){
            log.info("No file was provided, processing the internal file.");
            fileProcessorService.processDataFromInternalFile();
        }else{
            log.info("processing file: {}.", uploadedFile.getOriginalFilename());
            try {
                fileProcessorService.processDataFromExternalFile(uploadedFile.getInputStream());
            } catch (IOException e) {
                log.error("Error processing external file.", e);
                throw new BadFileException("Error processing external file");
            }

        }
    }
}
