package com.shop.backend.domain;

import com.shop.backend.utils.DateUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Item implements Serializable {

  @Id @GeneratedValue private long id;
  @Column(unique=true)
  private String name;
  private String measure;
  private int price;
  private Date created = DateUtils.now();
  private Date updated;

  public Item() {
  }

  public Item(String name, String measure, int price) {
    this.name = name;
    this.measure = measure;
    this.price = price;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMeasure() {
    return measure;
  }

  public void setMeasure(String measure) {
    this.measure = measure;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }
}
