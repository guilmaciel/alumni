package com.alumni.processor;

import com.alumni.exception.BadFileException;
import com.alumni.host.HostDataDto;
import com.alumni.host.HostDataRepository;
import com.alumni.utils.DataProcessorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.alumni.utils.DataProcessorUtils.*;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-11
 *
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class FileProcessorService {

    @Value("classpath:input-file.csv")
    Resource FILE;

    private final HostDataRepository repository;

    public void processDataFromInternalFile() {

        try {
            processData(FILE.getInputStream());
        } catch (IOException e) {
            log.error("Error processing internal file.", e);
            throw new BadFileException("Error processing the internal host file.");
        }

    }
    public void processDataFromExternalFile(InputStream inputStream) {

        try {
            processData(inputStream);
        } catch (IOException e) {
            log.error("Error processing external file.", e);
            throw new BadFileException("Error processing the external host file.");
        }
    }

    private void processData(InputStream inputStream) throws IOException {
        List<HostDataDto> dtoList = processFile(inputStream).stream().map(DataProcessorUtils::getHostDataDto).collect(Collectors.toList());
        saveData(dtoList);
    }

    private List<FileDataDto> processFile(InputStream inputStream) throws IOException {
        List<FileDataDto> dataList = new ArrayList<>();

        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = fileReader.readLine()) != null){
            dataList.add(getFileDataDto(line));
        }
        return dataList;
    }

    private void saveData(List<HostDataDto> dtos){
        repository.saveAllAndFlush(dtos.stream().map(DataProcessorUtils::dtoToEntity).collect(Collectors.toList()));
    }



}
