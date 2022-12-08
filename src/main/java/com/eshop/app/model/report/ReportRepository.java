package com.eshop.app.model.report;

import com.eshop.app.model.product.Product;
import com.eshop.app.model.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Long> {

  List<Report> findByProductAndType(Product product, ReportType type);
  List<Report> findByProduct(Product product);
  List<Report> findByUserAndType(User user, ReportType type);
}
