package com.alumni.batch;

import com.alumni.exception.BadDataException;
import com.alumni.host.HostDataEntity;
import com.alumni.host.HostDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;

import java.util.Arrays;
import java.util.List;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-12
 * Last Update: 2022-03-12
 *
 * Configuration class with the ddetails of the job that will process the host file and save it into the database.
 *
 */


@Slf4j
@Configuration
@EnableBatchProcessing
public class AlumniBatchConfiguration{

    @Autowired
    HostDataRepository repository;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    BatchProperties properties;


    /**
     * Created a Job Bean that will be triggered by the FileUpload service.
     *
     * @param step Step with the ItemReader, ItemProcessor and ItemWriter
     * @return a Job to bee executed with the given step.
     */
    @Bean
    public Job processAlumniHostFile(Step step){
        return jobBuilderFactory.get("processAlumniHostFile")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    /**
     * Bean Step to be used by the Job
     *
     * @param reader ItemReader will read the entries and prepare them to be processed.
     * @param processor ItemProcessor will process the entries and enabled them to be writer
     * @param writer ItemWriter will write the entries provided by the processor
     * @return
     */
    @Bean
    public Step step(@Autowired ItemReader reader,
                     @Autowired ItemProcessor processor,
                     @Autowired ItemWriter writer){
        return stepBuilderFactory.get("step")
                .allowStartIfComplete(true)
                .chunk(10)
                .faultTolerant().skipLimit(10).skip(BadDataException.class).skip(FlatFileParseException.class)
                .reader(reader).listener(itemReaderListener())
                .processor(processor).listener(itemWriterListener())
                .writer(writer).listener(itemWriterListener())
                .build();

    }

    /** Bean FlatFileItemReader to be used by the step
     *
     * @param lineMapper a LineMapper that will guide the FlaFileItemWriter on how to process the file entries into a FileDataDto.
     * @return a FlatFileItemReader
     */
    @Bean
    public FlatFileItemReader getFlatFileItemReader(@Autowired LineMapper lineMapper){
        return new FlatFileItemReaderBuilder<FileDataDto>()
                .lineMapper(lineMapper)
                .name("fileReader")
                .resource(new FileSystemResource(properties.getFilePath()))
                .build();
    }

    /** Bean ItemProcessor that instantiates an AlumniItemProcessor
     *
     * @return a new instance of AlumniItemProcessor
     */
    @Bean
    public ItemProcessor<FileDataDto, HostDataEntity> getItemProcessor(){
       return new AlumniItemProcessor();
    }

    /** Bean ItemWriterProcessor that saves the entries processed by the Item processor into the database.
     *
     * @return RepositoryItemWriter
     */
    @Bean
    public RepositoryItemWriter getRepositoryItemWriter(){
        RepositoryItemWriter<HostDataEntity> writer = new RepositoryItemWriter();
        writer.setRepository(repository);
        writer.setMethodName("saveAndFlush");
        return writer;
    }

    /** Bean a LineMapper that specifies the line tokenizer and FieldSetMappers
     *
     * @param mapper a FieldSetMapper that specifies the relation between the file rows and the FileDataDto.
     * @return LineMapper.
     */
    @Bean
    public LineMapper getLineMapper(@Autowired FieldSetMapper mapper){
        DefaultLineMapper lineMapper = new DefaultLineMapper();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer("|"));
        lineMapper.setFieldSetMapper(mapper);
        return lineMapper;
    }

    /** Bean FieldSetMapper that specifies the relation between the file rows and the FileDataDto.
     *
     * @return FieldSetMapper
     */
    @Bean
    public FieldSetMapper<FileDataDto> getFieldSetMapper(){

        return new FieldSetMapper() {
            @Override
            public Object mapFieldSet(FieldSet fieldSet) throws BindException {
                FileDataDto dto = new FileDataDto();
                dto.setHost(fieldSet.readString(0).split(",")[0]);
                dto.setHostStatistics(Arrays.asList(fieldSet.readString(1).split(",")));
                return dto;
            }
        };
    }


    /** Listener for the ItemProcessor
     *
     * @return ItemProcessListener
     */
    public ItemProcessListener<FileDataDto, HostDataEntity> itemProcessorListener(){
        return new ItemProcessListener<FileDataDto, HostDataEntity>() {
            @Override
            public void beforeProcess(FileDataDto item) {
            }

            @Override
            public void afterProcess(FileDataDto item, HostDataEntity result) {
                log.info("{}: Average: {} Max: {} Min: {}",
                        result.getHost(),
                        result.getAverageValue(),
                        result.getMaxValue(),
                        result.getMinValue());
            }
            @Override
            public void onProcessError(FileDataDto item, Exception e) {
                log.error("Error processing item {}", item);
            }
        };
    }

    /** Listener for the ItemWriter
     *
     * @return ItemWriteListener
     */
    public ItemWriteListener<HostDataEntity> itemWriterListener(){
        return new ItemWriteListener<HostDataEntity>() {
            @Override
            public void beforeWrite(List<? extends HostDataEntity> items) {

            }

            @Override
            public void afterWrite(List<? extends HostDataEntity> items) {
                log.info("{} items written.", items.size());
            }

            @Override
            public void onWriteError(Exception exception, List<? extends HostDataEntity> items) {

            }
        };
    }

    /** Listener for the ItemReader
     *
     * @return ItemReadListener
     */
    public ItemReadListener<FileDataDto> itemReaderListener(){
        return new ItemReadListener<FileDataDto>() {
            @Override
            public void beforeRead() {

            }

            @Override
            public void afterRead(FileDataDto item) {

            }

            @Override
            public void onReadError(Exception ex) {
                log.error("error reading entry", ex);
            }
        };
    }
}
