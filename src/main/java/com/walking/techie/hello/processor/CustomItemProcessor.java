package com.walking.techie.hello.processor;

import com.walking.techie.hello.model.Report;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Report, Report> {

  @Override
  public Report process(Report item) throws Exception {
    System.out.println("Processing..." + item);
    return item;
  }
}