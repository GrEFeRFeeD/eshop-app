package com.eshop.app.model.report;

import com.eshop.app.controllers.dtos.QuestionForm;
import com.eshop.app.controllers.dtos.ReviewForm;
import com.eshop.app.exceptions.ReportException;
import com.eshop.app.exceptions.ReportException.ReportExceptionProfile;
import com.eshop.app.model.product.Product;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  private final ReportRepository reportRepository;

  @Autowired
  public ReportService(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  public List<Report> findByProductAndType(Product product, ReportType type) {
    return reportRepository.findByProductAndType(product, type);
  }

  public Report obtainFrom(ReviewForm reviewForm) {

    Report review = new Report();
    review.setText(reviewForm.getText());
    review.setGrade(reviewForm.getGrade());
    review.setDate(new Date(System.currentTimeMillis()));
    return save(review);
  }

  public Report obtainFrom(QuestionForm questionForm) {

    Report question = new Report();
    question.setText(questionForm.getText());
    question.setDate(new Date(System.currentTimeMillis()));
    return save(question);
  }

  public Report save(Report report) {
    return reportRepository.save(report);
  }

  public Report findReviewById(Long id) throws ReportException {
    return reportRepository.findById(id).orElseThrow(() -> new ReportException(
        ReportExceptionProfile.REVIEW_NOT_FOUND));
  }

  public Report findQuestionById(Long id) throws ReportException {
    return reportRepository.findById(id).orElseThrow(() -> new ReportException(
        ReportExceptionProfile.QUESTION_NOT_FOUND));
  }
}
