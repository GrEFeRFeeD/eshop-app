package com.eshop.app.model.comment;

import com.eshop.app.controllers.dtos.CommentForm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  private final CommentRepository commentRepository;

  @Autowired
  public CommentService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public Comment obtainFrom(CommentForm commentForm) {
    Comment comment = new Comment();
    comment.setText(commentForm.getText());
    comment.setDate(new Date(System.currentTimeMillis()));
    return commentRepository.save(comment);
  }

  public Comment save(Comment comment) {
    return commentRepository.save(comment);
  }
}
