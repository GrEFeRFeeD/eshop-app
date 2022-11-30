package com.eshop.app.model.report;

import com.eshop.app.model.product.Product;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Long> {

  List<Report> findByProductAndType(Product product, ReportType type);
}
