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
 * Last Update: 2022-03-11
 *
 */

@Service
@RequiredArgsConstructor
public class HostDataService {

    private final HostDataRepository repository;

    public HostDataDto getById(String id){
        return findById(id);
    }

    public List<HostDataDto> getAll(){
        return findAll();
    }

    private HostDataDto findById(String id){
        return entityToDto(repository.findById(id).orElseThrow(HostNotFoundException::new));
    }

    private List<HostDataDto> findAll(){
        List<HostDataEntity> entities = repository.findAll();
        List<HostDataDto> dtos = entities.stream().map(DataProcessorUtils::entityToDto).collect(Collectors.toList());
        return dtos;
    }
}
