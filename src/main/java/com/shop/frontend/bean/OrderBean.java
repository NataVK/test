package com.shop.frontend.bean;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 16.01.15
 * Time: 1:47
 * To change this template use File | Settings | File Templates.
 */
public class OrderBean {

  private long id;
  private int totalPrice;
  private String userEmail;
  private String userFistName;
  private String userLastName;
  private String updated;

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserFistName() {
    return userFistName;
  }

  public void setUserFistName(String userFistName) {
    this.userFistName = userFistName;
  }

  public String getUserLastName() {
    return userLastName;
  }

  public void setUserLastName(String userLastName) {
    this.userLastName = userLastName;
  }

  public int getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(int totalPrice) {
    this.totalPrice = totalPrice;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUpdated() {
    return updated;
  }

  public void setUpdated(String updated) {
    this.updated = updated;
  }
}
