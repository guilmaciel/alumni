package com.alumni.processor;

import lombok.Data;

import java.util.List;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-11
 *
 */

@Data
public class FileDataDto {

    private String serverName;
    private List<String> serverStatistics;

}
