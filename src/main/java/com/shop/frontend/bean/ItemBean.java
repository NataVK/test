package com.shop.frontend.bean;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 16.01.15
 * Time: 23:01
 * To change this template use File | Settings | File Templates.
 */
public class ItemBean {

  private long id;
  private long orderId;
  private String name;
  private String measure;
  private int price;
  private int num;
  private String updated;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public String getUpdated() {
    return updated;
  }

  public void setUpdated(String updated) {
    this.updated = updated;
  }
}
