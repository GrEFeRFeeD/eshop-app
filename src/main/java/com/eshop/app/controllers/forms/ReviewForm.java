package com.eshop.app.controllers.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewForm {

  private String text;

  @Min(value = 1, message = "Grade value should be in range [1;5].")
  @Max(value = 5, message = "Grade value should be in range [1;5].")
  private Integer grade;
}
