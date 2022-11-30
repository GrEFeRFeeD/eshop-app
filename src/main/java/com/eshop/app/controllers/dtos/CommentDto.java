package com.eshop.app.controllers.dtos;

import com.eshop.app.model.comment.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {

  private Long id;
  private UserDto user;
  private String text;

  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ssZ")
  private Date date;

  public CommentDto(Comment comment) {
    this.id = comment.getId();
    this.user = new UserDto(comment.getUser());
    this.text = comment.getText();
    this.date = comment.getDate();
  }
}
