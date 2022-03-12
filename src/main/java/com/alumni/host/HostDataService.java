package com.alumni.host;

import com.alumni.exception.HostNotFoundException;
import com.alumni.utils.DataProcessorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.alumni.utils.DataProcessorUtils.entityToDto;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 * Service that handles the Host related calls
 */

@Service
@RequiredArgsConstructor
public class HostDataService {

    private final HostDataRepository repository;


    /**
     * Queries the database for a host with a given Id
     *
     * @param id Host id to be searched
     * @return HostDataDto with the given ID
     * @throws HostNotFoundException if no host is found with the given ID.
     */
    public HostDataDto getById(String id){
        return entityToDto(repository.findById(id).orElseThrow(HostNotFoundException::new));
    }

    /**
     * Queries the database for all host entries.
     *
     * @return A List of HostDataDto, with all entries in the database.
     */
    public List<HostDataDto> getAll(){
        List<HostDataEntity> entities = repository.findAll();
        List<HostDataDto> dtos = entities.stream().map(DataProcessorUtils::entityToDto).collect(Collectors.toList());
        return dtos;
    }
}
