package com.eshop.app.controllers.dtos;

import com.eshop.app.model.report.Report;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewListDto {

  private List<ReviewDto> reviews;

  public ReviewListDto(List<Report> reviews) {
    this.reviews = reviews.stream().map(ReviewDto::new).collect(Collectors.toList());
  }
}
