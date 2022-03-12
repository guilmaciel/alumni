package com.alumni.processor;

import lombok.Data;

import java.util.List;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 * DTO for the file entries
 *
 */

@Data
public class FileDataDto {

    private String host;
    private List<String> hostStatistics;
}
