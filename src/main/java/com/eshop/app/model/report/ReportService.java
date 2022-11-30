package com.eshop.app.model.report;

import com.eshop.app.model.product.Product;
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
}
