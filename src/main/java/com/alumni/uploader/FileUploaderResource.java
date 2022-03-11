package com.alumni.uploader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Last Update: 2022-03-11
 *
 */

@Slf4j
@RestController
@RequestMapping("upload")
public class FileUploaderResource {

    @Autowired
    FileUploadService service;
    @PostMapping
    public ResponseEntity<Object> uploadFile(@RequestParam(value = "File", required = false)MultipartFile file){

        service.uploadFile(file);

        return ResponseEntity.ok("File successfully processed.");
    }

}
