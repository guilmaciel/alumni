package com.alumni.processor;

import com.alumni.exception.BadFileException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 */

@SpringBootTest
public class FileProcessorServiceTest {

    @Autowired
    private FileProcessorService service;

    @MockBean
    private InputStream inputStream;// = Mockito.mock(BufferedReader.class);

    @Test
    public void test() throws IOException {

        Mockito.doThrow(new IOException("Mocked")).when(inputStream).read();
        assertThrows(BadFileException.class,() -> service.processDataFromExternalFile(inputStream));
    }

}