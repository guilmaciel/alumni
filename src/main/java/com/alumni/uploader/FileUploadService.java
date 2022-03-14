
package com.alumni.uploader;

import com.alumni.batch.BatchProperties;
import com.alumni.exception.BadFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-13
 *
 * Service class that will handle which file to be processed by the system. and triggers the batch job
 * that will process the fileee
 *
 */

@Slf4j
@Service
public class FileUploadService {


    public static final String ERROR_MSG = "Error processing the file provided";
    @Autowired
    BatchProperties properties;

    @Autowired
    JobLauncher launcher;

    @Autowired
    Job job;

    @Value("classpath:input-file.csv")
    Resource FILE;
    /**
     * Validates if a valid (not null) file was provided, and based on that fact proceeds with copying the file content to a
     * temp file that will be used by the batch job.
     *
     * @Param uploadedFile MultipartFile send by post call, an internal file will be used in case of null value provided
     *
     */
    public void uploadFile(MultipartFile uploadedFile) {

        File f = new File(properties.getFilePath());

        try {
            if(Objects.isNull(uploadedFile)){
                FileCopyUtils.copy(FILE.getFile(), f);
            }else{
                uploadedFile.transferTo(f);
            }
        } catch (IOException e) {
            log.error("Error trying to copy file {}", properties.getFilePath());
            throw new BadFileException("Error copying the file to the temp folder.");
        }
        try {
            JobExecution execution = launcher.run(job, new JobParameters());

        } catch (Exception e) {
            log.error("Error processing the file provided", e);
            throw new BadFileException(ERROR_MSG);
        }
    }
}
