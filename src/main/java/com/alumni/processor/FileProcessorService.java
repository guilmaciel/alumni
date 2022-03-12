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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.alumni.utils.DataProcessorUtils.getFileDataDto;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 * Service class that will handle the file processing
 *
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class FileProcessorService {

    @Value("classpath:input-file.csv")
    Resource FILE;

    private final HostDataRepository repository;


    /**
     * gets the InputStream from the internal and calls the method responsible for processing it.
     */
    public void processDataFromInternalFile() {

        try {
            processData(FILE.getInputStream());
        } catch (IOException e) {
            log.error("Error processing internal file.", e);
            throw new BadFileException("Error processing the internal host file.");
        }

    }

    /**
     * receives an InputStream and calls the method responsible for processing it.
     *
     * @param inputStream the input stream from the external file.
     */
    public void processDataFromExternalFile(InputStream inputStream) {
        processData(inputStream);
    }


    /**
     * Saves the processed InputStream and saves the List of HostDataDto into the database.
     *
     * @param inputStream the InputStream from the given file.
     * @throws BadFileException if there an any errors processing the InputStream.
     */

    private void processData(InputStream inputStream) {
        try{
            List<HostDataDto> dtoList = processInputStream(inputStream).stream().map(DataProcessorUtils::getHostDataDto).collect(Collectors.toList());
            saveData(dtoList);
        } catch (IOException e) {
            log.error("Error processing external file.", e);
            throw new BadFileException("Error processing the external host file.");
        }

    }


    /**
     * Converts the file content to a list of FileDataDto objects
     *
     * @param inputStream from the file to be processed
     * @return a List of FileDataDto
     * @throws IOException in case of error processing the InputStream
     */
    private List<FileDataDto> processInputStream(InputStream inputStream) throws IOException {
        List<FileDataDto> dataList = new ArrayList<>();

        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = fileReader.readLine()) != null){
            dataList.add(getFileDataDto(line));
        }
        return dataList;
    }

    /**
     * Saves the provided list of HostDataDto into the Database.
     *
     * @param dtos list of HostDataDto to be saved in the database.
     */
    private void saveData(List<HostDataDto> dtos){
        repository.saveAllAndFlush(dtos.stream().map(DataProcessorUtils::dtoToEntity).collect(Collectors.toList()));
    }



}
