package com.alumni.host;

import com.alumni.exception.RestResponseEntityExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
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
public class HostDataResourceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private RestResponseEntityExceptionHandler handler;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllEmpty() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hosts")).andExpect(status().isOk()).andReturn();

        String body = result.getResponse().getContentAsString();
        assertNotSame(StringUtils.EMPTY, body);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/upload")).andExpect(status().isCreated());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hosts")).andExpect(status().isOk()).andReturn();

        String body = result.getResponse().getContentAsString();
        assertNotSame(StringUtils.EMPTY, body);
    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hosts/n110")).andExpect(status().isNotFound()).andReturn();

        String body = result.getResponse().getContentAsString();
        assertEquals("No information related to the provided host was found.", body);
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/upload")).andExpect(status().isCreated());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hosts/n10")).andExpect(status().isOk()).andReturn();

        String body = result.getResponse().getContentAsString();
        assertNotSame(StringUtils.EMPTY, body);
    }
}