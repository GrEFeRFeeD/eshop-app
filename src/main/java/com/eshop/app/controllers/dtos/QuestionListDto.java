package com.eshop.app.controllers.dtos;

import com.eshop.app.model.report.Report;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuestionListDto {

  private List<QuestionDto> questions;

  public QuestionListDto(List<Report> questions) {
    this.questions = questions.stream().map(QuestionDto::new).collect(Collectors.toList());
  }
}
