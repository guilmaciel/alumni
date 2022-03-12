package com.alumni.host;

import lombok.Data;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 * DTO for the HostData
 *
 */

@Data
public class HostDataDto {

    private String host;
    private double minValue, maxValue, averageValue;

}

