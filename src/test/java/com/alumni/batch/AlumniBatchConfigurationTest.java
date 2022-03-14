package com.alumni.batch;

import com.alumni.host.HostDataEntity;
import com.alumni.host.HostDataRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.FileCopyUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AlumniBatchConfigurationTest {

    @Autowired
    JobLauncher launcher;

    @Autowired
    Job job;

    @Autowired
    HostDataRepository repository;

    @Autowired
    BatchProperties properties;



    @Test
    public void testReader() throws Exception {
        File f = new File(properties.getFilePath());
        f.createNewFile();
        FileCopyUtils.copy("host0|1,1,1,1\nhost|1,1,1,d".getBytes(), f);
        JobExecution execution = launcher.run(job, new JobParameters());
        assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);

        assertEquals(1, repository.findAll().size());
        assertEquals(getHostDataEntity("host0", 1d, 1d, 1d), repository.findById("host0").get());
        FileUtils.forceDelete(f);
    }

    @Test
    public void testReaderBadData() throws Exception {
        File f = new File(properties.getFilePath());
        f.createNewFile();
        FileCopyUtils.copy((
                "1,1,1,1" +
                "\nhost2|1,1,1,d"+
                "\nhost3|1,1,1,d"+
                "\nhost4|1,1,1,d"+
                "\nhost5|1,1,1,d"+
                "\nhost6|1,1,1,d"+
                "\nhost7|1,1,1,d"+
                "\nhost8|1,1,1,d"+
                "\nhost9|1,1,1,d"+
                "\nhost10|1,1,1,d"+
                "\nhost11|1,1,1,d"+
                "\nhost12|1,1,1,d"+
                "\nhost13|1,1,1,d"+
                "\nhost14|1,1,1,d"+
                "\nhost15|1,1,1,d"+
                "\nhost16|1,1,1,d"+
                "\nhost17|1,1,1,1").getBytes(), f);
        JobExecution execution = launcher.run(job, new JobParameters());
        assertEquals(execution.getExitStatus(), ExitStatus.FAILED);

        assertEquals(0, repository.findAll().size());
        //assertEquals(getHostDataEntity("host1", 1d, 1d, 1d), repository.findById("host1").get());
        FileUtils.forceDelete(f);
    }

    @Test
    public void testReaderEmptyFile() throws Exception {
        File f = new File(properties.getFilePath());
        f.createNewFile();
        FileCopyUtils.copy(StringUtils.EMPTY.getBytes(), f);
        JobExecution execution = launcher.run(job, new JobParameters());
        assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);

        assertEquals(0, repository.findAll().size());
        FileUtils.forceDelete(f);

    }

    @Test
    public void testReaderNoFile() throws Exception {
        //properties.setFileName("new-file.csv");
        File f = new File(properties.getPath()+"new-file.csv");
        f.createNewFile();
        FileCopyUtils.copy(StringUtils.EMPTY.getBytes(), f);
        JobExecution execution = launcher.run(job, new JobParameters());
        assertEquals(execution.getExitStatus(), ExitStatus.FAILED);

        assertEquals(0, repository.findAll().size());
        FileUtils.forceDelete(f);
    }

    private static HostDataEntity getHostDataEntity(String host, double average, double min, double max) {
        HostDataEntity entity = new HostDataEntity();
        entity.setHost(host);
        entity.setAverageValue(average);
        entity.setMinValue(min);
        entity.setMaxValue(max);

        return entity;
    }
}