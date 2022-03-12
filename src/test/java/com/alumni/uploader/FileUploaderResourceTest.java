package com.alumni.uploader;


import com.alumni.exception.AlumniProcessorException;
import com.alumni.exception.BadDataException;
import com.alumni.exception.BadFileException;
import com.alumni.exception.RestResponseEntityExceptionHandler;
import com.alumni.processor.FileProcessorService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.stream.Stream;

import static com.alumni.uploader.FileUploaderResource.FILE_SUCCESSFULLY_PROCESSED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 */

@SpringBootTest
@EnableWebMvc
public class FileUploaderResourceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private RestResponseEntityExceptionHandler handler;

    @MockBean
    private FileProcessorService service;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private final MockMultipartFile file = new MockMultipartFile(
            "File",
            "hello.txt",
            MediaType.MULTIPART_FORM_DATA_VALUE,
            "host,1,1,1|1,1,1,1".getBytes());

    @Test
    public void testUploadFileHappyPath() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/upload")).andExpect(status().isCreated()).andReturn();

        String body = result.getResponse().getContentAsString();
        assertEquals(FILE_SUCCESSFULLY_PROCESSED, body);

        result = mockMvc.perform(MockMvcRequestBuilders.get("/hosts")).andExpect(status().isOk()).andReturn();

        body = result.getResponse().getContentAsString();
        assertNotSame(StringUtils.EMPTY, body);

    }

    @ParameterizedTest
    @MethodSource("getExceptions")
    public void test(Throwable throwable, HttpStatus status) throws Exception {
        Mockito.doThrow(throwable).when(service).processDataFromInternalFile();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/upload")).andExpect(status().is(status.value())).andReturn();

        String body = result.getResponse().getContentAsString();
        assertEquals(throwable.getMessage(), body);

    }

    private static Stream<Arguments> getExceptions(){
        return Stream.of(

                arguments(new BadFileException("bad file."), HttpStatus.BAD_REQUEST),
                arguments(new BadDataException("bad data."), HttpStatus.BAD_REQUEST),
                arguments(new AlumniProcessorException("generic exception."), HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }


}