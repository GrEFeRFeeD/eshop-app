package com.eshop.app.controllers.dtos;

import com.eshop.app.model.comment.Comment;
import com.eshop.app.model.report.Report;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewDto {

  private Long id;
  private UserDto user;
  private String text;
  private Integer grade;
  private List<CommentDto> comments;

  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ssZ")
  private Date date;

  public ReviewDto(Report report) {
    this.id = report.getId();
    this.user = new UserDto(report.getUser());
    this.grade = report.getGrade();
    this.comments = report.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
    this.date = report.getDate();
  }
}
