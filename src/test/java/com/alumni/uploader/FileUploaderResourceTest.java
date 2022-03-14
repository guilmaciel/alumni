package com.alumni.uploader;


import com.alumni.exception.AlumniProcessorException;
import com.alumni.exception.RestResponseEntityExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.alumni.uploader.FileUploaderResource.FILE_SUCCESSFULLY_PROCESSED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.ArgumentMatchers.any;
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
    JobLauncher launcher;

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

    @Test
    public void TestUploadFileJobFails() throws Exception {
        Mockito.doThrow(AlumniProcessorException.class).when(launcher).run(any(), any());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/upload")).andExpect(status().isBadRequest()).andReturn();

        String body = result.getResponse().getContentAsString();
        assertEquals(FileUploadService.ERROR_MSG, body);

    }

}