package com.alumni.batch;

import com.alumni.exception.BadDataException;
import com.alumni.host.HostDataEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-12
 * Last Update: 2022-03-12
 *
 * Custom ItemProcessor for the FileDataDto
 *
 */

@Slf4j
public class AlumniItemProcessor implements ItemProcessor<FileDataDto, HostDataEntity> {

    /**
     * Implementation of the process method. with the specific logic to handle the data from the HostFile,
     * already converted into a FileDataDto into a HostDataEntity, that will be saved into the database.
     *
     * @param dto FileDataDto to be processed
     * @return HostDataEntity to be saved into the database.
     * @throws Exception
     */
    @Override
    public HostDataEntity process(FileDataDto dto) throws Exception {
        HostDataEntity entity = new HostDataEntity();

        entity.setHost(dto.getHost());

        try{

            double average = dto.getHostStatistics().stream().filter(s -> !s.equalsIgnoreCase("None")).mapToDouble(Double::valueOf).average().orElse(Double.NaN);
            double min = dto.getHostStatistics().stream().filter(s -> !s.equalsIgnoreCase("None")).mapToDouble(Double::valueOf).min().orElse(Double.NaN);
            double max = dto.getHostStatistics().stream().filter(s -> !s.equalsIgnoreCase("None")).mapToDouble(Double::valueOf).max().orElse(Double.NaN);

            entity.setAverageValue(average);
            entity.setMinValue(min);
            entity.setMaxValue(max);

        }catch (NumberFormatException ex){
            log.error("Error processing number", ex);
            throw new BadDataException("Error processing number");
        }

        return entity;
    }
}
