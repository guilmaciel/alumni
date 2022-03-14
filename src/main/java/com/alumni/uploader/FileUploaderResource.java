package com.alumni.uploader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-13
 *
 * Controller class that handles the rest calls related to file uploading/processing.
 *
 */

@Slf4j
@RestController
@RequestMapping("upload")
public class FileUploaderResource {

    public static final String FILE_SUCCESSFULLY_PROCESSED = "File successfully processed";
    @Autowired
    FileUploadService service;

    public static final String RESPONSE_MESSAGE = "File successfully processed.";

    /**
     * Enables post requests under the /upload endpoint, where a file with hosts information can be uploaded to the system.
     *
     * @param file file provided by the post call.
     * @return Returns a Response entity with HttpStatus Created. The status might be different if errors occur during the file processing.
     */

    @PostMapping
    public ResponseEntity<Object> uploadFile(@RequestParam(value = "File", required = false)MultipartFile file){

        service.uploadFile(file);

        return new ResponseEntity(FILE_SUCCESSFULLY_PROCESSED, HttpStatus.CREATED);
    }
}
