package com.shop.backend.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 16.01.15
 * Time: 0:42
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ItemMeasure implements Serializable {

  @Id @GeneratedValue private long id;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
