package com.eshop.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReportException extends Exception {

  @AllArgsConstructor
  public enum ReportExceptionProfile {

    REVIEW_NOT_FOUND("review_not_found",
        "Review by given identifier was not found.", HttpStatus.NOT_FOUND),

    INCOMPATIBLE_REVIEW("incompatible_review",
        "Review by given id does not apply to given product.", HttpStatus.BAD_REQUEST),

    QUESTION_NOT_FOUND("question_not_found",
        "Question by given identifier was not found.", HttpStatus.NOT_FOUND),

    INCOMPATIBLE_QUESTION("incompatible_review",
        "Review by given id does not apply to given product.", HttpStatus.BAD_REQUEST);

    private final String exceptionName;
    private final String exceptionMessage;
    private final HttpStatus responseStatus;

  }

  private final ReportExceptionProfile reportExceptionProfile;

  public ReportException(ReportExceptionProfile exceptionProfile) {
    super(exceptionProfile.exceptionMessage);
    this.reportExceptionProfile = exceptionProfile;
  }

  public String getName() {
    return reportExceptionProfile.exceptionName;
  }

  public HttpStatus getResponseStatus() {
    return reportExceptionProfile.responseStatus;
  }
}
