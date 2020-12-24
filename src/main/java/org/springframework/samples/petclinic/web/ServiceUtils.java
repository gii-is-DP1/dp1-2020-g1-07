package org.springframework.samples.petclinic.web;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.DishService;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtils {
  private static ServiceUtils instance;

  @Autowired
  private DishService dishService;

  /* Post constructor */

  @PostConstruct
  public void fillInstance() {
    instance = this;
  }

  /*static methods */

  public static DishService getDishService() {
    return instance.dishService;
  }
}
