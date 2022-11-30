package com.eshop.app.model.product;

import com.eshop.app.controllers.dtos.ProductForm;
import com.eshop.app.exceptions.ProductException;
import com.eshop.app.exceptions.ProductException.ProductExceptionProfile;
import com.eshop.app.model.characteristic.Characteristic;
import com.eshop.app.model.characteristic.CharacteristicService;
import com.eshop.app.model.report.Report;
import com.eshop.app.model.report.ReportService;
import java.util.List;
import java.util.stream.Collectors;
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

  public Product obtainFrom(ProductForm productForm) throws ProductException {

    Product product = obtainFromFormToObject(new Product(), productForm);
    return save(product);
  }

  public Product obtainFromFormToObject(Product product, ProductForm productForm)
      throws ProductException {

    product.setName(productForm.getName());
    product.setPrice(productForm.getPrice());
    product.setDescription(productForm.getDescription());

    List<Characteristic> characteristics = productForm.getCharacteristics().stream()
            .map(characteristicService::createCharacteristic)
            .collect(Collectors.toList());

    product.setCharacteristics(characteristics);

    if (checkBasicCharacteristicsPresence(product)) {
      return product;
    }

    throw new ProductException(ProductExceptionProfile.BASIC_CHARACTERISTICS_NOT_COVERED);
  }

  private boolean checkBasicCharacteristicsPresence(Product product) {

    List<String> basicCharacteristics = product.getCategory().getBasicCharacteristics();
    for (Characteristic characteristic : product.getCharacteristics()) {
      basicCharacteristics.remove(characteristic.getCharacteristic());
    }

    return basicCharacteristics.isEmpty();
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
