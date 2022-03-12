package com.alumni.utils;

import com.alumni.exception.BadDataException;
import com.alumni.host.HostDataDto;
import com.alumni.host.HostDataEntity;
import com.alumni.processor.FileDataDto;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 */

public class DataProcessorUtilsTest {



    @ParameterizedTest
    @MethodSource("getFileRows")
    public void assertGetFileDataDto(String fileEntry, boolean isValid, FileDataDto expectedValue){

        if(isValid){
            FileDataDto dto = DataProcessorUtils.getFileDataDto(fileEntry);
            assertEquals(expectedValue, dto);
        }else{
            Assertions.assertThrows(BadDataException.class,() -> DataProcessorUtils.getFileDataDto(fileEntry));
        }

    }

    @ParameterizedTest
    @MethodSource("getFileData")
    public void assertGetHostDataDto(FileDataDto dto, HostDataDto expectedValue, boolean isValid){
        if(isValid){
            assertEquals(expectedValue, DataProcessorUtils.getHostDataDto(dto));
        }else{
            assertThrows(BadDataException.class,() -> DataProcessorUtils.getHostDataDto(dto));
        }


    }

    @ParameterizedTest
    @MethodSource("getEntityAndDto")
    public void assertDtoToEntity(HostDataEntity entity, HostDataDto dto){
        assertEquals( entity, DataProcessorUtils.dtoToEntity(dto));
    }

    @ParameterizedTest
    @MethodSource("getEntityAndDto")
    public void assertEntityToDto(HostDataEntity entity, HostDataDto dto){
        assertEquals( dto, DataProcessorUtils.entityToDto(entity) );
    }



    private static FileDataDto getFileDataDto(String host, String... params){

        FileDataDto dto = new FileDataDto();

        dto.setHost(host);
        dto.setHostStatistics(Arrays.asList(params));
        return dto;
    }

    private static HostDataDto getHostDataDto(String host, double average, double min, double max){
        HostDataDto dto = new HostDataDto();
        dto.setHost(host);
        dto.setAverageValue(average);
        dto.setMinValue(min);
        dto.setMaxValue(max);
        return dto;

    }
    
    private static HostDataEntity getHostDataEntity(String host, double average, double min, double max){
        HostDataEntity entity = new HostDataEntity();
        entity.setHost(host);
        entity.setAverageValue(average);
        entity.setMinValue(min);
        entity.setMaxValue(max);
        
        return entity;
        
    }
    private static Stream<Arguments> getFileRows(){
        return Stream.of(

                arguments("host,1,1,1|12.1,12.1,12.1", true, getFileDataDto("host", "12.1", "12.1", "12.1")),
                arguments("host", false, null),
                arguments("host,1,1,1|", false, null),
                arguments("|12.1", false, null),
                arguments(", |12.1", false, null),
                arguments("host| ", false, null),
                arguments(StringUtils.EMPTY, false, null),
                arguments("host,1,1,1|1,b,c,d,e", true,getFileDataDto("host", "1", "b", "c", "d", "e"))
        );
    }
    private static Stream<Arguments> getFileData(){
        return Stream.of(
            arguments(getFileDataDto("host1", "3"), getHostDataDto("host1", 3d, 3d, 3d), true),
            arguments(getFileDataDto("host2", "3","3","4"), getHostDataDto("host2", (10d/3d), 3d, 4d), true),
            arguments(getFileDataDto("host3", "4","5","3"), getHostDataDto("host3", 4d, 3d, 5d), true),
            arguments(getFileDataDto("host4", "4","4","4","8"), getHostDataDto("host4", 5d, 4d, 8d), true),
            arguments(getFileDataDto("host5", "4","4","4",""), null, false),
            arguments(getFileDataDto("host6" ), getHostDataDto("host6", Double.NaN, Double.NaN, Double.NaN), true),
            arguments(getFileDataDto("host7", "1","a","b","c"), null, false)

        );
    }

    private static Stream<Arguments> getEntityAndDto(){
        return Stream.of(
                arguments(getHostDataEntity("host1", 3d, 3d, 3d), getHostDataDto("host1", 3d, 3d, 3d)),
                arguments(getHostDataEntity("host2", (10d/3d), 3d, 4d), getHostDataDto("host2", (10d/3d), 3d, 4d)),
                arguments(getHostDataEntity("host3", 4d, 3d, 5d), getHostDataDto("host3", 4d, 3d, 5d)),
                arguments(getHostDataEntity("host4", 5d, 4d, 8d), getHostDataDto("host4", 5d, 4d, 8d)),
                arguments(getHostDataEntity("host5", 4d, 4d, 4d), getHostDataDto("host5", 4d, 4d, 4d)),
                arguments(getHostDataEntity("host6", Double.NaN, Double.NaN, Double.NaN), getHostDataDto("host6", Double.NaN, Double.NaN, Double.NaN))

        );
    }

}