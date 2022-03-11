package com.alumni.host;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-11
 *
 */

@Entity
@Table(name="HOST_DATA")
@Data
public class HostDataEntity {

    @Id
    private String host;
    private double minValue, maxValue, averageValue;

}
