package com.walking.techie.hello;

import com.walking.techie.hello.model.Report;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class ReportFieldSetMapper implements FieldSetMapper<Report> {

  @Override
  public Report mapFieldSet(FieldSet fieldSet) throws BindException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Report report = new Report();
    report.setId(fieldSet.readInt(0));
    report.setSales(fieldSet.readBigDecimal(1));
    report.setQty(fieldSet.readInt(2));
    report.setStaffName(fieldSet.readString(3));
    String date = fieldSet.readString(4);
    try {
      report.setDate(sdf.parse(date));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return report;
  }
}
