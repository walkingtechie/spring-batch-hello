package com.walking.techie.hello.jobs;

import com.walking.techie.hello.ReportFieldSetMapper;
import com.walking.techie.hello.model.Report;
import com.walking.techie.hello.processor.CustomItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@EnableBatchProcessing
public class HelloWorldJob {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  @Autowired
  private StepBuilderFactory stepBuilderFactory;
  @Autowired
  private ReportFieldSetMapper setMapper;


  @Bean
  public FlatFileItemReader<Report> reader() {
    FlatFileItemReader<Report> reader = new FlatFileItemReader<>();
    reader.setResource(new ClassPathResource("report.csv"));
    reader.setLineMapper(new DefaultLineMapper<Report>() {{
      setLineTokenizer(new DelimitedLineTokenizer() {{
        setNames(new String[]{"id", "sales", "qty", "staffName", "date"});
      }});
      setFieldSetMapper(setMapper);
    }});
    return reader;
  }

  @Bean
  public CustomItemProcessor processor() {
    return new CustomItemProcessor();
  }

  @Bean
  public StaxEventItemWriter<Report> writer() {
    StaxEventItemWriter<Report> writer = new StaxEventItemWriter<>();
    writer.setResource(new FileSystemResource("file:xml/outputs/report.xml"));
    writer.setMarshaller(marshaller());
    writer.setRootTagName("report");
    return writer;
  }

  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setClassesToBeBound(Report.class);
    return marshaller;
  }

  @Bean
  public Job helloWorld() {
    return jobBuilderFactory.get("helloWorld").incrementer(new RunIdIncrementer())
        .flow(step1()).end().build();
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1").<Report, Report>chunk(10).reader(reader())
        .processor(processor()).writer(writer()).build();
  }
}
