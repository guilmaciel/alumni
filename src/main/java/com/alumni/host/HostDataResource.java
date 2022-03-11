package com.alumni.host;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-11
 *
 */

@RestController
@RequestMapping("hosts")
public class HostDataResource {

    @Autowired
    HostDataService service;
    @GetMapping("/{hostId}")
    public ResponseEntity<HostDataDto> getStatus(@PathVariable("hostId") String serverId){
        return ResponseEntity.ok(service.getById(serverId));
    }

    @GetMapping
    public ResponseEntity<List<HostDataDto>> getProcessedEntries() throws IOException {
        return ResponseEntity.ok(service.getAll());
    }
}
