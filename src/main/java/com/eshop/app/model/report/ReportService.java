package com.eshop.app.model.report;

import com.eshop.app.controllers.forms.QuestionForm;
import com.eshop.app.controllers.forms.ReviewForm;
import com.eshop.app.exceptions.ReportException;
import com.eshop.app.exceptions.ReportException.ReportExceptionProfile;
import com.eshop.app.model.comment.Comment;
import com.eshop.app.model.comment.CommentService;
import com.eshop.app.model.product.Product;
import com.eshop.app.model.user.User;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  private final ReportRepository reportRepository;
  private final CommentService commentService;

  @Autowired
  public ReportService(ReportRepository reportRepository, CommentService commentService) {
    this.reportRepository = reportRepository;
    this.commentService = commentService;
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

  public List<Report> findByProduct(Product product) {
    return reportRepository.findByProduct(product);
  }

  public void deleteAll(List<Report> reports) {

    for (Report report : reports) {

      List<Comment> comments = commentService.findByReport(report);
      commentService.deleteAll(comments);

      reportRepository.delete(report);
    }


  }

  public List<Report> findByUserAndType(User user, ReportType type) {
    return reportRepository.findByUserAndType(user, type);
  }
}
