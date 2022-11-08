package com.eshop.app.model.report;

import com.eshop.app.model.comment.Comment;
import com.eshop.app.model.user.User;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Report {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String text;
  private Integer grade;

  @Enumerated(EnumType.STRING)
  private ReportType type;

  @OneToMany(mappedBy = "report")
  private List<Comment> comments;
}
