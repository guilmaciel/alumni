package com.alumni.host;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 * Controller class that handles the rest calls related to Host queries
 */

@RestController
@RequestMapping("hosts")
@Slf4j
public class HostDataResource {

    @Autowired
    HostDataService service;
    @GetMapping("/{hostId}")
    public ResponseEntity<HostDataDto> getHostById(@PathVariable("hostId") String hostId){
        return ResponseEntity.ok(service.getById(hostId));
    }

    @GetMapping
    public ResponseEntity<List<HostDataDto>> getAllHosts() {
        return ResponseEntity.ok(service.getAll());
    }
}
