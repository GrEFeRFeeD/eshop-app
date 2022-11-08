package com.eshop.app.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/hello")
  public List<Integer> test() {

    return new ArrayList<>(List.of(1, 2, 3, 4, 5));
  }
}
