package com.eshop.app.model.product;

import com.eshop.app.controllers.forms.ProductForm;
import com.eshop.app.exceptions.ImageException;
import com.eshop.app.exceptions.ProductException;
import com.eshop.app.exceptions.ProductException.ProductExceptionProfile;
import com.eshop.app.model.category.Category;
import com.eshop.app.model.characteristic.Characteristic;
import com.eshop.app.model.characteristic.CharacteristicService;
import com.eshop.app.model.image.ImageService;
import com.eshop.app.model.report.Report;
import com.eshop.app.model.report.ReportService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final ReportService reportService;
  private final CharacteristicService characteristicService;
  private final ImageService imageService;

  @Autowired
  public ProductService(ProductRepository productRepository, ReportService reportService,
      CharacteristicService characteristicService, ImageService imageService) {
    this.productRepository = productRepository;
    this.reportService = reportService;
    this.characteristicService = characteristicService;
    this.imageService = imageService;
  }

  public Product findById(Long id) throws ProductException {
    return productRepository.findById(id).orElseThrow(() -> new ProductException(
        ProductExceptionProfile.PRODUCT_NOT_FOUND));
  }

  public List<Product> findAll() {
    return (List<Product>) productRepository.findAll();
  }

  public List<Product> findByCategory(Category category) {
    return productRepository.findByCategory(category);
  }

  public Product obtainFrom(ProductForm productForm) throws ProductException, ImageException {

    Product product = obtainFromFormToObject(new Product(), productForm);
    return save(product);
  }

  public Product obtainFromFormToObject(Product product, ProductForm productForm)
      throws ProductException, ImageException {

    product.setName(productForm.getName());
    product.setPrice(productForm.getPrice());
    product.setDescription(productForm.getDescription());

    product.setImage(imageService.findById(productForm.getImage()));

    List<Characteristic> characteristics = productForm.getCharacteristics().stream()
            .map(characteristicService::createCharacteristic)
            .collect(Collectors.toList());

    if (!checkBasicCharacteristicsPresence(product.getCategory().getBasicCharacteristics(), characteristics)) {
      throw new ProductException(ProductExceptionProfile.BASIC_CHARACTERISTICS_NOT_COVERED);
    }

    characteristicService.deleteAll(product.getCharacteristics());

    characteristics.forEach(c -> {
      c.setProduct(product);
      characteristicService.save(c);
    });

    product.setCharacteristics(characteristics);

    return product;
  }

  private static boolean checkBasicCharacteristicsPresence(List<String> basic, List<Characteristic> characteristics) {

    List<String> basicCharacteristics = new ArrayList<>(basic);
    for (Characteristic characteristic : characteristics) {
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

    productRepository.delete(product);
  }
}
