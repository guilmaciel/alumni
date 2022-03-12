package com.alumni.utils;

import com.alumni.exception.BadDataException;
import com.alumni.host.HostDataDto;
import com.alumni.host.HostDataEntity;
import com.alumni.processor.FileDataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class DataProcessorUtils {

    private static String PIPE = "\\|";

    public static FileDataDto getFileDataDto(String line) {
        isValidEntry(line);

        String hostSection = line.split(PIPE)[0];
        String statisticsSection = line.split(PIPE)[1];

        String hostName = hostSection.split(",")[0];
        if(StringUtils.isBlank(hostName)){
            log.error("Invalid host value: {}.", hostName);
            throw new BadDataException("Invalid Host value");
        }
        List<String> values;

        values = Stream.of(statisticsSection.split(",")).filter(s -> !s.equalsIgnoreCase("None")).collect(Collectors.toList());

        FileDataDto fileData = new FileDataDto();
        fileData.setHost(hostName);
        fileData.setHostStatistics(values);
        return fileData;


    }

    private static void isValidEntry(String line){
        if(!line.contains("|"))
            throw new BadDataException("Entry doesn't match expected pattern. No separator found.");

        String[] lineValues = line.split(PIPE);
        if(lineValues.length < 2)
            throw new BadDataException("Entry doesn't match expected pattern. Sections missing from an entry.");
        if(StringUtils.isBlank(lineValues[0]) || StringUtils.isBlank(lineValues[1]))
            throw new BadDataException("Entry doesn't match expected pattern. Blank sections found.");

    }

    public static HostDataDto getHostDataDto(FileDataDto fileData) {
        HostDataDto hostDataDto = new HostDataDto();

        hostDataDto.setHost(fileData.getHost());
        try{

            double average = fileData.getHostStatistics().stream().mapToDouble(Double::valueOf).average().orElse(Double.NaN);
            double min = fileData.getHostStatistics().stream().mapToDouble(Double::valueOf).min().orElse(Double.NaN);
            double max = fileData.getHostStatistics().stream().mapToDouble(Double::valueOf).max().orElse(Double.NaN);

            hostDataDto.setAverageValue(average);
            hostDataDto.setMinValue(min);
            hostDataDto.setMaxValue(max);

        }catch (NumberFormatException ex){
            log.error("Error processing number", ex);
            throw new BadDataException("Error Processing Number");
        }
        return hostDataDto;
    }

    public static HostDataDto entityToDto(HostDataEntity entity){
        HostDataDto dto = new HostDataDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public static HostDataEntity dtoToEntity(HostDataDto dto){
        HostDataEntity entity = new HostDataEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

}
