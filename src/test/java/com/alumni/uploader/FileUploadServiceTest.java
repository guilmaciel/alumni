package com.alumni.uploader;

import com.alumni.exception.BadFileException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 */

@SpringBootTest
public class FileUploadServiceTest {


    @Autowired
    private FileUploadService service;

    @MockBean
    MultipartFile file;
    @Test
    public void testUploadFileFileProvided(){
         file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "host,1,1,1|1,1,1,1".getBytes());
        service.uploadFile(file);
    }

    @Test
    public void testUploadFileIOException() throws IOException {
        Mockito.doThrow(IOException.class).when(file).getInputStream();
        assertThrows(BadFileException.class, () -> service.uploadFile(file));
    }

    @Test
    public void testUploadFileNoFileProvided(){
        service.uploadFile(null);
    }

}