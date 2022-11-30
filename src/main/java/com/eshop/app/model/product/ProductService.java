package com.eshop.app.model.product;

import com.eshop.app.controllers.dtos.ProductForm;
import com.eshop.app.exceptions.ProductException;
import com.eshop.app.exceptions.ProductException.ProductExceptionProfile;
import com.eshop.app.model.characteristic.CharacteristicService;
import com.eshop.app.model.report.Report;
import com.eshop.app.model.report.ReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final ReportService reportService;
  private final CharacteristicService characteristicService;

  @Autowired
  public ProductService(ProductRepository productRepository, ReportService reportService,
      CharacteristicService characteristicService) {
    this.productRepository = productRepository;
    this.reportService = reportService;
    this.characteristicService = characteristicService;
  }

  public Product findById(Long id) throws ProductException {
    return productRepository.findById(id).orElseThrow(() -> new ProductException(
        ProductExceptionProfile.PRODUCT_NOT_FOUND));
  }

  public List<Product> findAll() {
    return (List<Product>) productRepository.findAll();
  }

  public List<Product> findByCategory(ProductCategory category) {
    return productRepository.findByCategory(category);
  }

  public Product obtainFrom(ProductForm productForm) {

    Product product = obtainFromFormToObject(new Product(), productForm);
    return save(product);
  }

  public Product obtainFromFormToObject(Product product, ProductForm productForm) {

    product.setName(productForm.getName());
    product.setPrice(productForm.getPrice());
    product.setDescription(productForm.getDescription());
    // TODO: characteristics
    return product;
  }

  public Product save(Product product) {
    return productRepository.save(product);
  }

  public void delete(Product product) {

    characteristicService.deleteAll(product.getCharacteristics());

    List<Report> reports = reportService.findByProduct(product);
    reportService.deleteAll(reports);
  }
}
