package com.eshop.app.model.comment;

import com.eshop.app.model.report.Report;
import com.eshop.app.model.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String text;

  @ManyToOne
  @JoinColumn(name = "report_id")
  private Report report;
}
