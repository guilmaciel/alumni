package com.alumni.batch;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-12
 * Last Update: 2022-03-12
 *
 * Batch job properties
 */
@Configuration
@ConfigurationProperties(prefix="batch")
@Data
public class BatchProperties {
    private String path, fileName;

    /** Returns the temp file path.
     *
     * @return String with the path and file name of the temp file to be used by the batch job.
     */
    public String getFilePath(){
        return this.path + this.fileName;
    }

}
