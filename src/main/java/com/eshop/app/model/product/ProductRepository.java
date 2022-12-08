package com.eshop.app.model.product;

import com.eshop.app.model.category.Category;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

  List<Product> findByCategory(Category category);
}
