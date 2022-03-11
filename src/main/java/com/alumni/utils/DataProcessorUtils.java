package com.alumni.utils;

import com.alumni.host.HostDataDto;
import com.alumni.host.HostDataEntity;
import com.alumni.processor.FileDataDto;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProcessorUtils {

    public static FileDataDto getFileDataDto(String line){
        String serverName = line.split(",")[0];
        List<String> values = Arrays.asList();
        values = Stream.of(line.split("\\|")[1].split(",")).filter(s -> !s.equalsIgnoreCase("None")).collect(Collectors.toList());

        FileDataDto fileData = new FileDataDto();
        fileData.setServerName(serverName);
        fileData.setServerStatistics(values);
        return fileData;
    }

    public static HostDataDto getHostDataDto(FileDataDto fileData) {
        HostDataDto hostDataDto = new HostDataDto();

        hostDataDto.setHost(fileData.getServerName());

        double average = fileData.getServerStatistics().stream().mapToDouble(Double::valueOf).average().orElse(Double.NaN);
        double min = fileData.getServerStatistics().stream().mapToDouble(Double::valueOf).min().orElse(Double.NaN);
        double max = fileData.getServerStatistics().stream().mapToDouble(Double::valueOf).max().orElse(Double.NaN);

        hostDataDto.setAverageValue(average);
        hostDataDto.setMinValue(min);
        hostDataDto.setMaxValue(max);

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
