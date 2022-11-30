package com.eshop.app.model.comment;

import com.eshop.app.model.report.Report;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

  List<Comment> findByReport(Report report);
}
