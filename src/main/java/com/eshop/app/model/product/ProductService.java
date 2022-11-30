package com.eshop.app.model.product;

import com.eshop.app.exceptions.ProductException;
import com.eshop.app.exceptions.ProductException.ProductExceptionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product findById(Long id) throws ProductException {
    return productRepository.findById(id).orElseThrow(() -> new ProductException(
        ProductExceptionProfile.PRODUCT_NOT_FOUND));
  }
}
