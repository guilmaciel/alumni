package com.alumni.batch;

import com.alumni.host.HostDataEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AlumniItemProcessorTest {

    @Autowired
    AlumniItemProcessor processor;

    @Test
    public void test() throws Exception {
        HostDataEntity entity = processor.process(getFileDataDto("host", "1"));
        assertEquals("host", entity.getHost());
    }


    private static FileDataDto getFileDataDto(String host, String... params){

        FileDataDto dto = new FileDataDto();

        dto.setHost(host);
        dto.setHostStatistics(Arrays.asList(params));
        return dto;
    }
}